package dsm.pick2024.domain.timetable.peristence.repository

import dsm.pick2024.domain.timetable.domain.Timetable
import java.util.UUID
import org.springframework.data.repository.Repository

interface TimetableRepository: Repository<Timetable, UUID>
