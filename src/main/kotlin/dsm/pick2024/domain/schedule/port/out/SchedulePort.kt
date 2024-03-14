package dsm.pick2024.domain.schedule.port.out

interface SchedulePort :
    ScheduleMonthPort,
    ScheduleSavePort,
    FindScheduleByIdPort
