package dsm.pick2024.domain.devicetoken

import dsm.pick2024.global.base.BaseUUIDEntity
import org.apache.tomcat.jni.OS
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated

@Entity(name = "tbl_device_token")
class DeviceTokenEntity(
    id: UUID,

    @Column(name = "device_token")
    val deviceToken: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "os")
    val os: OS

) : BaseUUIDEntity(id)
