package dsm.pick2024_server.infrastructure.feign

import dsm.pick2024_server.infrastructure.feign.exception.FeignBadRequestException
import dsm.pick2024_server.infrastructure.feign.exception.FeignForbiddenException
import dsm.pick2024_server.infrastructure.feign.exception.FeignServerError
import dsm.pick2024_server.infrastructure.feign.exception.FeignUnAuthorizedException
import feign.FeignException
import feign.Response
import feign.codec.ErrorDecoder

class FeignClientErrorDecoder : ErrorDecoder {
    override fun decode(methodKey: String?, response: Response?): Exception {
        if (response!!.status() >= 400) {
            when (response.status()) {
                400 -> throw FeignBadRequestException
                401 -> throw FeignUnAuthorizedException
                403 -> throw FeignForbiddenException
                else -> throw FeignServerError
            }
        }
        return FeignException.errorStatus(methodKey, response)
    }
}
