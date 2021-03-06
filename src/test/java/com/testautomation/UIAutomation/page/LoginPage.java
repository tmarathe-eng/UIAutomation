package com.testautomation.UIAutomation.page;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.testautomation.UIAutomation.helper.util.CommonUtil;

public class LoginPage extends BasePage {
	
	private static final Logger LOG = Logger.getLogger(LoginPage.class);

	
	public LoginPage(WebDriver driver){
		super(driver);
		wait = new WebDriverWait(driver, 10);
	}
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @return true/false
	 * 
	 * Enter username and password data, click login button,
	 * verify navigation to Product page
	 * Return false if exception occurs
	 */
	public boolean LoginToDemoPage(String username, String password) {
		//Logging in to demo website using provided username and password
		enterData("txtbox_username", username);
		enterData("txtbox_password", password);
		if (! clickAndVerify("btn_login","hdng_productPg", getElementLocatorVal("txt_productPgHdng"))) {
			CommonUtil.writeMsg(LOG, "ERROR", "Product page not displayed after logging in as " + username + ".", driver, "LoginFailure");
			return false;
		}
		return true;
	}
	
}
