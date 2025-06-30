package com.swag.apollo.util;

import java.util.List;

public class RiskScorer {
	    public static int calculate(List<RuleViolation> violations) {
	        int score = 0;
	        for (RuleViolation v : violations) {
	                 if ("CYCLE_DEPENDENCY".equals(v.getTitle())) {
	                     score += 20;
	                 }
	                 if ("CTRL_REPO_DIRECT".equals(v.getTitle())) {
	                     score += 30;
	                 }
	                 // Add more rules here...
	             }
	            //if (v.type.equals("Cyclic dependency")) score += 20;
	            //if (v.type.contains("controller accesses repository")) score += 30;
	            // Add weights
	        return score;
	    }
	}


