package com.swag.apollo.analyzer;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.swag.apollo.RuleViolation;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.EvaluationResult;

public class ArchRuleChecker {
	    public static List<RuleViolation> check(String dir, String basePackage) {
	    	JavaClasses classes = new ClassFileImporter().importPath(Paths.get(dir));
	    	
	        List<RuleViolation> violations = new ArrayList<>();

	        ArchRule rule = noClasses().that().resideInAPackage("..controller..")
	                        .should().accessClassesThat().resideInAPackage("..repository..")
	                        .allowEmptyShould(true);
	        EvaluationResult result = rule.evaluate(classes);

	        result.getFailureReport().getDetails().forEach(detail ->
	            violations.add(new RuleViolation("Controller accesses repository", detail, detail, detail, detail, detail, 20))
	        );

	        return violations;
	    }
	}
