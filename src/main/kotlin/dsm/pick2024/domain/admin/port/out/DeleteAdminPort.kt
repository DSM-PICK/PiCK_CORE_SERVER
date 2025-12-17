package dsm.pick2024.domain.admin.port.out

import java.util.UUID

interface DeleteAdminPort {
    fun deleteById(adminId: UUID)
}
