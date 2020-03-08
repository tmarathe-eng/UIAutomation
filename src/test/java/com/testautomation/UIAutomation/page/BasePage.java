package com.testautomation.UIAutomation.page;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.testautomation.UIAutomation.helper.ExcelDataExtraction;

public class BasePage {
	private static final Logger LOG = Logger.getLogger(BasePage.class);
	
	protected WebDriver driver;
	protected WebDriverWait wait;
	private static HashMap<String, String[]> elementMap = null;
	
	public BasePage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, 10);
	}

	public boolean PopulateElementMap() {
		BasePage.elementMap = ExcelDataExtraction.GetElementMap();
		if (BasePage.elementMap == null) { return false; }
		return true;
	}
	
	protected By ConstructElementLocator(String locatorType, String locatorText) { 
		LOG.debug("Creating locator for: \n" + locatorType + ":" + locatorText);
		switch(locatorType) {
		case "id": 
			return By.id(locatorText);
		case "name":
			return By.name(locatorText);
		case "css" :
			return By.cssSelector(locatorText);
		case "xpath" :
			return By.xpath(locatorText);
		case "linkText" :
			return By.linkText(locatorText);
		default:
				return null;
		}
	}
	
	protected By GetElementLocator(String elementName) {
		return ConstructElementLocator(BasePage.elementMap.get(elementName)[0], BasePage.elementMap.get(elementName)[1]);
	}
	
	protected  String GetElementLocatorVal(String elementName) {
		return BasePage.elementMap.get(elementName)[1];
	}

	protected List<WebElement> GetElementList(String elementName) { 
		return driver.findElements(GetElementLocator(elementName));
	}
	
	protected WebElement FindElement(String elementName) {
		return driver.findElement(GetElementLocator(elementName));
	}
	
	protected WebElement FindElementAtIndex(String elementName, int index) {
		LOG.debug("Getting " + elementName + " at index " + index + ".");
		LOG.debug("Locator type: " + BasePage.elementMap.get(elementName)[0]);
		LOG.debug("Locator text: " + GetElementAtIndex(elementName,index));
		return driver.findElement(ConstructElementLocator(BasePage.elementMap.get(elementName)[0],GetElementAtIndex(elementName,index)));
	}
	
	protected String GetElementAtIndex(String elementName, int index) {
		return GetElementLocatorVal(elementName).replaceAll("~N~", "" + index + "");
	}
	
	protected boolean EnterData(String elementName, String val) {
		try {
			driver.findElement(GetElementLocator(elementName)).sendKeys(val);
		} catch(Exception e) {
			LOG.error("Error encountered while entering data in textbox field.\n" + e.getMessage());
			return false;
		}
		return true;
	}

	protected boolean ClickAndVerify(String elemNameToClick, String elemNameToChk, String valToChk) {
		try {
			driver.findElement(GetElementLocator(elemNameToClick)).click();
			wait.until(ExpectedConditions.textToBe(GetElementLocator(elemNameToChk), valToChk));
		} catch(Exception e) {
			LOG.error("Error encountered while clicking an element.\n" + e.getMessage());
			return false;
		}
		return true;
	}

	protected boolean ClickElementAtIndexAndVerify(String elemNameToClick, int index, String elemNameToChk, String valToChk) {
		try {
			FindElementAtIndex(elemNameToClick,index).click();
			wait.until(ExpectedConditions.textToBe(GetElementLocator(elemNameToChk), valToChk));
		} catch(Exception e) {
			LOG.error("Error encountered while clicking an element.\n" + e.getMessage());
			return false;
		}
		return true;
	}
	
	protected boolean GoToCart() {
		if (! ClickAndVerify("lnk_goToCart", "hdng_cartPg", GetElementLocatorVal("txt_cartPgHdng"))) { 
			LOG.error("Could not navigate to Cart page.");
			return false; 
		}
		return true;
	}

}
