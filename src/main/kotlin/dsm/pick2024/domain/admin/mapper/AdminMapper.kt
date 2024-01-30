package dsm.pick2024.domain.admin.mapper

import dsm.pick2024.domain.admin.domain.Admin
import dsm.pick2024.domain.admin.entity.AdminJpaEntity
import dsm.pick2024.global.base.GenericMapper
import org.springframework.stereotype.Component

@Component
abstract class AdminMapper : GenericMapper<AdminJpaEntity, Admin> {

    abstract override fun toEntity(domain: Admin): AdminJpaEntity

    abstract override fun toDomain(entity: AdminJpaEntity): Admin
}
