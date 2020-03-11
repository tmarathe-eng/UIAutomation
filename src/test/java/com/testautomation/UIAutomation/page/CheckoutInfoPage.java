package com.testautomation.UIAutomation.page;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.testautomation.UIAutomation.helper.util.CommonUtil;

public class CheckoutInfoPage extends BasePage {

	private static final Logger LOG = Logger.getLogger(CheckoutInfoPage.class);
	
	public CheckoutInfoPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Enter data for first name, last name and zip code, return false if exception occurs
	 * @param firstName
	 * @param lastName
	 * @param zipCode
	 * @return true/false
	 * 
	 * 
	 */
	public boolean fillCustomerInfo(String firstName, String lastName, String zipCode) {
		try {
		enterData("txtbox_firstname", firstName);
		enterData("txtbox_lastname", lastName);
		enterData("txtbox_zipCode", zipCode);
		CommonUtil.writeMsg(LOG, "INFO", "Entered customer information on Checkout Info page.", driver,"EnteredCustomerInfo");
		} catch(Exception e) {
			CommonUtil.writeMsg(LOG, "ERROR", "Error occurred while entering customer information on Checkout Info page.\n" + e.getMessage(), driver,"EnteredCustomerInfo");
			return false;
		}
		return true;
	}
	
	/**
	 * Click continue checkout button, navigate to checkout order review page, return false if exception occurs
	 * @return
	 */
	public boolean clickContinueToCheckoutButton() {
		if(!clickAndVerify("btn_continueCheckout", "hdng_orderReviewPg", getElementLocatorVal("txt_orderReviewPgHdng"))) {
			LOG.error("Could not navigate to Order Review Page after entering customer information.");
			CommonUtil.writeMsg(LOG, "ERROR","Could not navigate to Order Review Page after entering customer information.", driver,"OrderOverviewFailure");
			return false;
		}
		CommonUtil.writeMsg(LOG, "INFO", "Navigated to Order Overview page.", driver,"OrderOverviewSuccess");
		return true;
	}

}
