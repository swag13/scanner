package com.swag.apollo.analyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.json.JSONObject;

import com.swag.apollo.react.analyzer.ReactSourceAnalyzer;

public class ReactRiskAnalyzer {
	   
	public static void analyzeAndScore(String basePackage) throws IOException {
    // 👇 Run React AI-blind spot scanner
       System.out.println("\n🧠 Starting React AI-blind spot analysis...");

       JSONObject reactScan = ReactSourceAnalyzer.runReactScanner(basePackage);  // Update path

       if (reactScan.length() > 0) {
           System.out.println("⚠️ React AI-blind spot issues found:");
           reactScan.keySet().forEach(file -> {
               System.out.println("📄 " + file);
               reactScan.getJSONArray(file).forEach(issue ->
                   System.out.println("   ⚠️ " + issue));
           });

           // Save React scan JSON
           Files.writeString(Path.of("target/react_ai_blindspots.json"), reactScan.toString(2));
       } else {
           System.out.println("✅ No AI-blind spots detected in React code.");
       }
	}
}
