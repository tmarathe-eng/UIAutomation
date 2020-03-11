package com.testautomation.UIAutomation.page;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.testautomation.UIAutomation.helper.util.CommonUtil;

public class ProductsPage extends BasePage {

	private static final Logger LOG = Logger.getLogger(ProductsPage.class);

	public ProductsPage(WebDriver driver) {
		super(driver);
		wait = new WebDriverWait(driver, 10);
	}

	/**
	 * Returns true if adding products to cart is successful
	 * Iterates over each products listed on Products page and 
	 * adds products to cart that contain given productName
	 * @param productName
	 * @return true/false
	 */
	public boolean addProductsToCart(String productName) {
		//Get product element list
		List<WebElement> productList = getElementList("list_products");
		int itemsAddedToCart = 0;
				
		for (int i=0;i<productList.size();i++) {
			//Get product name on (i+1)th row
			String itemName = findElementAtIndex("txt_productName", i+1).getText();
			if (itemName.contains(productName)) {
				itemsAddedToCart++;
				if (! clickElementAtIndexAndVerify("btn_addToCart", i+1, "txt_cartItemCount", "" + itemsAddedToCart + "")) {
					CommonUtil.writeMsg(LOG, "ERROR", "Item '" + itemName + "' could not be added to cart.", driver,"AddToCartFailure");
					return false;
				}
			}
		}
		CommonUtil.writeMsg(LOG, "INFO", "Added all " + productName + " products to cart.", driver,"AddToCartSuccess");
		return true;
	}
}
