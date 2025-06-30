package com.swag.apollo.util;

public class RuleViolation {
    private String type;
    private String title;
    private String description;
    private String filePath;
    private String elementName;
    private String severity;
    private int score;

    // Constructor
    public RuleViolation(String type, String title, String description, String filePath,
                         String elementName, String severity, int score) {
        this.type = type;
        this.title = title;
        this.description = description;
        this.filePath = filePath;
        this.elementName = elementName;
        this.severity = severity;
        this.score = score;
    }

    // Getters and toString()
    @Override
    public String toString() {
        return String.format(
            "Type: %s\nTitle: %s\nDescription: %s\nFile: %s\nElement: %s\nSeverity: %s\nScore: %d\n",
            type, title, description,
            filePath == null ? "N/A" : filePath,
            elementName == null ? "N/A" : elementName,
            severity, score
        );
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

}

