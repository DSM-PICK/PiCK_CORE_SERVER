package dsm.pick2024.domain.admin.port.out

import dsm.pick2024.domain.admin.domain.Admin

interface FindAdminByNamePort {
    fun findByName(name: String): Admin?
}
