package dsm.pick2024.global.security.auth

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class AuthDetails(
    private val name: String
) : UserDetails {

    companion object {
        private val ROLE_STU = "ROLE_STU"
    }

    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return listOf<SimpleGrantedAuthority>(SimpleGrantedAuthority(ROLE_STU))
    }

    override fun getPassword(): String? {
        return null
    }

    override fun getUsername(): String {
        return name
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
