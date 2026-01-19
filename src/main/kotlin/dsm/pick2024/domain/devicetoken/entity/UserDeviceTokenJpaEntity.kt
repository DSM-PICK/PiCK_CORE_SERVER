package dsm.pick2024.domain.devicetoken.entity

import dsm.pick2024.domain.devicetoken.enum.OSType
import dsm.pick2024.global.base.BaseUUIDEntity
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity(name = "tbl_admin_device_token")
class UserDeviceTokenJpaEntity(
    id: UUID,

    @Column(name = "user_id", nullable = false)
    val userId: UUID,

    @Column(name = "device_token")
    val deviceToken: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "os", nullable = false)
    val os: OSType

) : BaseUUIDEntity(id)
