package com.swag.apollo.util;

import java.util.Map;

public class ViolationRemediationTips {
    private static final Map<String, String> tips = Map.of(
        "NO_DI", "Refactor to use Spring Dependency Injection via @Autowired or constructor injection.",
        "SENSITIVE_LOG", "Avoid logging sensitive information; mask or omit sensitive data fields.",
        "NO_LOG_IN_CATCH", "Always log exceptions in catch blocks using a logger."
        // add more as needed
    );

    public static String getTip(String violationType) {
        return tips.getOrDefault(violationType, "Refer to project documentation for fix guidance.");
    }
}
