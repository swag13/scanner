package com.swag.apollo.util;

public class RuleViolation {
    private String type;
    private String title;
    private String description;
    private String filePath;
    private String elementName;
    private String severity;
    private int score;
    private String category;
    private String remediationTip; // ✅ new field

    // Constructor with category
    public RuleViolation(String type, String title, String description, String filePath,
                         String elementName, String severity, int score, String category) {
        this.type = type;
        this.title = title;
        this.description = description;
        this.filePath = filePath;
        this.elementName = elementName;
        this.severity = severity;
        this.score = score;
        this.category = category;
        
    }

    // Optional backward-compatible constructor
    public RuleViolation(String type, String title, String description, String filePath,
                         String elementName, String severity, int score) {
        this(type, title, description, filePath, elementName, severity, score, "Uncategorized");
    }

    // Getters
    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getElementName() {
        return elementName;
    }

    public String getSeverity() {
        return severity;
    }

    public int getScore() {
        return score;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return String.format(
            "Category: %s\nType: %s\nTitle: %s\nDescription: %s\nFile: %s\nElement: %s\nSeverity: %s\nScore: %d\nTip: %s\n",
            category,
            type, title, description,
            filePath == null ? "N/A" : filePath,
            elementName == null ? "N/A" : elementName,
            severity, score,
            remediationTip == null ? "No suggestion available." : remediationTip
        );
    }

	public void setRemediationTip(String remediationTip) {
		this.remediationTip = remediationTip;
	}
}
