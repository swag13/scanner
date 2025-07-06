package com.swag.apollo.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ViolationFormatter {

    public static Map<String, List<RuleViolation>> groupByCategory(List<RuleViolation> violations) {
        return violations.stream()
            .collect(Collectors.groupingBy(RuleViolation::getCategory));
    }

    public static int calculateTotalRiskScore(List<RuleViolation> violations) {
        return violations.stream()
            .mapToInt(RuleViolation::getScore)
            .sum();
    }

    public static Map<String, Object> generateRiskReport(List<RuleViolation> violations) {
        Map<String, Object> report = new LinkedHashMap<>();
        report.put("totalRiskScore", calculateTotalRiskScore(violations));
        report.put("riskLevel", getRiskLevel(calculateTotalRiskScore(violations)));
        report.put("violationsByCategory", groupByCategory(violations));
        return report;
    }

    public static String getRiskLevel(int score) {
        if (score >= 200) return "HIGH";
        if (score > 100) return "MODERATE";
        return "LOW";
    }

    public static void writeJsonReport(List<RuleViolation> violations, String outputPath) {
        Map<String, Object> report = generateRiskReport(violations);

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            mapper.writeValue(new File(outputPath), report);
            System.out.println("📄 Risk report written to: " + outputPath);
        } catch (IOException e) {
            System.err.println("❌ Failed to write JSON report: " + e.getMessage());
        }
    }
    
    public static String violationsToGroupedMarkdown(List<RuleViolation> violations) {
        Map<String, List<RuleViolation>> grouped = violations.stream()
            .collect(Collectors.groupingBy(RuleViolation::getCategory));

        StringBuilder md = new StringBuilder("# 🧠 AI Risk Report\n\n");

        for (Map.Entry<String, List<RuleViolation>> entry : grouped.entrySet()) {
            md.append("## ").append(entry.getKey()).append(" Violations\n\n");
            for (RuleViolation v : entry.getValue()) {
                md.append("- **").append(v.getTitle()).append("** (`").append(v.getSeverity()).append("`, ")
                  .append(v.getScore()).append(" pts): ").append(v.getDescription()).append("  \n")
                  .append("  ↪️ File: `").append(v.getFilePath()).append("`, Element: `")
                  .append(v.getElementName()).append("`\n\n");
            }
        }

        return md.toString();
    }

    public static String violationsToGroupedHtml(List<RuleViolation> violations) {
        Map<String, List<RuleViolation>> grouped = violations.stream()
            .collect(Collectors.groupingBy(RuleViolation::getCategory));

        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>AI Risk Report</title></head><body>");
        html.append("<h1>🧠 AI Risk Report</h1>");

        for (Map.Entry<String, List<RuleViolation>> entry : grouped.entrySet()) {
            html.append("<h2>").append(entry.getKey()).append(" Violations</h2><ul>");
            for (RuleViolation v : entry.getValue()) {
                html.append("<li><b>").append(v.getTitle()).append("</b> (")
                    .append(v.getSeverity()).append(", ").append(v.getScore()).append(" pts): ")
                    .append(v.getDescription()).append("<br>")
                    .append("📄 <code>").append(v.getFilePath()).append("</code><br>")
                    .append("🔧 Element: <code>").append(v.getElementName()).append("</code></li>");
            }
            html.append("</ul>");
        }

        html.append("</body></html>");
        return html.toString();
    }

    public static void writeMarkdownReport(List<RuleViolation> violations, String path) throws IOException {
        String md = violationsToGroupedMarkdown(violations);
        Files.writeString(Path.of(path), md);
    }

    public static void writeHtmlReport(List<RuleViolation> violations, String path) throws IOException {
        String html = violationsToGroupedHtml(violations);
        Files.writeString(Path.of(path), html);
    }

}
