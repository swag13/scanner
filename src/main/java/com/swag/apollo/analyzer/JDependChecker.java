
package com.swag.apollo.analyzer;

import java.util.ArrayList;
import java.util.List;

import com.swag.apollo.RuleViolation;

import jdepend.framework.JDepend;
import jdepend.framework.JavaPackage;

/**
 * Uses JDepend to detect cyclic package dependencies.
 */
public class JDependChecker {

    public static List<RuleViolation> run(String classDirPath) throws Exception {
        List<RuleViolation> violations = new ArrayList<>();

        JDepend jdepend = new JDepend();
        jdepend.addDirectory(classDirPath); // Fix: Pass String directly
        jdepend.analyze();

        for (Object obj : jdepend.getPackages()) { // Fix: Iterate over Object and cast
            JavaPackage pkg = (JavaPackage) obj;
            if (pkg.containsCycle()) { // Fix: Use containsCycle() to check for cycles
                String description = "Package '" + pkg.getName() + "' participates in a cycle.";
                violations.add(new RuleViolation(
                    "CYCLE_PACKAGE",
                    "Cyclic Package Dependency",
                    description,
                    null,
                    pkg.getName(),
                    "HIGH",
                    25
                ));
            }
        }

        return violations;
    }
}
