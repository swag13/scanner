package com.swag.apollo.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.json.JSONObject;

import com.swag.apollo.RuleViolation;

public class RiskReportGenerator {
	    public static void generateReport(List<RuleViolation> violations, int score) throws IOException {
	        JSONObject report = new JSONObject();
	        report.put("score", score);
	        report.put("violations", violations);
	        Files.writeString(Path.of("output/risk-score.json"), report.toString(2));
	    }
	}


