package dsm.pick2024.domain.weekendmeal.port.out

interface WeekendMealPort :
    FindWeekendMealByUserIdPort,
    SaveWeekendMealPort,
    FindWeekendMealClassPort,
    FindWeekendMealQuitClassPort,
    ExistsByUserId
