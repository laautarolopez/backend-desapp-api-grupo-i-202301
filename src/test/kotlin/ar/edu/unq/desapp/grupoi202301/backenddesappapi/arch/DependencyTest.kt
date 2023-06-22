package ar.edu.unq.desapp.grupoi202301.backenddesappapi.arch

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import com.tngtech.archunit.library.Architectures.layeredArchitecture
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DependencyTest {
    lateinit private var baseClasses: JavaClasses

    @BeforeAll
    fun setup() {
        baseClasses = ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("ar.edu.unq.desapp.grupoi202301.backenddesappapi")
    }

    @Test
    fun layeredArchitectureShouldBeRespected(){
        layeredArchitecture()
            .consideringAllDependencies()
            .layer("Controller").definedBy("..restWebService..")
            .layer("Service").definedBy("..service..")
            .layer("Persistence").definedBy("..persistence..")
            .layer("Aspect").definedBy("..aspect..")

            .whereLayer("Controller").mayOnlyBeAccessedByLayers("Service")
            .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller", "Aspect")
            .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Service")
            .check(baseClasses)
    }

    @Test
    fun controllerClassesShouldHaveSpringControllerAnnotation() {
        classes().that().resideInAPackage("..restWebService")
            .should().beAnnotatedWith("org.springframework.web.bind.annotation.RestController")
            .check(baseClasses);
    }
}