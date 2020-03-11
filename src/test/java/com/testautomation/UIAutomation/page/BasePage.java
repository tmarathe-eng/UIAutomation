package com.testautomation.UIAutomation.page;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.testautomation.UIAutomation.helper.util.ExcelDataExtraction;

/**
 * @author tm0338
 * Contains common methods and constructor used by all page classes in the project.
 */
public class BasePage {
	private static final Logger LOG = Logger.getLogger(BasePage.class);
	
	protected WebDriver driver;
	protected WebDriverWait wait;
	private static HashMap<String, String[]> elementMap = null; //Element map data variable
	
	public BasePage(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, 10); //Explicit wait is used
	}

	//Calls method from ExcelDataExtraction helper class to get element map data from excel file
	public boolean PopulateElementMap() {
		BasePage.elementMap = ExcelDataExtraction.GetElementMap();
		if (BasePage.elementMap == null) { return false; }
		return true;
	}
	
	/**
	 * 
	 * @param locatorType Any of the locator types used in element map - id, name, css, xpath, linkText
	 * @param locatorText Text value of the locator - e.g. div.header, #username, password etc.
	 * @return Returns locator of type org.openqa.selenium.By
	 * 
	 * Creates locator from given locator type and locator text values
	 */
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
	
	/**
	 * 
	 * @param elementName
	 * @return Returns locator of type org.openqa.selenium.By
	 * 
	 * Looks for locator type and locator text for given elementName in element map and creates locator
	 */
	protected By GetElementLocator(String elementName) {
		return ConstructElementLocator(BasePage.elementMap.get(elementName)[0], BasePage.elementMap.get(elementName)[1]);
	}
	
	/**
	 * 
	 * @param elementName
	 * @return Element locator text
	 * 
	 * Looks up locator text for given elementName in element map and returns it
	 */
	protected  String GetElementLocatorVal(String elementName) {
		return BasePage.elementMap.get(elementName)[1];
	}

	/**
	 * 
	 * @param elementName
	 * @return Element list
	 * 
	 * Calls GetElementLocator to look up locator type and locator text for given elementName, 
	 * creates WebElement list for resulting locator 
	 */
	protected List<WebElement> GetElementList(String elementName) { 
		return driver.findElements(GetElementLocator(elementName));
	}
	
	/**
	 * 
	 * @param elementName
	 * @return WebElement
	 * 
	 * Calls GetElementLocator to look up locator type and locator text for given elementName, 
	 * creates single WebElement for resulting locator 
	 */
	protected WebElement FindElement(String elementName) {
		return driver.findElement(GetElementLocator(elementName));
	}
	
	/**
	 * 
	 * @param elementName
	 * @param index
	 * @return WebElement at provided index
	 * 
	 * Gets element locator text by calling GetElementAtIndex
	 * Creates locator by calling ConstructElementLocator with locator type and locator text found above
	 * Returns WebElement for given index value
	 */
	protected WebElement FindElementAtIndex(String elementName, int index) {
		LOG.debug("Getting " + elementName + " at index " + index + ".");
		LOG.debug("Locator type: " + BasePage.elementMap.get(elementName)[0]);
		LOG.debug("Locator text: " + GetElementAtIndex(elementName,index));
		return driver.findElement(ConstructElementLocator(BasePage.elementMap.get(elementName)[0],GetElementAtIndex(elementName,index)));
	}
	
	/**
	 * 
	 * @param elementName
	 * @param index
	 * @return Element locator text
	 * 
	 * Replaces placeholder '~N~' in locator text with current index,
	 * Returns locator text for element at current index
	 */
	protected String GetElementAtIndex(String elementName, int index) {
		return GetElementLocatorVal(elementName).replaceAll("~N~", "" + index + "");
	}
	
	/**
	 * 
	 * @param elementName
	 * @param val
	 * @return true/false
	 * 
	 * Enters data in textbox identified by elementName, returns false if any exception occurs
	 */
	protected boolean EnterData(String elementName, String val) {
		try {
			driver.findElement(GetElementLocator(elementName)).sendKeys(val);
		} catch(Exception e) {
			LOG.error("Error encountered while entering data in textbox field.\n" + e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param elemNameToClick
	 * @param elemNameToChk
	 * @param valToChk
	 * @return true/false
	 * 
	 * Clicks on element identified by param1, waits until value of element identified by param2 is equal to param3
	 * Returns false if exception occurs
	 */
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

	/**
	 * 
	 * @param elemNameToClick
	 * @param index
	 * @param elemNameToChk
	 * @param valToChk
	 * @return true/false
	 * 
	 * Clicks on element identified by param1 at index param2, waits until value of element identified by param3 is equal to param4
	 * Returns false if exception occurs
	 */
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
	
	/**
	 * 
	 * @return true/false
	 * 
	 * Clicks on goToCart link on www.saucedemo.com page and navigates to Cart page
	 */
	protected boolean GoToCart() {
		if (! ClickAndVerify("lnk_goToCart", "hdng_cartPg", GetElementLocatorVal("txt_cartPgHdng"))) { 
			LOG.error("Could not navigate to Cart page.");
			return false; 
		}
		return true;
	}

}
