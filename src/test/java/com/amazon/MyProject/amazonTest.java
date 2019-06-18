package com.amazon.MyProject;

import org.testng.annotations.Test;

import junit.framework.Assert;

import java.io.IOException;
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

		log.info("Running verify Searched Product in list test");
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
		log.info("Searching product in list");

		// Assuming task is to verify searched product is shown in list
		Assert.assertTrue("searched product not found in list", productPresent.contains(product));
		log.info("Test Passed");
	}

	@Test(dataProvider = "dataVal")
	public void verifyAddedProductInCart(String product) {

		log.info("Running verify Added Product In Cart test");

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

		Assert.assertTrue("product not found in cart",
				cartobj.cartProductTitle(1).getText().toLowerCase().contains(firstProductName));
		log.info("Test Passed");
	}

	@Test(dataProvider = "dataVal")
	public void verifyAddingTwoProduct(String product) {

		log.info("Test verify Adding 2 Product In Cart");
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

		Assert.assertEquals("added products not found in cart", 2, cartobj.productsInCart().size());
		log.info("Test Passed");
	}

	@Test(dataProvider = "dataVal")
	public void verifyRemovingProduct(String product) throws InterruptedException {
		log.info("Verify Deleting Product Test");
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

		cartobj.deleteButton.click();
		log.info("deleted one product");

		// Adding sleep as there is delay in amazon to perform delete action
		Thread.sleep(500);

		Assert.assertEquals("product not deleted ", noOfProducts - 1, cartobj.productsInCart().size());
		log.info("Test Passed");
	}

	@Test(dataProvider = "dataVal")
	public void verifyUpdateInTotalPrice(String product) throws InterruptedException {

		log.info("Running Verify price change with change in quantity");

		log.info("navigated to home");
		SearchPage home = new SearchPage(driver);
		home.sendSearch(product);
		log.info("searched results shown");

		String oldTab = driver.getWindowHandle();
		int quantity = 10;
		int productNumber = 1;

		home.productTitle(productNumber).click();

		home.addProduct(oldTab);

		log.info("product added to cart");

		home.cart.click();

		CartPage cartobj = new CartPage(driver);

		Double priceOfProduct = Double.parseDouble(cartobj.getProductPrice(productNumber).getText().trim());

		cartobj.selectNoOfItems(quantity, productNumber);
		log.info("updated quantity to " + quantity);

		Double totalCartPrice = Double.parseDouble(cartobj.totalCartPrice.getText().trim().replaceAll(",", ""));

		Assert.assertEquals("price not matching", priceOfProduct * quantity, totalCartPrice);
		log.info("Test Passed");
	}

	@DataProvider
	public static Object[][] dataVal() {
		Object[][] obj = new Object[1][1];
		obj[0][0] = "car";
		return obj;

	}
}
