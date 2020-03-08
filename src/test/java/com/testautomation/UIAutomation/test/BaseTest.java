package com.testautomation.UIAutomation.test;

import static com.testautomation.UIAutomation.helper.CommonUtil.failureMessage;
import static com.testautomation.UIAutomation.helper.CommonUtil.report;
import static com.testautomation.UIAutomation.helper.CommonUtil.reportLog;
import static com.testautomation.UIAutomation.helper.CommonUtil.successMessage;
import static com.testautomation.UIAutomation.helper.CommonUtil.testCaseDataMap;

import java.io.File;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.relevantcodes.extentreports.LogStatus;
import com.testautomation.UIAutomation.helper.CommonUtil;
import com.testautomation.UIAutomation.helper.ExcelDataExtraction;
import com.testautomation.UIAutomation.page.BasePage;
import com.testautomation.UIAutomation.page.LoginPage;

public class BaseTest {

	private static final Logger LOG = Logger.getLogger(BaseTest.class);

	protected WebDriver driver;

	@BeforeClass
	public void setup() {
		
		startTestReporting("Test_Setup", "Setting up test execution environment and logging in.");
		successMessage = "Logged in successfully.";
		failureMessage = "Login failed.";
		
		try {
			System.setProperty("webdriver.chrome.driver",
					new File("src/test/resources/drivers").getAbsolutePath() + "\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.get("https://www.saucedemo.com/");
		} catch(Exception e) {
			finishTestReporting(ITestResult.FAILURE,"Could not navigate to main website. See logs for detail.<br>" + 
							reportLog.addScreenCapture(CommonUtil.TakeScreenshot(driver,"Test_Setup")) );
		}
		//Populate Element Map
		if (!(new BasePage(driver).PopulateElementMap())) { 
			finishTestReporting(ITestResult.FAILURE,"Could not populate element map from excel file. See logs for detail.<br>" + 
					reportLog.addScreenCapture(CommonUtil.TakeScreenshot(driver,"Test_Setup")) );
			driver.quit();
		}
		
		testCaseDataMap = ExcelDataExtraction.GetTestCaseDataMap();
		
		//Login as standard_user
		if (!(new LoginPage(driver).LoginToDemoPage("standard_user", "secret_sauce"))) {
			finishTestReporting(ITestResult.FAILURE,"Could not login using given credentials.<br>" + 
					reportLog.addScreenCapture(CommonUtil.TakeScreenshot(driver,"Test_Setup")) );
			driver.quit();
		} else {
			finishTestReporting(ITestResult.SUCCESS,"Successfully logged in to website.<br>" + 
					reportLog.addScreenCapture(CommonUtil.TakeScreenshot(driver,"Test_Setup")) );
		}
		
		
	}
	
	public void startTestReporting(String testCaseName, String testCaseDescription) {
		reportLog = report.startTest(testCaseName, testCaseDescription);
	}
	
	public void initializeTest(Method m, Logger TEST_LOG) {
		startTestReporting(testCaseDataMap.get(m.getName())[0], testCaseDataMap.get(m.getName())[1]);
		successMessage = "";
		failureMessage = "";
		TEST_LOG.debug("Test Case ID: " + testCaseDataMap.get(m.getName())[0]);
		TEST_LOG.debug("Test Case Description: " + testCaseDataMap.get(m.getName())[1]);
		TEST_LOG.debug("Test Case Success Message: " + testCaseDataMap.get(m.getName())[2]);
		TEST_LOG.debug("Test Case Failure Message: " + testCaseDataMap.get(m.getName())[3]);
	}

	public void finishTestReporting(int testResultStatus, String resultMsg) {
		if (testResultStatus == ITestResult.SUCCESS) { reportLog.log(LogStatus.PASS, resultMsg); }
		if (testResultStatus == ITestResult.FAILURE) { reportLog.log(LogStatus.FAIL, resultMsg); }
		report.endTest(reportLog);
		report.flush();
	}
	
	public void reportTestResults(ITestResult result) {
		String s = reportLog.addScreenCapture(CommonUtil.TakeScreenshot(driver,result.getName()));
		finishTestReporting(result.getStatus(), (result.getStatus() == ITestResult.SUCCESS) ?
								(testCaseDataMap.get(result.getMethod().getMethodName())[2] + s) : 
								(testCaseDataMap.get(result.getMethod().getMethodName())[3] + s) );
	}
	
	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}
