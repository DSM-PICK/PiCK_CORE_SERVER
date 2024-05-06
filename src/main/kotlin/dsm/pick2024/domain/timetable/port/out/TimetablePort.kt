package dsm.pick2024.domain.timetable.port.out

interface TimetablePort :
    SaveAllTimetablePort,
    FindTimetableByDayWeekPort,
    FindTimetableByIdPort,
    SaveTimetablePort
