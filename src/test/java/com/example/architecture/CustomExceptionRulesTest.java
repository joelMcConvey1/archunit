package com.example.architecture;

import com.example.ArchitectureTestFixtures;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.Test;

public class CustomExceptionRulesTest extends ArchitectureTestFixtures {

    @Test
    void exceptionClasses_ShouldResideInExceptionPackage() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().haveSimpleNameEndingWith("Exception")
                .should().resideInAPackage(CUSTOM_EXCEPTION_PACKAGE);

        rule.check(classes);
    }
}
