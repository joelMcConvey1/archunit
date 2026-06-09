package com.example.architecture;

import com.example.ArchitectureTestFixtures;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import jakarta.persistence.Entity;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

public class ServiceRulesTest extends ArchitectureTestFixtures {

    @Test
    void serviceClasses_ShouldBeAnnotatedWithServiceAnnotation() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().haveSimpleNameEndingWith("Service")
                .should().beAnnotatedWith(Service.class);

        rule.check(classes);
    }

    @Test
    void noServiceClasses_ShouldResideOutsideDesignatedPackage() {
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that().haveSimpleNameEndingWith("Service")
                .or().areAnnotatedWith(Service.class)
                .should().resideOutsideOfPackages(SERVICE_LAYER_PACKAGE);

        rule.check(classes);
    }

    @Test
    void serviceClasses_ShouldNotDependOnControllerLayer() {
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that().resideInAPackage(SERVICE_LAYER_PACKAGE)
                .should().dependOnClassesThat()
                .resideInAPackage(CONTROLLER_LAYER_PACKAGE);

        rule.check(classes);
    }

    @Test
    void publicServiceMethods_ShouldNotReturnEntities() {
        ArchRule rule = ArchRuleDefinition.methods()
                .that().arePublic()
                .and().areDeclaredInClassesThat().areAnnotatedWith(Service.class)
                .should().notHaveRawReturnType(Entity.class);

        rule.check(classes);
    }

    @Test
    void serviceClasses_ShouldBeSuffixed() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().resideInAPackage(SERVICE_LAYER_PACKAGE)
                .and().areAnnotatedWith(Service.class)
                .should().haveSimpleNameEndingWith("Service");

        rule.check(classes);
    }
}
