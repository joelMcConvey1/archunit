package com.example.architecture;

import com.example.ArchitectureTestFixtures;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public class PersistenceRulesTest extends ArchitectureTestFixtures {

    @Test
    @DisplayName("Repository classes should extend JpaRepository")
    void repositoryClasses_ShouldExtendJpaRepository() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().areAnnotatedWith(Repository.class)
                .should().beAssignableTo(JpaRepository.class);

        rule.check(classes);
    }

    @Test
    @DisplayName("Repository classes should be interfaces")
    void repositoryClasses_ShouldBeInterfaces() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().haveSimpleNameEndingWith("Repository")
                .should().beInterfaces();

        rule.check(classes);
    }

    @Test
    @DisplayName("Repository interfaces should only be accessed by service layer")
    void repositoryInterfaces_ShouldOnlyBeAccessedByServiceLayer() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().areAnnotatedWith(Repository.class)
                .should().onlyBeAccessed()
                .byAnyPackage(SERVICE_LAYER_PACKAGE, PERSISTENCE_LAYER_PACKAGE);

        rule.check(classes);
    }

    @Test
    @DisplayName("Repository interfaces should be suffixed with 'Repository'")
    void repositoryInterfaces_ShouldBeSuffixed() {
        ArchRule rule = ArchRuleDefinition.classes()
                .that().resideInAPackage(PERSISTENCE_LAYER_PACKAGE)
                .and().areAnnotatedWith(Repository.class)
                .should().haveSimpleNameEndingWith("Repository");

        rule.check(classes);
    }
}
