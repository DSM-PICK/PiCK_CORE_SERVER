package dsm.pick2024.domain.admin.mapper

import dsm.pick2024.domain.admin.domain.Admin
import dsm.pick2024.domain.admin.entity.AdminJpaEntity
import dsm.pick2024.global.base.GenericMapper
import org.springframework.stereotype.Component

@Component
class AdminMapper : GenericMapper<AdminJpaEntity, Admin> {

    override fun toEntity(domain: Admin) = domain.run {
        AdminJpaEntity(
            id = id,
            name = name,
            password = password,
            classRoom = classRoom,
            adminId = adminId
        )
    }

    override fun toDomain(entity: AdminJpaEntity?) = entity?.run {
        Admin(
            id = id,
            name = name,
            password = password,
            classRoom = classRoom,
            adminId = adminId
        )
    }
}
