package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter("reports/ExtentReport.html");
            spark.config().setDocumentTitle("Automation Report");
            spark.config().setReportName("Selenium Test Execution");
            spark.config().setTheme(Theme.DARK);

            extent = new ExtentReports();
            extent.attachReporter(spark);

            // Add system/environment info
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("Tester", "Anjum");
        }
        return extent;
    }
}
