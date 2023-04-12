package ar.edu.unq.desapp.grupoi202301.backenddesappapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration

@SpringBootApplication
@Configuration
class BackendDesappApiApplication

fun main(args: Array<String>) {
	runApplication<BackendDesappApiApplication>(*args)
}
