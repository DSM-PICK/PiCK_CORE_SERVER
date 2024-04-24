package dsm.pick2024.domain.bug.presentation

import dsm.pick2024.domain.bug.AddBugService
import dsm.pick2024.domain.bug.UploadBugImageService
import dsm.pick2024.domain.bug.presentation.dto.request.BugRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@Tag(name = "버그제보 api")
@RestController
@RequestMapping("/bug")
class BugController(
    private val uploadBugImageService: UploadBugImageService,
    private val addBugService: AddBugService
) {

    @Operation(summary = "버그제보 이미지 업로드 API")
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("/upload")
    fun uploadBugImage(
        @RequestPart(name = "file") file: MultipartFile
    ) =
        uploadBugImageService.uploadBugImage(file)

    @Operation(summary = "버그제부 문자 전송 API")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/message")
    fun sendBugMessage(
        @RequestBody bugRequest: BugRequest
    ) =
        addBugService.bugAlarm(bugRequest)
}
