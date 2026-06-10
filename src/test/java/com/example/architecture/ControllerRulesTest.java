package com.example.architecture;

import com.example.ArchitectureTestFixtures;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import jakarta.persistence.Entity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

public class ControllerRulesTest extends ArchitectureTestFixtures {

    @Test
    @DisplayName("Rest Controller classes should be annotated with @RestController")
    void restControllerClasses_ShouldBeAnnotatedWith_RestControllerAnnotation() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().haveSimpleNameEndingWith("Controller")
                .should().beAnnotatedWith(RestController.class);

        rule.check(classes);
    }

    @Test
    @DisplayName("Controller classes should reside in the controller package")
    void noRestControllerClasses_ShouldResideOutsideDesignatedPackage() {
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that().haveSimpleNameEndingWith("Controller")
                .or().areAnnotatedWith(RestController.class)
                .should().resideOutsideOfPackages(CONTROLLER_LAYER_PACKAGE);

        rule.check(classes);
    }

    @Test
    @DisplayName("Rest Controller classes should not depend on each other")
    void restControllerClasses_ShouldNotDependOnEachOther() {
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that().areAnnotatedWith(RestController.class)
                .should().dependOnClassesThat()
                .resideInAPackage(CONTROLLER_LAYER_PACKAGE);

        rule.check(classes);
    }

    @Test
    @DisplayName("Rest Controller classes should not directly depend on Repository classes")
    void restControllers_ShouldNotDirectlyDependOnRepositories() {
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that().resideInAPackage(CONTROLLER_LAYER_PACKAGE)
                .should().dependOnClassesThat()
                .areAnnotatedWith(Repository.class);

        rule.check(classes);
    }

    @Test
    @DisplayName("Public methods in Rest Controller classes should be annotated with a request mapping annotation")
    void publicRestControllerMethods_ShouldBeAnnotatedWithARequestMapping() {
        ArchRule rule = ArchRuleDefinition.methods()
                .that().arePublic()
                .and().areDeclaredInClassesThat().areAnnotatedWith(RestController.class)
                .should().beAnnotatedWith(RequestMapping.class)
                .orShould().beAnnotatedWith(GetMapping.class)
                .orShould().beAnnotatedWith(PostMapping.class)
                .orShould().beAnnotatedWith(PutMapping.class)
                .orShould().beAnnotatedWith(DeleteMapping.class);

        rule.check(classes);
    }

    @Test
    @DisplayName("Public methods in Rest Controller classes should not return JPA entities")
    void publicRestControllerMethods_ShouldNotReturnEntities() {
        ArchRule rule = ArchRuleDefinition.methods()
                .that().arePublic()
                .and().areDeclaredInClassesThat().areAnnotatedWith(RestController.class)
                .should().notHaveRawReturnType(Entity.class);

        rule.check(classes);
    }

    @Test
    @DisplayName("Classes annotated with @RestController should be suffixed with 'Controller'")
    void restControllerClasses_ShouldBeSuffixed() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().resideInAPackage(CONTROLLER_LAYER_PACKAGE)
                .and().areAnnotatedWith(RestController.class)
                .should().haveSimpleNameEndingWith("Controller");

        rule.check(classes);
    }
}
