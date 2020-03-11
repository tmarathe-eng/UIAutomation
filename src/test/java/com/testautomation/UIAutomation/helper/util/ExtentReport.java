package com.testautomation.UIAutomation.helper.util;

import static com.testautomation.UIAutomation.helper.util.CommonUtil.TestCaseDataMap;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author tm0338
 * Contains methods to help with generating HTML report using extent report library
 */
public class ExtentReport {

	//Extent Report variables
	public static ExtentReports Report;
	public static ExtentTest ReportLog;
	public static String SuccessMessage;
	public static String FailureMessage;
	
	//Initializes ExtentTest object at start of test execution
	public static void startTestReporting(String testCaseName, String testCaseDescription) {
		ReportLog = Report.startTest(testCaseName, testCaseDescription);
	}

	//Allows to start test report with data for the current test being executed
	public static void initializeTest(Method m, Logger TEST_LOG) {
		startTestReporting(TestCaseDataMap.get(m.getName())[0], TestCaseDataMap.get(m.getName())[1]);
		ExtentReport.SuccessMessage = "";
		ExtentReport.FailureMessage = "";
		TEST_LOG.debug("Test Case ID: " + TestCaseDataMap.get(m.getName())[0]);
		TEST_LOG.debug("Test Case Description: " + TestCaseDataMap.get(m.getName())[1]);
		TEST_LOG.debug("Test Case Success Message: " + TestCaseDataMap.get(m.getName())[2]);
		TEST_LOG.debug("Test Case Failure Message: " + TestCaseDataMap.get(m.getName())[3]);
	}
	
	//Allows to print test results to html report after test is completed
	public static void finishTestReporting(int testResultStatus, String resultMsg) {
		if (testResultStatus == ITestResult.SUCCESS) { ExtentReport.ReportLog.log(LogStatus.PASS, resultMsg); }
		if (testResultStatus == ITestResult.FAILURE) { ExtentReport.ReportLog.log(LogStatus.FAIL, resultMsg); }
		ExtentReport.Report.endTest(ExtentReport.ReportLog);
		ExtentReport.Report.flush();
	}
	
	//Allows to add screenshot at the end of test completion along with test results
	public static void reportTestResults(WebDriver driver,ITestResult result) {
		String s = ExtentReport.ReportLog.addScreenCapture(CommonUtil.TakeScreenshot(driver,result.getName()));
		finishTestReporting(result.getStatus(), (result.getStatus() == ITestResult.SUCCESS) ?
								(TestCaseDataMap.get(result.getMethod().getMethodName())[2] + s) : 
								(TestCaseDataMap.get(result.getMethod().getMethodName())[3] + s) );
	}
}
