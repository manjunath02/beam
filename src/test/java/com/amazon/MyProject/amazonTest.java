package com.amazon.MyProject;

import org.testng.annotations.Test;

import junit.framework.Assert;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import resource.Base;

public class amazonTest extends Base {

	private static Logger log = LogManager.getLogger(amazonTest.class.getName());

	@BeforeMethod
	public void beforeTest() throws IOException {
		initializeDriver();
		Utils preReq = new Utils(driver);		
		driver.get(prop.getProperty("url"));
		preReq.prerequisiteToClearCart();
		log.info("pre requisite run");
	}

	@AfterMethod
	public void afterTest() {
		 driver.close();
	}

	@Test(dataProvider = "dataVal")
	public void verifySearchedProduct(String product) {

		String productPresent = "";
		log.info("navigated to home");
		SearchPage home = new SearchPage(driver);
		home.sendSearch(product);
		log.info("Search Results Displayed");

		for (int i = 1; i < home.searchedProducts().size(); i++) {
			if (home.productTitle(i).getText().toLowerCase().contains(product)) {
				productPresent = home.productTitle(i).getText().toLowerCase();
				break;
			}

		}

		// Assuming task is to verify searched product is shown in list
		Assert.assertTrue("searched product not found in list",productPresent.contains(product));
		
	}

	@Test(dataProvider = "dataVal")
	public void verifyAddedProductInCart(String product) {

		log.info("navigated to home");
		SearchPage home = new SearchPage(driver);
		home.sendSearch(product);
		log.info("searched results shown");

		String oldTab = driver.getWindowHandle();
		String firstProductName = home.productTitle(1).getText().toLowerCase();

		home.productTitle(1).click();

		home.addProduct(oldTab);
		
		log.info("product added to cart");
		
		home.cart.click();

		CartPage cartobj = new CartPage(driver);

		Assert.assertTrue("product not found in cart",cartobj.productCartTitle(1).getText().toLowerCase().contains(firstProductName));
	}

//	

	@Test(dataProvider = "dataVal")
	public void verifyAddingTwoProduct(String product) {

		
		log.info("navigated to home");
		SearchPage home = new SearchPage(driver);
		CartPage cartobj = new CartPage(driver);
		home.sendSearch(product);
		log.info("searched results shown");

		String oldTab = driver.getWindowHandle();

		home.productTitle(1).click();

		home.addProduct(oldTab);
		log.info("added one product");
		home.productTitle(2).click();

		home.addProduct(oldTab);
		log.info("added second product");
		home.cart.click();

		Assert.assertEquals("added products not found in cart",2, cartobj.productsInCart().size());

	}

	@Test(dataProvider = "dataVal")
	public void verifyRemovingProduct(String product) throws InterruptedException {

		int noOfProducts;
		log.info("navigated to home");
		SearchPage home = new SearchPage(driver);
		CartPage cartobj = new CartPage(driver);
		home.sendSearch(product);
		log.info("searched results shown");

		String oldTab = driver.getWindowHandle();

		home.productTitle(1).click();

		home.addProduct(oldTab);
		log.info("added first product");
		home.productTitle(2).click();

		home.addProduct(oldTab);
		log.info("added second product");
		home.cart.click();

		noOfProducts = cartobj.productsInCart().size();

		cartobj.delete.click();
		log.info("deleted one product");
		
		
		// Adding sleep as there is delay in amazon to perform delete action
		Thread.sleep(5000);

		Assert.assertEquals("product not deleted ",noOfProducts - 1, cartobj.productsInCart().size());

	}

	@DataProvider
	public static Object[][] dataVal() {
		Object[][] obj = new Object[1][1];
		obj[0][0] = "car";
		return obj;

	}
}
