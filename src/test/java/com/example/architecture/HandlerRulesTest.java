package com.example.architecture;

import com.example.ArchitectureTestFixtures;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RestControllerAdvice;

public class HandlerRulesTest extends ArchitectureTestFixtures {

    @Test
    @DisplayName("Classes annotated with @RestControllerAdvice should only reside in the handler package")
    void classesAnnotatedWithRestControllerAdvice_ShouldOnlyResideInHandlerPackage() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().areAnnotatedWith(RestControllerAdvice.class)
                .should().resideInAPackage(HANDLER_PACKAGE);

        rule.check(classes);
    }

    @Test
    @DisplayName("Classes with names ending in 'Handler' or 'ExceptionHandler' should only reside in the handler package")
    void exceptionHandlerClasses_ShouldOnlyResideInHandlerPackage() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().haveSimpleNameEndingWith("Handler")
                .or().haveSimpleNameEndingWith("ExceptionHandler")
                .should().resideInAPackage(HANDLER_PACKAGE);

        rule.check(classes);
    }
}
