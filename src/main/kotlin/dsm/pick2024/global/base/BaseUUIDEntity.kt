package dsm.pick2024.global.base

import org.hibernate.annotations.GenericGenerator
import java.util.UUID
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseUUIDEntity(
    id : UUID?
){
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(
        columnDefinition = "BINARY(16)",
        nullable = false
    )
    val id: UUID? = if (id == UUID(0, 0)) null else id
}
