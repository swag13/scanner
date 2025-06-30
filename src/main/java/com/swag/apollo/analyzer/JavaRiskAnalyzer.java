package com.swag.apollo.analyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.json.JSONObject;

import com.swag.apollo.react.analyzer.ReactSourceAnalyzer;
import com.swag.apollo.util.ReportUtil;
import com.swag.apollo.util.RuleViolation;

public class JavaRiskAnalyzer {

    /**
     * Performs structure analysis and risk scoring.
     *
     * @param compiledDir  path to compiled classes (e.g., target/classes)
     * @param basePackage  base package to scan (e.g., com.example)
     * @return total risk score based on weighted violations
     * @throws IOException 
     */
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
            score += violation.getScore(); // Or apply a more complex scoring logic
        }
        
        // Console output
        ReportUtil.printViolationReport(violations);

        // Optional: Get JSON or Markdown output
        String json = ReportUtil.violationsToJson(violations);
        String markdown = ReportUtil.violationsToMarkdown(violations);
        String html = ReportUtil.violationsToHtml(violations);

        // Save to file (optional)
        Files.writeString(Path.of("target/violations.json"), json);
        Files.writeString(Path.of("target/violations.md"), markdown);
        Files.writeString(Path.of("target/violations.html"), html);

        System.out.println("\n🧠 Total Risk Score: " + score);
        return score;
    }
}
