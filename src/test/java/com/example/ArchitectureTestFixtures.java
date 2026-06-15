package com.example;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.BeforeAll;

public abstract class ArchitectureTestFixtures {

    public static final String CONTROLLER_LAYER_PACKAGE = "com.example.controller..";
    public static final String SERVICE_LAYER_PACKAGE = "com.example.service..";
    public static final String PERSISTENCE_LAYER_PACKAGE = "com.example.persistence..";

    public static final String CUSTOM_EXCEPTION_PACKAGE = "com.example.exception..";
    public static final String HANDLER_PACKAGE = "com.example.handler..";
    public static final String MODEL_PACKAGE = "com.example.model..";

    public static JavaClasses classes;

    @BeforeAll
    public static void setUp() {
        classes = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_ARCHIVES)
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
                .importPackages("com.example");
    }
}
