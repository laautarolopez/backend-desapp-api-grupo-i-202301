package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityFilterChain {
    @Bean
    @Throws(Exception::class)
    fun permitAll(http: HttpSecurity): SecurityFilterChain? {
        http.cors().and().csrf().disable()
            .authorizeHttpRequests().requestMatchers("/**").permitAll()
        http.headers().frameOptions().disable()
        return http.build()
    }
}