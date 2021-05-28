package com.TestFrameWork;


import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.xml.XmlSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentTestNGIReporterListener implements IReporter {

	private static final String OUTPUT_FOLDER = System.getProperty("user.dir") + "/" + "reports/";
	private static final String FILE_NAME = "Extent.html";

	private ExtentReports extent;
	static ExtentTest test;

	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		// TODO Auto-generated method stub
		init();

		for (ISuite suite : suites) {
			Map<String, ISuiteResult> suiteResults = suite.getResults();

			for (ISuiteResult r : suiteResults.values()) {
				ITestContext context = r.getTestContext();

				buildTestNodes(context.getFailedTests(), Status.FAIL);
				buildTestNodes(context.getSkippedTests(), Status.SKIP);
				buildTestNodes(context.getPassedTests(), Status.PASS);

			}
		}

		extent.flush();
	}

	private void init() {
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(OUTPUT_FOLDER + FILE_NAME);
		htmlReporter.config().setDocumentTitle("Project Report");
		htmlReporter.config().setReportName(" Created by Deepak Gill");
		htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
		htmlReporter.config().setTheme(Theme.STANDARD);

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setReportUsesManualConfiguration(true);
	}

	private void buildTestNodes(IResultMap tests, Status status) {
		ExtentTest test;

		if (tests.size() > 0) {
			for (ITestResult result : tests.getAllResults()) {
				System.out.println("TestName" + result.getMethod().getMethodName());
				test = extent.createTest(result.getMethod().getMethodName());

				for (String group : result.getMethod().getGroups()) {
					System.out.println("Group Name" + group);
					test.assignCategory(group);
					System.out.println("Group Name");
				}

				System.out.println("Result Name" + result.getName());
				System.out.println("Status of test " + test.getStatus().name());
				if (result.getThrowable() != null && result.getStatus() == ITestResult.FAILURE) {
					List<String> testngReporterLogs = Reporter.getOutput(result);
					for (String testngLog : testngReporterLogs) {
						test.log(status, testngLog);
					}
					test.log(status, result.getThrowable());
				} else if (result.getThrowable() == null) {
					List<String> testngReporterLogs = Reporter.getOutput(result);
					for (String testngLog : testngReporterLogs) {
						test.log(status, testngLog);
					}
					test.log(status, "Test " + status.toString().toLowerCase() + "ed Successfully");
				} else {
					List<String> testngReporterLogs = Reporter.getOutput(result);
					for (String testngLog : testngReporterLogs) {
						test.log(status, testngLog);
					}
					test.log(status, "Test " + status.toString().toLowerCase() + "ed ");
				}

				// extent.flush();
				test.getModel().setStartTime(getTime(result.getStartMillis()));
				test.getModel().setEndTime(getTime(result.getEndMillis()));
			}
		}
	}

	private Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}

}