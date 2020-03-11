package com.testautomation.UIAutomation.test;

import static com.testautomation.UIAutomation.helper.util.ExtentReport.Report;
import static com.testautomation.UIAutomation.helper.util.ExtentReport.initializeTest;
import static com.testautomation.UIAutomation.helper.util.ExtentReport.reportTestResults;

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
import com.testautomation.UIAutomation.page.CartPage;
import com.testautomation.UIAutomation.page.CheckoutInfoPage;
import com.testautomation.UIAutomation.page.Navigation;
import com.testautomation.UIAutomation.page.OrderReviewPage;
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
	Navigation navigate;
	ProductsPage productsPg;
	CartPage cartPg;
	CheckoutInfoPage chkoutInfoPg;
	OrderReviewPage orderReviewPg;
	
	public PurchaseProductsTest() {
		super();
		//Test results can be found in test-output/extent-reports/Test_Result_<yyyymmddhhmmssms>.html file
		Report = new ExtentReports(
				new File(FilePath.TEST_RESULT_DIR + "/Test_Result_" + System.currentTimeMillis() + ".html")
						.getAbsolutePath());
	}

	//This method runs before each test method, it sets up html reporting parameters and test case metadata for each test
	@BeforeMethod
	public void startTest(Method m, Object[] testArgs) {
		initializeTest(m.getName(),LOG);
		navigate = new Navigation(driver);
		productsPg = new ProductsPage(driver);
		cartPg = new CartPage(driver);
		chkoutInfoPg = new CheckoutInfoPage(driver);
		orderReviewPg = new OrderReviewPage(driver);
	}
	
	/**
	 * Test method calls PurchaseProducts method from ProductsPage class under com.testautomation.UIAutomation.page package
	*/
	@Test
	public void buyAllTshirtsTest() {
		//Verify user is on Products page, if not navigate to that page from top navigation
		Assert.assertTrue(navigate.toProductsPg());
		
		//Verify all products containing 'T-Shirt' in product name are added to cart
		Assert.assertTrue(productsPg.addProductsToCart("T-Shirt"));
		
		//Verify navigation to Cart Page
		Assert.assertTrue(navigate.toCartPg());
		
		//Verify navigation to Checkout Information page
		Assert.assertTrue(cartPg.checkOutItems());
		
		//Enter customer information 
		Assert.assertTrue(chkoutInfoPg.fillCustomerInfo("Firstname", "Lastname", "00000"));
		
		//Verify navigation to Order Review page
		Assert.assertTrue(chkoutInfoPg.clickContinueToCheckoutButton());
		
		//Verify transaction completes after clicking Finish button
		Assert.assertTrue(orderReviewPg.completeTransaction());
	}
	
	//This method is called after each test is run, it takes screenshot and reports result to html report file
	@AfterMethod
	public void stopTest(ITestResult result, Object[] testArgs) {
		reportTestResults(driver,result);
	}
}
