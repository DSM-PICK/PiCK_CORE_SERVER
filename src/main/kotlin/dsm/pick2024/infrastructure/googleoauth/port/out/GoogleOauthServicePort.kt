package dsm.pick2024.infrastructure.googleoauth.port.out

interface GoogleOauthServicePort {
    fun getToken(): String
}
