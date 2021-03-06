package com.testautomation.UIAutomation.page;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.testautomation.UIAutomation.helper.util.CommonUtil;

public class CartPage extends BasePage {

	private static final Logger LOG = Logger.getLogger(CartPage.class);
	
	public CartPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Click on checkout button, navigate to checkout information page
	 * Return false if exception occurs
	 * @return true/false
	 */
	public boolean checkOutItems() {
		if (! clickAndVerify("btn_checkout", "hdng_checkoutPg", getElementLocatorVal("txt_checkoutPgHdng"))) { 
			LOG.error("Could not navigate to Check Out page.");
			CommonUtil.writeMsg(LOG, "ERROR", "Could not navigate to Check Out page.", driver,"CheckoutFailure");
			return false; 
		}
		CommonUtil.writeMsg(LOG, "INFO", "Clicked on 'Checkout' button.", driver,"CheckoutSuccess");
		return true;
	}
}
