package com.testautomation.UIAutomation.page;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.testautomation.UIAutomation.helper.CommonUtil;

public class CheckoutInfoPage extends BasePage {

	private static final Logger LOG = Logger.getLogger(CheckoutInfoPage.class);
	
	public CheckoutInfoPage(WebDriver driver) {
		super(driver);
	}
	
	public boolean FillCustomerInfo(String firstName, String lastName, String zipCode) {
		EnterData("txtbox_firstname", firstName);
		EnterData("txtbox_lastname", lastName);
		EnterData("txtbox_zipCode", zipCode);
		CommonUtil.writeMsg(LOG, "INFO", "Entered customer information on Checkout Info page.", driver,"EnteredCustomerInfo");
		if(!ClickAndVerify("btn_continueCheckout", "hdng_orderReviewPg", GetElementLocatorVal("txt_orderReviewPgHdng"))) {
			LOG.error("Could not navigate to Order Review Page after entering customer information.");
			CommonUtil.writeMsg(LOG, "ERROR","Could not navigate to Order Review Page after entering customer information.", driver,"OrderOverviewFailure");
			return false;
		}
		CommonUtil.writeMsg(LOG, "INFO", "Navigated to Order Overview page.", driver,"OrderOverviewSuccess");
		return true;
	}

}
