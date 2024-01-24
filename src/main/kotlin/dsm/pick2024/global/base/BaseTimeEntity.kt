package dsm.pick2024.global.base

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTimeEntity(
    @CreatedDate
    private val createdAt: LocalDateTime? = LocalDateTime.now(),
    @LastModifiedDate
    private val modifiedAt: LocalDateTime? = LocalDateTime.now()
)
