package com.swag.apollo;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;

public class ArchitectureRulesTest {

   // JavaClasses importedClasses = new ClassFileImporter().importPackages("com.swag.myapp");  // replace with your app package

    @Test
    void controllers_should_not_access_repositories_directly() {
    	  // Path to target project's compiled classes (update this)
        File classesDir = new File("D:\\GIT\\spring-boot-rest-api-antipatterns\\target\\classes");

        JavaClasses importedClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPath(classesDir.toPath());
    	
        // Define the rule: Controllers should not access repositories directly
        ArchRule rule = noClasses()
        	    .that().resideInAPackage("..controller..")
        	    .should().accessClassesThat().resideInAPackage("..repository..")
        	    //.allowEmptyShould(true)
        	    .because("controller should not directly access repositories");
        rule.allowEmptyShould(true).check(importedClasses);

    }
    
    @Test
    void services_should_not_access_repositories_directly() {
    	  // Path to target project's compiled classes (update this)
        File classesDir = new File("D:\\GIT\\spring-boot-rest-api-antipatterns\\target\\classes");

        JavaClasses importedClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPath(classesDir.toPath());
    	
        // Define the rule: Controllers should not access repositories directly
        ArchRule rule = noClasses()
        	    .that().resideInAPackage("..service..")
        	    .should().accessClassesThat().resideInAPackage("..repository..")
        	    //.allowEmptyShould(true)
        	    .because("services should not directly access repositories");
        rule.allowEmptyShould(true).check(importedClasses);

    }
}
