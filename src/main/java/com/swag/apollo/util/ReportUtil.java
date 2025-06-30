package com.swag.apollo.util;


import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ReportUtil {

    // 1. Print to console (text)
    public static void printViolationReport(List<RuleViolation> violations) {
        System.out.println("=== Code Quality Violations Report ===");
        System.out.println("Total Violations: " + violations.size());
        System.out.println();

        for (RuleViolation v : violations) {
            System.out.println(v.toString());
            System.out.println("-----------------------------------");
        }
    }

    // 2. Export as JSON string
    public static String violationsToJson(List<RuleViolation> violations) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(violations);
    }

    // 3. Export as Markdown
    public static String violationsToMarkdown(List<RuleViolation> violations) {
        StringBuilder md = new StringBuilder();
        md.append("# Code Quality Violations Report\n\n");
        md.append("| Type | Title | Description | File | Element | Severity | Score |\n");
        md.append("|------|-------|-------------|------|---------|----------|-------|\n");

        for (RuleViolation v : violations) {
            md.append("| ")
              .append(v.getType()).append(" | ")
              .append(v.getTitle()).append(" | ")
              .append(v.getDescription()).append(" | ")
              .append(v.getFilePath() == null ? "N/A" : v.getFilePath()).append(" | ")
              .append(v.getElementName() == null ? "N/A" : v.getElementName()).append(" | ")
              .append(v.getSeverity()).append(" | ")
              .append(v.getScore()).append(" |\n");
        }

        return md.toString();
    }
    
    public static String violationsToHtml(List<RuleViolation> violations) {
   		StringBuilder html = new StringBuilder();
   		html.append("<h1>Code Quality Violations Report</h1>");
   		html.append("<p>Total Violations: ").append(violations.size()).append("</p>");
   		html.append("<table border='1'>");
   		html.append(
   				"<tr><th>Type</th><th>Title</th><th>Description</th><th>File</th><th>Element</th><th>Severity</th><th>Score</th></tr>");

   		for (RuleViolation v : violations) {
   			html.append("<tr>").append("<td>").append(v.getType()).append("</td>").append("<td>").append(v.getTitle())
   					.append("</td>").append("<td>").append(v.getDescription()).append("</td>").append("<td>")
   					.append(v.getFilePath() == null ? "N/A" : v.getFilePath()).append("</td>").append("<td>")
   					.append(v.getElementName() == null ? "N/A" : v.getElementName()).append("</td>").append("<td>")
   					.append(v.getSeverity()).append("</td>").append("<td>").append(v.getScore()).append("</td>").append("</tr>");
   		}
   		html.append("</table>");
   		return html.toString();
   	} 
}
