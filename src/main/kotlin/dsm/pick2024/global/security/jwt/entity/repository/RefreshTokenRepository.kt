package dsm.pick2024.global.security.jwt.entity.repository

import dsm.pick2024_server.global.security.jwt.entity.RefreshToken
import org.springframework.data.repository.CrudRepository

interface RefreshTokenRepository : CrudRepository<RefreshToken, Long>
