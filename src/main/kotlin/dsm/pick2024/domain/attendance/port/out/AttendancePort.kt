package dsm.pick2024.domain.attendance.port.out

interface AttendancePort :
    SaveAllPort,
    FindAttendanceByUserIdPort,
    QueryClassAttendancePort,
    QueryClubAttendancePort,
    FindAllAttendancePort
