package dsm.pick2024.domain.weekendmeal.persistence

import dsm.pick2024.domain.admin.exception.AdminNotFoundException
import dsm.pick2024.domain.admin.persistence.repository.AdminRepository
import dsm.pick2024.domain.weekendmeal.domain.WeekendMealPeriod
import dsm.pick2024.domain.weekendmeal.mapper.WeekendMealPeriodMapper
import dsm.pick2024.domain.weekendmeal.persistence.repository.WeekendMealPeriodRepository
import dsm.pick2024.domain.weekendmeal.port.out.WeekendMealPeriodPort
import org.springframework.stereotype.Component
import java.util.*

@Component
class WeekendMealPeriodPersistenceAdapter(
    private val weekendMealPeriodMapper: WeekendMealPeriodMapper,
    private val weekendMealPeriodRepository: WeekendMealPeriodRepository,
    private val adminRepository: AdminRepository
) : WeekendMealPeriodPort {

    override fun save(weekendMealPeriod: WeekendMealPeriod) {
        val admin = adminRepository.findById(weekendMealPeriod.adminId).orElseThrow { AdminNotFoundException }
        weekendMealPeriodRepository.save(weekendMealPeriodMapper.toEntity(weekendMealPeriod, admin))
    }

    override fun queryWeekendMealById(id: UUID): WeekendMealPeriod? {
        return weekendMealPeriodRepository.findById(id)?.let { weekendMealPeriodMapper.toDomain(it) }
    }

    override fun queryWeekendMealByAdminId(id: UUID): WeekendMealPeriod? {
        return weekendMealPeriodRepository.findByAdmin_Id(id)?.let { weekendMealPeriodMapper.toDomain(it) }
    }

    override fun queryAllWeekendMeal(): List<WeekendMealPeriod> {
        return weekendMealPeriodRepository.findAll().map { weekendMealPeriodMapper.toDomain(it) }
    }
}
