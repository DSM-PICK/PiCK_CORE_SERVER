package dsm.pick2024.infrastructure.feign

import dsm.pick2024.infrastructure.feign.exception.FeignBadRequestException
import dsm.pick2024.infrastructure.feign.exception.FeignForbiddenException
import dsm.pick2024.infrastructure.feign.exception.FeignServerError
import dsm.pick2024.infrastructure.feign.exception.FeignUnAuthorizedException
import feign.FeignException
import feign.Response
import feign.codec.ErrorDecoder
import org.slf4j.LoggerFactory

class FeignClientErrorDecoder : ErrorDecoder {
    private val log = LoggerFactory.getLogger(this::class.java)
    override fun decode(methodKey: String?, response: Response?): Exception {
        response?.let {
            response.body().asInputStream().use { stream ->
                val body = stream.readBytes().toString(Charsets.UTF_8)
                val truncatedBody = if (body.length > 1000) body.take(1000) + "... (truncated)" else body
                log.error(
                    "Feign error - method: $methodKey, status: ${response.status()}," +
                        " reason: ${response.reason()}, body: $truncatedBody"
                )
            }

            if (response.status() >= 400) {
                when (response.status()) {
                    400 -> throw FeignBadRequestException
                    401 -> throw FeignUnAuthorizedException
                    403 -> throw FeignForbiddenException
                    else -> throw FeignServerError
                }
            }
        }
        return FeignException.errorStatus(methodKey, response)
    }
}
