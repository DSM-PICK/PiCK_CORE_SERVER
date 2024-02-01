package dsm.pick2024.domain.admin.port.out

import dsm.pick2024.domain.admin.domain.Admin

interface FindByAdminIdPort {
    fun findByAdminId(adminId: String): Admin?
}
