package com.example.architecture;

import com.example.ArchitectureTestFixtures;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CustomExceptionRulesTest extends ArchitectureTestFixtures {

    @Test
    @DisplayName("Custom exception classes should reside in the exception package")
    void customExceptionClasses_ShouldResideInExceptionPackage() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().haveSimpleNameEndingWith("Exception")
                .should().resideInAPackage(CUSTOM_EXCEPTION_PACKAGE);

        rule.check(classes);
    }
}
