package ar.edu.unq.desapp.grupoi202301.backenddesappapi.restWebService.security.jwt

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig : WebMvcConfigurer {

    @Autowired
    private lateinit var jwtInterceptor: JwtInterceptor

    @Autowired
    private lateinit var roleInterceptor: RoleInterceptor

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(roleInterceptor)
                .addPathPatterns("/cryptos/create")

        registry.addInterceptor(jwtInterceptor)
            .addPathPatterns("/trades/create")
            .addPathPatterns("/transactions/create")
            .addPathPatterns("/transactions/transfer")
            .addPathPatterns("/transactions/confirm")
            .addPathPatterns("/transactions/cancel")
            .addPathPatterns("/volume/**")
    }
}