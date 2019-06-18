package com.amazon.MyProject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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
	public WebElement deleteButton;
	
	@FindBy(xpath = "//span[@class='a-size-medium a-color-price sc-price sc-white-space-nowrap sc-price-sign']")
	public WebElement totalCartPrice;
	

	CartPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}
	
	public void selectNoOfItems(int quantity,int item) throws InterruptedException {
		
		driver.findElement(By.xpath("(//span[@class='a-button-text a-declarative'])[" + item + "]")).click();
		driver.findElement(By.xpath("//li[@class='a-dropdown-item quantity-option quantity-option-10']/a")).click();
		
		if(quantity==10)
		{
			driver.findElement(By.xpath("(//input[@name='quantityBox'])[" + item + "]")).sendKeys(Integer.toString(quantity));
			driver.findElement(By.xpath("(//input[@name='quantityBox'])[" + item + "]")).sendKeys(Keys.RETURN);
			//adding sleep as amazon takes time to update price
			Thread.sleep(1000);
		}
		
		
	}
	
	public WebElement getProductPrice(int i) {
		return (WebElement) driver.findElement(By.xpath("(//span[@class='a-size-medium a-color-price sc-price sc-white-space-nowrap sc-product-price sc-price-sign a-text-bold'])[" + i + "]"));
	}
	

	public List<WebElement> productsInCart() {
		List<WebElement> productsCart = driver
				.findElements(By.xpath("//span[@class='a-size-medium sc-product-title a-text-bold']"));
		return productsCart;
	}

	public WebElement cartProductTitle(int i) {
		return (WebElement) driver
				.findElement(By.xpath("(//span[@class='a-size-medium sc-product-title a-text-bold'])[" + i + "]"));
	}
}
