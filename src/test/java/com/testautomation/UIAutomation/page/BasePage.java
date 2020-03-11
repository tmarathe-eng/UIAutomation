package com.testautomation.UIAutomation.page;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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
	 * Creates locator from given locator type and locator text values
	 * @param locatorType Any of the locator types used in element map - id, name, css, xpath, linkText
	 * @param locatorText Text value of the locator - e.g. div.header, #username, password etc.
	 * @return Returns locator of type org.openqa.selenium.By
	 * 
	 */
	protected By constructElementLocator(String locatorType, String locatorText) { 
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
	 * Looks for locator type and locator text for given elementName in element map and creates locator
	 * @param elementName
	 * @return Returns locator of type org.openqa.selenium.By
	 * 
	 */
	protected By getElementLocator(String elementName) {
		return constructElementLocator(BasePage.elementMap.get(elementName)[0], BasePage.elementMap.get(elementName)[1]);
	}
	
	/**
	 * Looks up locator text for given elementName in element map and returns it
	 * @param elementName
	 * @return Element locator text
	 */
	protected  String getElementLocatorVal(String elementName) {
		return BasePage.elementMap.get(elementName)[1];
	}

	/**
	 * Calls GetElementLocator to look up locator type and locator text for given elementName, 
	 * creates WebElement list for resulting locator 
	 * @param elementName
	 * @return Element list
	 * 
	 */
	protected List<WebElement> getElementList(String elementName) { 
		return driver.findElements(getElementLocator(elementName));
	}
	
	/**
	 * Calls GetElementLocator to look up locator type and locator text for given elementName, 
	 * creates single WebElement for resulting locator 
	 * @param elementName
	 * @return WebElement
	 * 
	 */
	protected WebElement findElement(String elementName) {
		return driver.findElement(getElementLocator(elementName));
	}
	
	/**
	 * Gets element locator text by calling GetElementAtIndex
	 * Creates locator by calling ConstructElementLocator with locator type and locator text found above
	 * Returns WebElement for given index value
	 * @param elementName
	 * @param index
	 * @return WebElement at provided index
	 */
	protected WebElement findElementAtIndex(String elementName, int index) {
		LOG.debug("Getting " + elementName + " at index " + index + ".");
		LOG.debug("Locator type: " + BasePage.elementMap.get(elementName)[0]);
		LOG.debug("Locator text: " + getElementAtIndex(elementName,index));
		return driver.findElement(constructElementLocator(BasePage.elementMap.get(elementName)[0],getElementAtIndex(elementName,index)));
	}
	
	/**
	 * Replaces placeholder '~N~' in locator text with current index,
	 * Returns locator text for element at current index
	 * @param elementName
	 * @param index
	 * @return Element locator text
	 */
	protected String getElementAtIndex(String elementName, int index) {
		return getElementLocatorVal(elementName).replaceAll("~N~", "" + index + "");
	}
	
	/**
	 * Enters data in textbox identified by elementName, returns false if any exception occurs
	 * @param elementName
	 * @param val
	 * @return true/false
	 */
	protected boolean enterData(String elementName, String val) {
		try {
			driver.findElement(getElementLocator(elementName)).sendKeys(val);
		} catch(Exception e) {
			LOG.error("Error encountered while entering data in textbox field.\n" + e.getMessage());
			return false;
		}
		return true;
	}

	
	protected boolean click(String elementName) {
		try {
			findElement(elementName).click();
		} catch(Exception e) {
			LOG.debug("Could not click object '" + elementName + "'.");
			return false;
		}
		return true;
	}
	
	/**
	 * Clicks on element identified by param1, waits until value of element identified by param2 is equal to param3
	 * Returns false if exception occurs
	 * @param elemNameToClick
	 * @param elemNameToChk
	 * @param valToChk
	 * @return true/false
	 * 
	 */
	protected boolean clickAndVerify(String elemNameToClick, String elemNameToChk, String valToChk) {
		try {
			driver.findElement(getElementLocator(elemNameToClick)).click();
			wait.until(ExpectedConditions.textToBe(getElementLocator(elemNameToChk), valToChk));
		} catch(Exception e) {
			LOG.error("Error encountered while clicking an element.\n" + e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Clicks on element identified by param1 at index param2, waits until value of element identified by param3 is equal to param4
	 * Returns false if exception occurs
	 * @param elemNameToClick
	 * @param index
	 * @param elemNameToChk
	 * @param valToChk
	 * @return true/false
	 * 
	 */
	protected boolean clickElementAtIndexAndVerify(String elemNameToClick, int index, String elemNameToChk, String valToChk) {
		try {
			findElementAtIndex(elemNameToClick,index).click();
			wait.until(ExpectedConditions.textToBe(getElementLocator(elemNameToChk), valToChk));
		} catch(Exception e) {
			LOG.error("Error encountered while clicking an element.\n" + e.getMessage());
			return false;
		}
		return true;
	}
	
	protected boolean checkIfAlreadyOnPage(String elemNameToChk, String valToChk) {
		//Check if user is already on the requested page
		try {
			if (findElement(elemNameToChk).getText().equals(valToChk)) { return true;}
			else { return false;}
		} catch(NoSuchElementException nse) {
			LOG.debug("Not on requested page.");
			return false;
		}
	}
	
}
