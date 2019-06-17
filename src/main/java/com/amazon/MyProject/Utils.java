package com.amazon.MyProject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Utils {
	public WebDriver driver;

	@FindBy(xpath = "//a[@href='https://www.amazon.in/gp/cart/view.html?ref_=nav_cart']")
	public WebElement cart;

	Utils(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	public List<WebElement> products() {
		List<WebElement> product = driver
				.findElements(By.xpath("//span[@class='a-declarative']/input[@value='Delete']"));
		return product;
	}

	public WebElement delete() {
		return (WebElement) driver.findElements(By.xpath("//span[@class='a-declarative']/input[@value='Delete'][1]"));

	}

	public void prerequisiteToClearCart() {
		cart.click();
		while (products().size() != 0) {
			delete();

		}

	}

}
