package com.swag.apollo.analyzer;

import java.io.IOException;
import java.util.List;

import com.swag.apollo.util.ReportUtil;
import com.swag.apollo.util.RuleViolation;
import com.swag.apollo.util.ViolationFormatter;
import com.swag.apollo.util.ViolationRemediationTips;

public class JavaRiskAnalyzer {

    public static int analyzeAndScore(String basePackage) throws IOException {
        List<RuleViolation> violations = JavaSourceAnalyzer.analyze(basePackage);

        if (violations.isEmpty()) {
            System.out.println("✅ No structural issues found.");
            return 0;
        }

        System.out.println("🔍 Found " + violations.size() + " structural issues.");
        System.out.println("📊 Risk analysis report:");
        System.out.println("🚨 Violations:");
        int score = 0;

        for (RuleViolation violation : violations) {
            System.out.println(violation);
            score += violation.getScore();
            
            String tip = ViolationRemediationTips.getTip(violation.getType());
            violation.setRemediationTip(tip);
            System.out.println("💡 Suggestion: " + tip + "\n");
        }

        // Console summary
        ReportUtil.printViolationReport(violations);

        // ✅ Use ViolationFormatter for JSON grouped report
        ViolationFormatter.writeJsonReport(violations, "target/risk-score.json");
        ViolationFormatter.writeMarkdownReport(violations, "target/violations.md");
        ViolationFormatter.writeHtmlReport(violations, "target/violations.html");


        System.out.println("\n🧠 Total Risk Score: " + score);
        return score;
    }
}
