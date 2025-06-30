package com.swag.apollo.analyzer;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.swag.apollo.java.analyzer.JavaSourceArchitectureScanner;
import com.swag.apollo.java.analyzer.SolidViolationDetector;
import com.swag.apollo.util.RuleViolation;

public class JavaSourceAnalyzer {

	/**
	 * Analyzes structure issues using both ArchUnit and Classycle.
	 *
	 * @param compiledDir  path to compiled classes (e.g., "target/classes")
	 * @param basePackage  base Java package to scan (e.g., "com.example")
	 * @return list of all structural rule violations
	 */
	public static List<RuleViolation> analyze(String basePackage) {
		List<RuleViolation> violations = new ArrayList<>();

		try {
			// 1. Add ArchUnit rule results
			//List<RuleViolation> archViolations = ArchRuleChecker.check(basePackage);
			//violations.addAll(archViolations);

			File targetProject = new File("D:\\GIT\\spring-boot-rest-api-antipatterns\\src\\main\\java\\com\\swag\\myapp");
			JavaSourceArchitectureScanner javaScanner = new JavaSourceArchitectureScanner();
			List<RuleViolation> javaViolations = javaScanner.run("D:\\GIT\\spring-boot-rest-api-antipatterns\\src\\main\\java\\com\\swag\\myapp");
			violations.addAll(javaViolations);

			// 2. Add  dependency cycles
			//List<RuleViolation> cycleViolations = StructuralCycleAnalyzer.runCycleCheck(compiledDir);
			//violations.addAll(cycleViolations);

			// 3. God class detector
			//List<RuleViolation> godClassViolations = GodClassDetector.run();
			// violations.addAll(godClassViolations);

			SolidViolationDetector detector = new SolidViolationDetector();
			List<RuleViolation> spViolations = detector.run(targetProject);
			violations.addAll(spViolations);

			// System.out.println("\n==== SOLID Principle Violations ====");
			//violations.forEach(System.out::println);


		} catch (Exception e) {
			System.err.println("❌ Error running analysis: " + e.getMessage());
			e.printStackTrace();
		}

		return violations;
	}
}
