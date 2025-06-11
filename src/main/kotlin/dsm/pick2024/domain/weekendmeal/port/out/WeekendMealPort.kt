package dsm.pick2024.domain.weekendmeal.port.out

interface WeekendMealPort :
    SaveWeekendMealPort,
    ExistsWeekendMealPort,
    QueryWeekendMealPort,
    UpdateWeekendMealPort
