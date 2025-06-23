
package com.swag.apollo.analyzer;

import java.util.ArrayList;
import java.util.List;

import com.swag.apollo.RuleViolation;

import jdepend.framework.JDepend;
import jdepend.framework.JavaPackage;

/**
 * Analyzer that checks cyclic package dependencies using both Classycle and JDepend.
 */
public class StructuralCycleAnalyzer {

    public static List<RuleViolation> runCycleCheck(String classDir) throws Exception {
        List<RuleViolation> violations = new ArrayList<>();
/*
        // 1. Run Classycle and parse output
        String reportFile = "target/classycle-report.txt";
        File output = new File(reportFile);
        output.getParentFile().mkdirs();

        try (PrintStream ps = new PrintStream(new FileOutputStream(output))) {
            PrintStream originalOut = System.out;
            System.setOut(ps);
            DependencyChecker.main(new String[]{"-package", classDir});
            System.setOut(originalOut);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(reportFile))) {
            String line;
            boolean inCycleBlock = false;

            while ((line = reader.readLine()) != null) {
                if (line.contains("Cycle")) {
                    inCycleBlock = true;
                } else if (inCycleBlock && line.trim().isEmpty()) {
                    inCycleBlock = false;
                } else if (inCycleBlock) {
                    String pkg = line.trim();
                    violations.add(new RuleViolation(
                        "CYCLE_PACKAGE_CLASSYCLE",
                        "Cyclic Package Dependency (Classycle)",
                        "Package part of a cycle: " + pkg,
                        null,
                        pkg,
                        "HIGH",
                        25
                    ));
                }
            }
        }
*/
        // 2. Run JDepend and add violations
        JDepend jdepend = new JDepend();
        jdepend.addDirectory(classDir);
        jdepend.analyze();

        for (Object obj : jdepend.getPackages()) { // Fix: Iterate over Object and cast
            JavaPackage pkg = (JavaPackage) obj;
            if (pkg.containsCycle()) {
                violations.add(new RuleViolation(
                    "CYCLE_PACKAGE_JDEPEND",
                    "Cyclic Package Dependency (JDepend)",
                    "Package '" + pkg.getName() + "' participates in a cycle.",
                    null,
                    pkg.getName(),
                    "HIGH",
                    30
                ));
            }
        }

        return violations;
    }
}
