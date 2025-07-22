package com.zemoso.seleniumTest.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            String path=System.getProperty("user.dir")+"//reports//ExtentReport.html";
            ExtentSparkReporter reporter = new ExtentSparkReporter(path);
            reporter.config().setReportName("Amazon Automation");
            reporter.config().setDocumentTitle("Web Test Results");

            extent = new ExtentReports();
            extent.attachReporter(reporter);

            extent.setSystemInfo("Project", "Amazon Automation");
            extent.setSystemInfo("Tester", "Harish");
            extent.setSystemInfo("Environment", "QA");
        }
        return extent;
    }
}

