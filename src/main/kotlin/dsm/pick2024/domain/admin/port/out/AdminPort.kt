package dsm.pick2024.domain.admin.port.out

interface AdminPort :
    QueryAdminPort,
    ExistsByAdminIdPort,
    AdminSavePort,
    UpdateAdminPort,
    DeleteAdminPort
