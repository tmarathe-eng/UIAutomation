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
	 * 
	 * @param productName
	 * @param firstName
	 * @param lastName
	 * @param zipCode
	 * @return true/false
	 * 
	 * Performs following tasks, if anyone fails, returns false
	 * 1. Add products to cart that contains param1
	 * 2. Go to cart
	 * 3. Click checkout button
	 * 4. Fill in checkout information (param2, param3, param4)
	 * 5. Complete transaction by clicking Finish button
	 */
	public boolean PurchaseProducts(String productName, String firstName, String lastName, String zipCode) {
		if (!AddProductsToCart(productName)) { return false; }
		if (!GoToCart()) { return false;}
		if (!(new CartPage(driver).CheckOutItems())) { return false; }
		if (!(new CheckoutInfoPage(driver).FillCustomerInfo(firstName, lastName, zipCode))) { return false; }
		if (!(new OrderReviewPage(driver).CompleteTransaction())) { return false; }
		return true;
	}

	/**
	 * 
	 * @param productName
	 * @return true/false
	 * 
	 * Iterates over each products listed on Products page and 
	 * adds products to cart that contain given param1
	 */
	public boolean AddProductsToCart(String productName) {
		//Get product element list
		List<WebElement> productList = GetElementList("list_products");
		int itemsAddedToCart = 0;
				
		for (int i=0;i<productList.size();i++) {
			//Get product name on (i+1)th row
			String itemName = FindElementAtIndex("txt_productName", i+1).getText();
			if (itemName.contains(productName)) {
				itemsAddedToCart++;
				if (! ClickElementAtIndexAndVerify("btn_addToCart", i+1, "txt_cartItemCount", "" + itemsAddedToCart + "")) {
					CommonUtil.writeMsg(LOG, "ERROR", "Item '" + itemName + "' could not be added to cart.", driver,"AddToCartFailure");
					return false;
				}
			}
		}
		CommonUtil.writeMsg(LOG, "INFO", "Added all " + productName + " products to cart.", driver,"AddToCartSuccess");
		return true;
	}
}
