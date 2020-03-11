package com.testautomation.UIAutomation.test;

import static com.testautomation.UIAutomation.helper.util.ExtentReport.*;

import java.io.File;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.testautomation.UIAutomation.helper.constants.FilePath;
import com.testautomation.UIAutomation.helper.util.ExtentReport;
import com.testautomation.UIAutomation.page.ProductsPage;

/**
 * @author tm0338
 * Contains test methods to run test for following scenario
 * 1. Go to login page for www.saucedemo.com
 * 2. Login as standard_user
 * 3. Add all T-Shirt products to cart
 * 4. Fill checkout information
 * 5. Complete transaction
 */
public class PurchaseProductsTest extends BaseTest {

	private static final Logger LOG = Logger.getLogger(PurchaseProductsTest.class);
	
	public PurchaseProductsTest() {
		super();
		//Test results can be found in test-output/extent-reports/Test_Result_<yyyymmddhhmmssms>.html file
		ExtentReport.Report = new ExtentReports(
				new File(FilePath.TEST_RESULT_DIR + "/Test_Result_" + System.currentTimeMillis() + ".html")
						.getAbsolutePath());
	}

	//This method runs before each test method, it sets up html reporting parameters and test case metadata for each test
	@BeforeMethod
	public void StartTest(Method m, Object[] testArgs) {
		initializeTest(m,LOG);
	}
	
	/**
	 * Test method calls PurchaseProducts method from ProductsPage class under com.testautomation.UIAutomation.page package
	*/
	@Test
	public void BuyAllTshirtsTest() {
		Assert.assertTrue(new ProductsPage(driver).PurchaseProducts("T-Shirt","FirstName", "LastName", "00000"));
	}
	
	//This method is called after each test is run, it takes screenshot and reports result to html report file
	@AfterMethod
	public void StopTest(ITestResult result, Object[] testArgs) {
		reportTestResults(driver,result);
	}
}
