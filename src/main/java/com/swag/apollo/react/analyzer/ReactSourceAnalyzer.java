package com.swag.apollo.react.analyzer;


import java.io.*;
import java.nio.file.*;
import org.json.JSONObject;

public class ReactSourceAnalyzer {

    public static JSONObject runReactScanner(String reactPath) {
        try {
            String outputFile = "react_scan_results.json";
            ProcessBuilder pb = new ProcessBuilder("python3", "react_ai_blindspot_scanner.py", reactPath, "--output", outputFile);
            pb.directory(new File("path/to/python/scanner")); // Update path
            pb.redirectErrorStream(true);
            Process process = pb.start();

            // Print output from Python script
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                String jsonContent = Files.readString(Paths.get("path/to/python/scanner", outputFile));
                return new JSONObject(jsonContent);
            } else {
                System.err.println("React AI scanner failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }
}
