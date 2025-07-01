package com.swag.apollo;

import com.swag.apollo.analyzer.JavaRiskAnalyzer;
import com.swag.apollo.analyzer.ReactRiskAnalyzer;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java -jar scanner.jar <language> <directory>");
            System.err.println("Example: java -jar scanner.jar java /repo/src/main/java");
            System.exit(1);
        }

        String language = args[0];
        String directory = args[1];
        int riskScore = 0;

        try {
            switch (language.toLowerCase()) {
                case "java":
                    riskScore = JavaRiskAnalyzer.analyzeAndScore(directory);
                    break;
                case "react":
                    ReactRiskAnalyzer.analyzeAndScore(directory);
                    break;
                default:
                    System.err.println("❌ Unsupported language: " + language);
                    System.exit(1);
            }

            System.out.println("🧠 Total Risk Score: " + riskScore);
            if (riskScore >= 200) {
                System.out.println("❌ Merge blocked due to high risk.");
                System.exit(1); // Fail the pipeline
            } else if (riskScore > 100) {
                System.out.println("⚠ Moderate risk. Review recommended.");
            } else {
                System.out.println("✅ Codebase is AI-ready.");
            }

        } catch (IOException e) {
            System.err.println("❌ Error during scanning: " + e.getMessage());
            System.exit(1);
        }
    }
}
