package dsm.pick2024.domain.selfstudy.port.out

import dsm.pick2024.domain.selfstudy.domain.SelfStudy

interface SelfStudySaveAllPort {
    fun saveAll(selfStudy: SelfStudy): List<SelfStudy>
}
