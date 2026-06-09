package com.example.architecture;

import com.example.ArchitectureTestFixtures;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RestControllerAdvice;

public class HandlerRulesTest extends ArchitectureTestFixtures {

    @Test
    void classesAnnotatedWithRestControllerAdvice_ShouldOnlyResideInHandlerPackage() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().areAnnotatedWith(RestControllerAdvice.class)
                .should().resideInAPackage(HANDLER_PACKAGE);

        rule.check(classes);
    }

    @Test
    void handlerClasses_ShouldOnlyResideInHandlerPackage() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().haveSimpleNameEndingWith("Handler")
                .or().haveSimpleNameEndingWith("ExceptionHandler")
                .should().resideInAPackage(HANDLER_PACKAGE);

        rule.check(classes);
    }
}
