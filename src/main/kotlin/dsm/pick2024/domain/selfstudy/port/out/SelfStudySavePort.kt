package dsm.pick2024.domain.selfstudy.port.out

import dsm.pick2024.domain.selfstudy.domain.SelfStudy

interface SelfStudySavePort {
    fun save(selfStudy: SelfStudy)
}
