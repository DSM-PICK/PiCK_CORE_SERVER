package dsm.pick2024.domain.bug.presentation

import dsm.pick2024.domain.bug.UploadBugImageService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/bug")
class BugController(
    private val uploadBugImageService: UploadBugImageService
) {

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("/upload")
    fun uploadBugImage(
        @RequestPart(name = "file") file: MultipartFile
    ) =
        uploadBugImageService.uploadBugImage(file)
}
