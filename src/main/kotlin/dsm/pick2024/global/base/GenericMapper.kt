package dsm.pick2024.global.base

interface GenericMapper<E, D> {
    fun toEntity(domain: D): E

    fun toDomain(entity: E?): D?
}
