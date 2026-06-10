package com.example.architecture;

import com.example.ArchitectureTestFixtures;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class LayeredArchitectureTest extends ArchitectureTestFixtures {

    @Test
    @DisplayName("Layers of the three-layer architecture should enforce boundaries between one another")
    void layersOfThreeLayerArchitecture_shouldEnforceLayerBoundaries() {
        ArchRule rule = layeredArchitecture().consideringAllDependencies()
                .layer("Controllers").definedBy(CONTROLLER_LAYER_PACKAGE)
                .layer("Services").definedBy(SERVICE_LAYER_PACKAGE)
                .layer("Persistence").definedBy(PERSISTENCE_LAYER_PACKAGE)

                .whereLayer("Controllers").mayNotBeAccessedByAnyLayer()
                .whereLayer("Services").mayOnlyBeAccessedByLayers("Controllers")
                .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Services");

        rule.check(classes);
    }
}
