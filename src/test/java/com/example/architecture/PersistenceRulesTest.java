package com.example.architecture;

import com.example.ArchitectureTestFixtures;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public class PersistenceRulesTest extends ArchitectureTestFixtures {

    @Test
    void repositoryClasses_ShouldBeExtendJpaRepository() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().areAnnotatedWith(Repository.class)
                .should().beAssignableTo(JpaRepository.class);

        rule.check(classes);
    }

    @Test
    void repositoryClasses_ShouldBeInterfaces() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().haveSimpleNameEndingWith("Repository")
                .should().beInterfaces();

        rule.check(classes);
    }

    @Test
    void repositories_ShouldOnlyBeAccessedByServiceLayer() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().areAnnotatedWith(Repository.class)
                .should().onlyBeAccessed()
                .byAnyPackage(SERVICE_LAYER_PACKAGE, PERSISTENCE_LAYER_PACKAGE);

        rule.check(classes);
    }

    @Test
    void repositoryClasses_ShouldBeSuffixed() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().resideInAPackage(PERSISTENCE_LAYER_PACKAGE)
                .and().areAnnotatedWith(Repository.class)
                .should().haveSimpleNameEndingWith("Repository");

        rule.check(classes);
    }
}
