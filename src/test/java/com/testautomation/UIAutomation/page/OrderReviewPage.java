package com.testautomation.UIAutomation.page;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.testautomation.UIAutomation.helper.util.CommonUtil;

public class OrderReviewPage extends BasePage {

	private static final Logger LOG = Logger.getLogger(OrderReviewPage.class);
	
	public OrderReviewPage(WebDriver driver) {
		super(driver);
	}

	/**
	 * 
	 * @return true/false
	 * 
	 * Click Finish button on Order Review page, verify transaction completes
	 * Return false if exception occurs
	 */
	public boolean completeTransaction() {
		if (! clickAndVerify("btn_finish", "hdng_orderCompletePg", getElementLocatorVal("txt_orderCompletePgHdng"))) {
			LOG.error("Could not complete transaction on Order Review page.");
			CommonUtil.writeMsg(LOG, "ERROR", "Could not complete transaction on Order Review page.", driver,"TransactionCompleteFailure");
			return false;
		}
		CommonUtil.writeMsg(LOG, "INFO", "Transaction completed successfully on Order Review page.", driver,"TransactionCompleteSuccess");
		return true;
	}
}
