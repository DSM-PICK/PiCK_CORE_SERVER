package dsm.pick2024.domain.selfstudy.port.out

import java.time.LocalDate

interface ExistsByDateAndFloor {
    fun existsByDateAndFloor(date: LocalDate, floor: Int): Boolean?
}
