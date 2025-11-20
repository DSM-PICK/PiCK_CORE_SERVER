package dsm.pick2024.domain.devicetoken

import dsm.pick2024.domain.admin.entity.AdminJpaEntity
import dsm.pick2024.domain.devicetoken.enum.OSType
import dsm.pick2024.global.base.BaseUUIDEntity
import java.util.UUID
import javax.persistence.*

@Entity(name = "tbl_admin_device_token")
class AdminDeviceTokenJpaEntity(
    id: UUID,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", unique = true, nullable = false)
    val admin: AdminJpaEntity,

    @Column(name = "device_token")
    val deviceToken: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "os", nullable = false)
    val os: OSType

) : BaseUUIDEntity(id)
