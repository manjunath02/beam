package com.amazon.MyProject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage {

	public WebDriver driver;

	@FindBy(xpath = "//span[@class='a-size-medium a-color-price sc-price sc-white-space-nowrap sc-product-price sc-price-sign a-text-bold']")
	public WebElement productPrice;

	@FindBy(xpath = "//span[@class='a-size-medium a-color-price sc-price sc-white-space-nowrap sc-price-sign']")
	public WebElement totalPrice;

	@FindBy(xpath = "//span[@class='a-button-text a-declarative']")
	public WebElement quantity;
	
	@FindBy(xpath = "//span[@class='a-button a-button-primary a-button-small sc-update-link a-span8']")
	public WebElement updateButton;

	@FindBy(xpath = "//span[@class='a-declarative']/input[@value='Delete']")
	public WebElement delete;

	CartPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}


	public List<WebElement> productsInCart() {
		List<WebElement> productsCart = driver
				.findElements(By.xpath("//span[@class='a-size-medium sc-product-title a-text-bold']"));
		return productsCart;
	}

	public WebElement productCartTitle(int i) {
		return (WebElement) driver
				.findElement(By.xpath("(//span[@class='a-size-medium sc-product-title a-text-bold'])[" + i + "]"));
	}
}
