package com.example.architecture;

import com.example.ArchitectureTestFixtures;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import jakarta.persistence.Entity;
import org.junit.jupiter.api.Test;

public class DomainRulesTest extends ArchitectureTestFixtures {

    @Test
    void noClassesWithEntityAnnotation_ShouldResideOutsideOfModelPackage() {
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that().areAnnotatedWith(Entity.class)
                .should().resideOutsideOfPackage(MODEL_PACKAGE);

        rule.check(classes);
    }

    @Test
    void entityClasses_ShouldNotDependOnServiceOrControllerLayers() {
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that().resideInAPackage(MODEL_PACKAGE)
                .should().dependOnClassesThat()
                .resideInAnyPackage(SERVICE_LAYER_PACKAGE, CONTROLLER_LAYER_PACKAGE);

        rule.check(classes);
    }

    @Test
    void noDtoClasses_ShouldResideOutsideOfModelPackage() {
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that().haveSimpleNameEndingWith("Request")
                .or().haveSimpleNameEndingWith("Response")
                .should().resideOutsideOfPackage(MODEL_PACKAGE);

        rule.check(classes);
    }
}
