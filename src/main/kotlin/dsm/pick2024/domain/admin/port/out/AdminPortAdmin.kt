package dsm.pick2024.domain.admin.port.out

interface AdminPortAdmin :
    FindAdminByNamePort,
    FindByAdminIdPort,
    ExistsByAdminIdPort,
    AdminSavePort,
    FindAdminByGradeAndClassNum
