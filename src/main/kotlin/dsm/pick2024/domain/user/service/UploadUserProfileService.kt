package dsm.pick2024.domain.user.service

import dsm.pick2024.domain.user.facade.UserFacade
import dsm.pick2024.domain.user.port.`in`.UploadUserProfileUseCase
import dsm.pick2024.domain.user.port.out.UserSavePort
import dsm.pick2024.infrastructure.s3.FileUtil
import dsm.pick2024.infrastructure.s3.PathList
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class UploadUserProfileService(
    private val fileUtil: FileUtil,
    private val userFacade: UserFacade,
    private val userSavePort: UserSavePort
) : UploadUserProfileUseCase {

    @Transactional
    override fun uploadUserProfile(file: MultipartFile) {
        val user = userFacade.currentUser()

        if (user.profile != null) fileUtil.delete(user.profile!!, PathList.PROFILE)
        val update = user.updateProfileFileName(fileUtil.upload(file, PathList.PROFILE))

        userSavePort.save(update)
    }
}
