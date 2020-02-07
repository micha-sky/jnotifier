package io.dope.jnotifier;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("io.dope.jnotifier");

        noClasses()
            .that()
                .resideInAnyPackage("io.dope.jnotifier.service..")
            .or()
                .resideInAnyPackage("io.dope.jnotifier.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..io.dope.jnotifier.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
