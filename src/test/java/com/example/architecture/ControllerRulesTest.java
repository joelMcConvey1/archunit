package com.example.architecture;

import com.example.ArchitectureTestFixtures;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import jakarta.persistence.Entity;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

public class ControllerRulesTest extends ArchitectureTestFixtures {

    @Test
    void controllerClasses_ShouldBeAnnotatedWith_RestControllerAnnotation() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().haveSimpleNameEndingWith("Controller")
                .should().beAnnotatedWith(RestController.class);

        rule.check(classes);
    }

    @Test
    void noRestControllerClasses_ShouldResideOutsideDesignatedPackage() {
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that().haveSimpleNameEndingWith("Controller")
                .or().areAnnotatedWith(RestController.class)
                .should().resideOutsideOfPackages(CONTROLLER_LAYER_PACKAGE);

        rule.check(classes);
    }

    @Test
    void restControllerClasses_ShouldNotDependOnEachOther() {
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that().areAnnotatedWith(RestController.class)
                .should().dependOnClassesThat()
                .resideInAPackage(CONTROLLER_LAYER_PACKAGE);

        rule.check(classes);
    }

    @Test
    void restControllers_ShouldNotDirectlyDependOnRepositories() {
        ArchRule rule = ArchRuleDefinition.noClasses()
                .that().resideInAPackage(CONTROLLER_LAYER_PACKAGE)
                .should().dependOnClassesThat()
                .areAnnotatedWith(Repository.class);

        rule.check(classes);
    }

    @Test
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
    void publicRestControllerMethods_ShouldNotReturnEntities() {
        ArchRule rule = ArchRuleDefinition.methods()
                .that().arePublic()
                .and().areDeclaredInClassesThat().areAnnotatedWith(RestController.class)
                .should().notHaveRawReturnType(Entity.class);

        rule.check(classes);
    }

    @Test
    void restControllerClasses_ShouldBeSuffixed() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().resideInAPackage(CONTROLLER_LAYER_PACKAGE)
                .and().areAnnotatedWith(RestController.class)
                .should().haveSimpleNameEndingWith("Controller");

        rule.check(classes);
    }
}
