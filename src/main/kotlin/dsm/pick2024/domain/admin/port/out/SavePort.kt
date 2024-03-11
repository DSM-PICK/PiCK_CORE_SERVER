package dsm.pick2024.domain.admin.port.out

import dsm.pick2024.domain.admin.domain.Admin

interface SavePort {
    fun save(admin: Admin)
}
