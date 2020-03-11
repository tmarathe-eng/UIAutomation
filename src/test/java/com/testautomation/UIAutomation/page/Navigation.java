package com.testautomation.UIAutomation.page;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.testautomation.UIAutomation.helper.util.CommonUtil;


/**
 * Provides navigation to top level elements such as menu items and cart
 * @author tm0338
 *
 */
public class Navigation extends BasePage {

	private static final Logger LOG = Logger.getLogger(Navigation.class);
	
	public Navigation(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Clicks on goToCart link on www.saucedemo.com page and navigates to Cart page
	 * @return true/false
	 *  
	 */
	public boolean toCartPg() {
		//Check if already on page, if not navigate to Cart page
		if (!checkIfAlreadyOnPage("hdng_cartPg", getElementLocatorVal("txt_cartPgHdng"))) {
			CommonUtil.writeMsg(LOG, "INFO",  "Navigating to Cart page.");
			if (!clickAndVerify("lnk_goToCart", "hdng_cartPg", getElementLocatorVal("txt_cartPgHdng"))) { 
				CommonUtil.writeMsg(LOG, "ERROR", "Error encountered while navigating to Cart page.", driver, "NavigateToCartPg");
				return false;
			}
		}
		return true;
	}
	

	/**
	 * Clicks on All Items link in Menu and navigates to Products page
	 * @return true/false
	 *  
	 */
	public boolean toProductsPg() {
		//Check if already on page, if not navigate to Products page
		if (!checkIfAlreadyOnPage("hdng_productPg", getElementLocatorVal("txt_productPgHdng"))) {
			CommonUtil.writeMsg(LOG, "INFO",  "Navigating to Products page.");
			click("btn_mainMenu");
			if (!clickAndVerify("lnk_productsMenuLink", "hdng_productPg", getElementLocatorVal("txt_productPgHdng"))) { 
				CommonUtil.writeMsg(LOG, "ERROR", "Error encountered while navigating to Products page.", driver, "NavigateToProductsPg");
				return false;
			}
		}
		return true;
	}
}
