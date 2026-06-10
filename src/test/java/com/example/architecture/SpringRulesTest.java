package com.example.architecture;

import com.example.ArchitectureTestFixtures;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import lombok.experimental.UtilityClass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

public class SpringRulesTest extends ArchitectureTestFixtures {

    @Test
    @DisplayName("Singleton Spring components should only have final fields")
    void singletonComponents_ShouldOnlyHaveFinalFields() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().areAnnotatedWith(Component.class)
                .or().areAnnotatedWith(ConfigurationProperties.class)
                .or().areAnnotatedWith(RestController.class)
                .or().areAnnotatedWith(RestControllerAdvice.class)
                .or().areAnnotatedWith(Repository.class)
                .or().areAnnotatedWith(Service.class)
                .should().haveOnlyFinalFields();

        rule.check(classes);
    }

    @Test
    @DisplayName("Field dependency injection should not be used in Spring components")
    void fieldDependencyInjection_ShouldNotBeUsed() {
        ArchRule rule = ArchRuleDefinition.noFields()
                .should().beAnnotatedWith(Autowired.class);

        rule.check(classes);
    }
}
