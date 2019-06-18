package com.amazon.MyProject;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchPage {

	public WebDriver driver;

	@FindBy(id = "twotabsearchtextbox")
	public WebElement searchBar;

	@FindBy(id = "add-to-cart-button")
	public WebElement addToCartButton;

	@FindBy(xpath = "//div[@class='nav-search-submit nav-sprite']")
	public WebElement submitButton;

	@FindBy(xpath = "//a[@href='https://www.amazon.in/gp/cart/view.html?ref_=nav_cart']")
	public WebElement cart;

	SearchPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	public void sendSearch(String product) {
		searchBar.sendKeys(product);
		submitButton.click();
	}

	public List<WebElement> searchedProducts() {
		List<WebElement> searchProduct = driver
				.findElements(By.xpath("//a[@class='a-link-normal a-text-normal']/span"));
		return searchProduct;
	}

	public WebElement productTitle(int i) {
		return (WebElement) driver.findElement(By.xpath("(//a[@class='a-link-normal a-text-normal']/span)[" + i + "]"));
	}

	public void addProduct(String old) {

		// get window handles
		ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
		newTab.remove(old);

		// change focus to new tab
		driver.switchTo().window(newTab.get(0));

		addToCartButton.click();

		driver.close();

		// change focus back to old tab
		driver.switchTo().window(old);

	}

}
