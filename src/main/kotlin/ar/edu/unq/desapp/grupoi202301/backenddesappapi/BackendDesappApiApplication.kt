package ar.edu.unq.desapp.grupoi202301.backenddesappapi

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Configuration

@SpringBootApplication
@Configuration
@OpenAPIDefinition(info = Info(title = "Backend Desapp Api", version = "1.0", description = "Universidad Nacional de Quilmes - Desarrollo de aplicaciones"))
@EnableCaching
class BackendDesappApiApplication

fun main(args: Array<String>) {
	runApplication<BackendDesappApiApplication>(*args)
}
