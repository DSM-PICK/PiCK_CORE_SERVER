package dsm.pick2024.domain.user.entity

import dsm.pick2024.global.base.BaseUUIDEntity
import java.util.UUID
import javax.persistence.Entity

@Entity(name = "tbl_user")
class UserJpaEntity(
    id: UUID?,

    val name: String
) : dsm.pick2024.global.base.BaseUUIDEntity(id)
