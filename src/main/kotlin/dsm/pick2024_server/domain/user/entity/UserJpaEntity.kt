package dsm.pick2024_server.domain.user.entity

import dsm.pick2024_server.global.base.BaseUUIDEntity
import java.util.UUID
import javax.persistence.Entity

@Entity(name = "tbl_user")
class UserJpaEntity(
    id: UUID?,

    val name: String
) : BaseUUIDEntity(id)
