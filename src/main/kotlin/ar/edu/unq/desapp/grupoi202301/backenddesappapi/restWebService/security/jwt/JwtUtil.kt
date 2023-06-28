package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.security.jwt

import ar.edu.unq.desapp.grupoi202301.backenddesappapi.model.User
import ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.DTO.LoginDTO
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil {

    private val secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    fun generateToken(email: String): String {
        if(email == "admin@admin.com") {
            return generateAdminToken(email)
        }
        val expirationTime = Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME)
        return Jwts.builder()
            .setSubject(email)
            .setExpiration(expirationTime)
            .signWith(secretKey)
            .compact()
    }

    private fun generateAdminToken(email: String): String {
        val expirationTime = Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME)
        return Jwts.builder()
                .setSubject(email)
                .claim("role", "ADMIN")
                .setExpiration(expirationTime)
                .signWith(secretKey)
                .compact()
    }

    fun validateToken(token: String): Boolean {
        try {
            val claims: Claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .body
            return !claims.expiration.before(Date())
        } catch (e: Exception) {
            return false
        }
    }

    fun getEmailFromToken(token: String): String? {
        return try {
            val claims: Claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .body
            claims.subject
        } catch (e: Exception) {
            null
        }
    }

    fun getRoleFromToken(token: String): String? {
        return try {
            val claims: Claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .body
            claims.get("role", String::class.java)
        } catch (e: Exception) {
            null
        }
    }

    fun isPasswordOnExistingUser(user: User?, login: LoginDTO): Boolean {
        return user != null && user.password == login.password
    }

    companion object {
        private const val TOKEN_EXPIRATION_TIME: Long = 3600000 // 1 hour
    }
}