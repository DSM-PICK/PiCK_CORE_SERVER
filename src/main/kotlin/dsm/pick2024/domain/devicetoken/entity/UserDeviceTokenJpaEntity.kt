package dsm.pick2024.domain.devicetoken.entity

import dsm.pick2024.global.base.BaseUUIDEntity
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "tbl_admin_device_token")
class AdminDeviceTokenJpaEntity (
    id: UUID,

    @Column(name = "user_id", nullable = false)
    val userId: UUID,

    @Column(name = "device_token")
    val deviceToken: String,

) : BaseUUIDEntity(id)
