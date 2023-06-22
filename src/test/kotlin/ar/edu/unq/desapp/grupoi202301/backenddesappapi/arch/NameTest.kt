package ar.edu.unq.desapp.grupoi202301.backenddesappapi.arch

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NameTest {
    lateinit private var baseClasses: JavaClasses

    @BeforeAll
    fun setup() {
        baseClasses = ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("ar.edu.unq.desapp.grupoi202301.backenddesappapi")
    }

    @Test
    fun modelExceptionsClassesShouldEndWithException(){
        classes().that().resideInAPackage("..model.exceptions..")
            .should().haveSimpleNameEndingWith("Companion")
            .orShould().haveSimpleNameEndingWith("Exception")
            .check(baseClasses)
    }

    @Test
    fun restWebServiceExceptionsClassesShouldContainExceptionOrError(){
        classes().that().resideInAPackage("..restWebService.exception..")
            .should().haveSimpleNameEndingWith("Companion")
            .orShould().haveSimpleNameContaining("Exception")
            .orShould().haveSimpleNameContaining("Error")
            .check(baseClasses)
    }

    @Test
    fun serviceExceptionsClassesShouldEndWithNonExistent(){
        classes().that().resideInAPackage("..service..exception..")
            .should().haveSimpleNameEndingWith("Companion")
            .orShould().haveSimpleNameEndingWith("NonExistent")
            .check(baseClasses)
    }

    @Test
    fun persistenceClassesShouldEndWithPersistence(){
        classes().that().resideInAPackage("..persistence..")
            .should().haveSimpleNameEndingWith("Persistence")
            .check(baseClasses)
    }

    @Test
    fun serviceClassesShouldEndWithService(){
        classes().that().resideInAPackage("..service")
            .should().haveSimpleNameEndingWith("Service")
            .check(baseClasses)
    }

    @Test
    fun serviceImplementationClassesShouldEndWithServiceImp(){
        classes().that().resideInAPackage("..service.imp")
            .should().haveSimpleNameEndingWith("ServiceImp")
            .check(baseClasses)
    }
}