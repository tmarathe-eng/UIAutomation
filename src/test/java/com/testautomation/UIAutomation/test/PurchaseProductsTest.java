package com.testautomation.UIAutomation.test;

import static com.testautomation.UIAutomation.helper.CommonUtil.report;
import static com.testautomation.UIAutomation.helper.CommonUtil.testResultDir;

import java.io.File;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.testautomation.UIAutomation.page.ProductsPage;


public class PurchaseProductsTest extends BaseTest {

	private static final Logger LOG = Logger.getLogger(PurchaseProductsTest.class);
	
	public PurchaseProductsTest() {
		super();
		report = new ExtentReports(
				new File(testResultDir + "/Test_Result_" + System.currentTimeMillis() + ".html")
						.getAbsolutePath());
	}

	@BeforeMethod
	public void StartTest(Method m, Object[] testArgs) {
		initializeTest(m,LOG);
	}
	
	@Test
	public void BuyAllTshirtsTest() {
		Assert.assertTrue(new ProductsPage(driver).PurchaseProducts("T-Shirt","FirstName", "LastName", "00000"));
	}
	
	
	@AfterMethod
	public void StopTest(ITestResult result, Object[] testArgs) {
		reportTestResults(result);
	}
}
