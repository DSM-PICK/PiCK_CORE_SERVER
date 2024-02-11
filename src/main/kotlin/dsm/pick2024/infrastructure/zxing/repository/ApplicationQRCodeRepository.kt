package dsm.pick2024.infrastructure.zxing.repository

import dsm.pick2024.infrastructure.zxing.entity.ApplicationQRCode
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import java.util.UUID


interface ApplicationQRCodeRepository : JpaRepository<ApplicationQRCode, String>
