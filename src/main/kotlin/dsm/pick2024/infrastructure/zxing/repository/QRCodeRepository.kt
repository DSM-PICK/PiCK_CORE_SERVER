package dsm.pick2024.infrastructure.zxing.repository

import dsm.pick2024.infrastructure.zxing.entity.QRCode
import org.springframework.data.repository.CrudRepository
import java.util.UUID


interface QRCodeRepository : CrudRepository<QRCode, UUID>
