package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.security.jwt

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class JwtInterceptor : HandlerInterceptor {

    @Autowired
    private lateinit var jwtUtil: JwtUtil

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val token = extractToken(request)
        if (token != null && jwtUtil.validateToken(token)) {
            val email = jwtUtil.getEmailFromToken(token)
            if (email != null) {
                val authentication = UsernamePasswordAuthenticationToken(email, null, emptyList())
                SecurityContextHolder.getContext().authentication = authentication
                return true
            }
        }
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido o expirado")
        return false
    }

    fun extractToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7)
        }
        return null
    }
}

@Component
class RoleInterceptor : JwtInterceptor() {

    @Autowired
    private lateinit var jwtUtil: JwtUtil

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val token = extractToken(request)

        if (token != null && jwtUtil.validateToken(token)) {
            val role = jwtUtil.getRoleFromToken(token)
            if(role == "ADMIN") {
                val email = jwtUtil.getEmailFromToken(token)
                if (email != null) {
                    val authentication = UsernamePasswordAuthenticationToken(email, null, emptyList())
                    SecurityContextHolder.getContext().authentication = authentication
                    return true
                }
            }
        }

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido o expirado")
        return false
    }
}
