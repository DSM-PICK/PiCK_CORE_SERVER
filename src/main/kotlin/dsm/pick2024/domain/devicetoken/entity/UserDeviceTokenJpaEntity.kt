package dsm.pick2024.domain.devicetoken.entity

import dsm.pick2024.domain.devicetoken.enum.OSType
import dsm.pick2024.domain.user.entity.UserJpaEntity
import dsm.pick2024.global.base.BaseUUIDEntity
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity(name = "tbl_user_device_token")
class UserDeviceTokenJpaEntity(
    id: UUID,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    val user: UserJpaEntity,

    @Column(name = "device_token")
    val deviceToken: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "os", nullable = false)
    val os: OSType

) : BaseUUIDEntity(id)
