package com.MCP_Automation.reporting;

import com.MCP_Automation.utils.SeleniumUtil;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Custom Test Report class for generating HTML and text reports
 */
public class TestReport {
    
    private String reportName;
    private String reportDir;
    private List<TestLog> testLogs;
    private int passCount;
    private int failCount;
    private long startTime;
    private long endTime;
    
    /**
     * Constructor
     * @param reportName - Name of the report
     */
    public TestReport(String reportName) {
        this.reportName = reportName;
        this.reportDir = "test-reports/";
        this.testLogs = new ArrayList<>();
        this.passCount = 0;
        this.failCount = 0;
        this.startTime = System.currentTimeMillis();
        
        // Create report directory
        new File(reportDir).mkdirs();
    }
    
    /**
     * Log a test step
     * @param stepName - Name of the step
     * @param status - Status (PASS, FAIL, INFO)
     * @param description - Step description
     */
    public void logStep(String stepName, String status, String description) {
        TestLog log = new TestLog(stepName, status, description, SeleniumUtil.getFormattedTimestamp());
        testLogs.add(log);
        
        if ("PASS".equals(status)) {
            passCount++;
        } else if ("FAIL".equals(status)) {
            failCount++;
        }
        
        System.out.println("[" + status + "] " + stepName + " - " + description);
    }
    
    /**
     * Log test pass
     * @param stepName - Name of the step
     * @param description - Step description
     */
    public void logPass(String stepName, String description) {
        logStep(stepName, "PASS", description);
    }
    
    /**
     * Log test fail
     * @param stepName - Name of the step
     * @param description - Step description
     */
    public void logFail(String stepName, String description) {
        logStep(stepName, "FAIL", description);
    }
    
    /**
     * Log test info
     * @param stepName - Name of the step
     * @param description - Step description
     */
    public void logInfo(String stepName, String description) {
        logStep(stepName, "INFO", description);
    }
    
    /**
     * Generate HTML report
     */
    public void generateHTMLReport() {
        endTime = System.currentTimeMillis();
        long duration = (endTime - startTime) / 1000;
        
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html>\n");
        html.append("<head>\n");
        html.append("  <title>").append(reportName).append(" - Test Report</title>\n");
        html.append("  <style>\n");
        html.append("    body { font-family: Arial, sans-serif; margin: 20px; background-color: #f5f5f5; }\n");
        html.append("    .header { background-color: #2c3e50; color: white; padding: 20px; border-radius: 5px; }\n");
        html.append("    .summary { margin: 20px 0; padding: 15px; background-color: #ecf0f1; border-radius: 5px; }\n");
        html.append("    .pass { background-color: #d4edda; color: #155724; padding: 10px; margin: 5px 0; border-radius: 3px; }\n");
        html.append("    .fail { background-color: #f8d7da; color: #721c24; padding: 10px; margin: 5px 0; border-radius: 3px; }\n");
        html.append("    .info { background-color: #d1ecf1; color: #0c5460; padding: 10px; margin: 5px 0; border-radius: 3px; }\n");
        html.append("    table { width: 100%; border-collapse: collapse; margin-top: 20px; }\n");
        html.append("    th, td { border: 1px solid #bdc3c7; padding: 12px; text-align: left; }\n");
        html.append("    th { background-color: #34495e; color: white; }\n");
        html.append("    tr:nth-child(even) { background-color: #ecf0f1; }\n");
        html.append("  </style>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        
        html.append("  <div class='header'>\n");
        html.append("    <h1>").append(reportName).append(" - Test Report</h1>\n");
        html.append("    <p>Generated: ").append(SeleniumUtil.getFormattedTimestamp()).append("</p>\n");
        html.append("  </div>\n");
        
        html.append("  <div class='summary'>\n");
        html.append("    <h2>Test Summary</h2>\n");
        html.append("    <p><strong>Total Steps:</strong> ").append(testLogs.size()).append("</p>\n");
        html.append("    <p><strong>Passed:</strong> ").append(passCount).append("</p>\n");
        html.append("    <p><strong>Failed:</strong> ").append(failCount).append("</p>\n");
        html.append("    <p><strong>Duration:</strong> ").append(duration).append(" seconds</p>\n");
        html.append("  </div>\n");
        
        html.append("  <h2>Test Steps</h2>\n");
        html.append("  <table>\n");
        html.append("    <tr><th>Step Name</th><th>Status</th><th>Description</th><th>Timestamp</th></tr>\n");
        
        for (TestLog log : testLogs) {
            String rowClass = log.status.equals("PASS") ? "pass" : (log.status.equals("FAIL") ? "fail" : "info");
            html.append("    <tr class='").append(rowClass).append("'>\n");
            html.append("      <td>").append(log.stepName).append("</td>\n");
            html.append("      <td>").append(log.status).append("</td>\n");
            html.append("      <td>").append(log.description).append("</td>\n");
            html.append("      <td>").append(log.timestamp).append("</td>\n");
            html.append("    </tr>\n");
        }
        
        html.append("  </table>\n");
        html.append("</body>\n");
        html.append("</html>\n");
        
        // Write to file
        String reportPath = reportDir + reportName + "_" + SeleniumUtil.getTimestamp() + ".html";
        try (FileWriter fw = new FileWriter(reportPath)) {
            fw.write(html.toString());
            System.out.println("HTML Report generated at: " + reportPath);
        } catch (IOException e) {
            System.err.println("Failed to generate HTML report: " + e.getMessage());
        }
    }
    
    /**
     * Generate text report
     */
    public void generateTextReport() {
        endTime = System.currentTimeMillis();
        long duration = (endTime - startTime) / 1000;
        
        StringBuilder text = new StringBuilder();
        text.append("=================================================\n");
        text.append("TEST REPORT: ").append(reportName).append("\n");
        text.append("=================================================\n");
        text.append("Generated: ").append(SeleniumUtil.getFormattedTimestamp()).append("\n");
        text.append("Duration: ").append(duration).append(" seconds\n");
        text.append("-------------------------------------------------\n");
        text.append("SUMMARY\n");
        text.append("-------------------------------------------------\n");
        text.append("Total Steps: ").append(testLogs.size()).append("\n");
        text.append("Passed: ").append(passCount).append("\n");
        text.append("Failed: ").append(failCount).append("\n");
        text.append("-------------------------------------------------\n");
        text.append("TEST STEPS\n");
        text.append("-------------------------------------------------\n");
        
        for (TestLog log : testLogs) {
            text.append("[").append(log.status).append("] ").append(log.stepName).append("\n");
            text.append("  Description: ").append(log.description).append("\n");
            text.append("  Time: ").append(log.timestamp).append("\n");
        }
        
        text.append("=================================================\n");
        
        // Write to file
        String reportPath = reportDir + reportName + "_" + SeleniumUtil.getTimestamp() + ".txt";
        try (FileWriter fw = new FileWriter(reportPath)) {
            fw.write(text.toString());
            System.out.println("Text Report generated at: " + reportPath);
        } catch (IOException e) {
            System.err.println("Failed to generate text report: " + e.getMessage());
        }
    }
    
    /**
     * Get pass count
     * @return - Number of passed tests
     */
    public int getPassCount() {
        return passCount;
    }
    
    /**
     * Get fail count
     * @return - Number of failed tests
     */
    public int getFailCount() {
        return failCount;
    }
    
    /**
     * Inner class for test log
     */
    private static class TestLog {
        String stepName;
        String status;
        String description;
        String timestamp;
        
        TestLog(String stepName, String status, String description, String timestamp) {
            this.stepName = stepName;
            this.status = status;
            this.description = description;
            this.timestamp = timestamp;
        }
    }
}
