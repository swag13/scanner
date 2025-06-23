package com.swag.apollo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.swag.apollo.analyzer.RiskAnalyzer;

public class Main {
	//public static void main(String[] args) {
		/*
		 * String compiledPath = "target/classes"; String basePackage = "com.example";
		 * // update to your base package
		 * 
		 * List<RuleViolation> violations = ArchitectureAnalyzer.analyze(compiledPath,
		 * basePackage);
		 * 
		 * if (violations.isEmpty()) {
		 * System.out.println("✅ No structural issues found."); } else {
		 * System.out.println("🚨 Violations:");
		 * violations.forEach(System.out::println); }
		 */

	    public static void main(String[] args) {
	        String compiledPath = "D:\\GIT\\spring-boot-rest-api-antipatterns\\target\\classes";
	        String basePackage = "com.swag.myapp"; // Change this to your base app package

	        int riskScore;
			try {
				riskScore = RiskAnalyzer.analyzeAndScore(compiledPath, basePackage);
				if (riskScore >= 50) {
		            System.out.println("❗ High architectural risk. Refactoring recommended.");
		        } else if (riskScore > 0) {
		            System.out.println("⚠ Medium risk. Monitor and refactor selectively.");
		        } else {
		            System.out.println("✅ Code structure looks clean.");
		        }
		        
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        
	        // Optionally, you can save the violations to a file or database
	        
	       
	    }
}
