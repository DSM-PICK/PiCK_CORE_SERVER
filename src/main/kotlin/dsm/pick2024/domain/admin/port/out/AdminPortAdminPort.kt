package dsm.pick2024.domain.admin.port.out

interface AdminPortAdminPort :
    FindAdminByNamePort,
    FindByAdminIdPort,
    ExistsByAdminIdPort,
    AdminSavePort,
    FindAdminByGradeAndClassNumPort,
    FindAllPort
