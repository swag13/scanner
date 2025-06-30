package com.swag.apollo;

import java.io.IOException;

import com.swag.apollo.analyzer.JavaRiskAnalyzer;
import com.swag.apollo.analyzer.ReactRiskAnalyzer;

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
	        analyzeJavaRepo();
	        analyzeReactRepo();

	        
	        // Optionally, you can save the violations to a file or database
	        
	       
	    }

		private static void analyzeReactRepo() {
			String basePackage = "../react-project"; // Change this to your base app package

			try {
				ReactRiskAnalyzer.analyzeAndScore(basePackage);
			} catch (IOException e) {
				System.err.println("Error analyzing React code: " + e.getMessage());
			}
			
		}

		private static void analyzeJavaRepo() {
			String basePackage = "com.swag.myapp"; // Change this to your base app package

	        int riskScore;
			try {
				riskScore = JavaRiskAnalyzer.analyzeAndScore(basePackage);
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
		}
}
