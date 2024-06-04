package dsm.pick2024.domain.admin.port.out

interface AdminPort :
    FindAdminByNamePort,
    FindByAdminIdPort,
    ExistsByAdminIdPort,
    AdminSavePort,
    FindAdminByGradeAndClassNumPort,
    FindAllPort
