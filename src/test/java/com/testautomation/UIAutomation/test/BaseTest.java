package com.testautomation.UIAutomation.test;

import static com.testautomation.UIAutomation.helper.constants.FilePath.CHROME_DRIVER_FILE;
import static com.testautomation.UIAutomation.helper.util.CommonUtil.GetScreenshotForReport;
import static com.testautomation.UIAutomation.helper.util.CommonUtil.TestCaseDataMap;
import static com.testautomation.UIAutomation.helper.util.ExtentReport.finishTestReporting;
import static com.testautomation.UIAutomation.helper.util.ExtentReport.startTestReporting;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.testautomation.UIAutomation.helper.util.ExcelDataExtraction;
import com.testautomation.UIAutomation.helper.util.ExtentReport;
import com.testautomation.UIAutomation.page.BasePage;
import com.testautomation.UIAutomation.page.LoginPage;

/**
 * @author tm0338
 * Contains methods to start and stop webdriver, reporting and logging in to portal
 */
public class BaseTest {

	private static final Logger LOG = Logger.getLogger(BaseTest.class);

	protected WebDriver driver;

	@BeforeClass
	public void setup() {
		LOG.debug("Setting up reporting in BaseTest class.");
		startTestReporting("Test_Setup", "Setting up test execution environment and logging in.");
		ExtentReport.SuccessMessage = "Logged in successfully.";
		ExtentReport.FailureMessage = "Login failed.";
		
		//Load and initialize Selenium WebDriver object
		LOG.debug("Initializing WebDriver");
		initializeDriver();
		
		//Populate Element Map
		LOG.debug("Getting page element maps from excel file.");
		if (!(new BasePage(driver).PopulateElementMap())) { 
			finishTestReporting(ITestResult.FAILURE,"Could not populate element map from excel file. See logs for detail.<br>" + 
					GetScreenshotForReport(driver, "Test_Setup") );
			driver.quit();
		}
		
		//Populate Test Case Data
		LOG.debug("Getting test case metadata from excel file.");
		TestCaseDataMap = ExcelDataExtraction.GetTestCaseDataMap();
		
		//Login to portal
		LOG.debug("Logging in as standard_user");
		loginToPortal();
		
		
		
	}
	
	private void initializeDriver() {
		try {
			System.setProperty("webdriver.chrome.driver",CHROME_DRIVER_FILE);
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.get("https://www.saucedemo.com/");
		} catch(Exception e) {
			finishTestReporting(ITestResult.FAILURE,"Could not navigate to main website. See logs for detail.<br>" + 
					GetScreenshotForReport(driver, "Test_Setup") );
		}
	}
	
	public boolean loginToPortal() {
		//Login as standard_user
		if (!(new LoginPage(driver).LoginToDemoPage("standard_user", "secret_sauce"))) {
			finishTestReporting(ITestResult.FAILURE,"Could not login using given credentials.<br>" + 
					GetScreenshotForReport(driver, "Test_Setup") );
			driver.quit();
			return false;
		} else {
			finishTestReporting(ITestResult.SUCCESS,"Successfully logged in to website.<br>" + 
					GetScreenshotForReport(driver, "Test_Setup") );
		}
		return true;
	}
	
	@AfterClass
	public void tearDown() {
		LOG.debug("Closing driver object.");
		driver.quit();
	}
}
