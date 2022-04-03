package listener;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.util.Date;

public class ExtentManager {
    static ExtentReports extent;

    public static ExtentReports getReportObject() {
        String fileName = "Engine.html";
        String directory = System.getProperty("user.dir") + "/REPORTS/";
        String path = directory + fileName;
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("ClarityTTS Engine Automation");
        reporter.config().setDocumentTitle("Test Results");

        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Tester", "Pandian Angaiah");
        return extent;

    }

    public static String getReportName() {
        Date d = new Date();
        String filename = "Automation Report_" + d.toString().replace(":", "-").replace(" ", "-");
        return filename;

    }
}

