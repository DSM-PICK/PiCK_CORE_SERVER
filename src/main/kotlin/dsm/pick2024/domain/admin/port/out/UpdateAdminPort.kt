package dsm.pick2024.domain.admin.port.out

import java.util.UUID

interface UpdateAdminPort {
    fun updateAdminPassword(adminId: UUID, password: String)
}
