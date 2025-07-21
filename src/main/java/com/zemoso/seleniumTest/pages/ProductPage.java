package com.zemoso.seleniumTest.pages;

import com.zemoso.seleniumTest.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

public class ProductPage {
    WebDriver driver;
    WaitUtils waitUtils;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        waitUtils = new WaitUtils(driver, 2);
    }

    @FindBy(xpath = "//div[@class='a-section a-spacing-none a-padding-none']//input[@id='add-to-cart-button']")
    WebElement addToCartButton;


    @FindBy(xpath = "//span[@id='a-autoid-6-announce']")
    WebElement quantityDropdownLabel;

    @FindBy(xpath = "//div[@class='a-popover-inner']//ul[@role='listbox']/li")
    List<WebElement> quantityOptions;
    public void selectMinimumQuantityFromDropdown() {
        try{
            waitUtils.waitForVisibilityOfElement(quantityDropdownLabel);
            assertDropdownClickable();
            quantityDropdownLabel.click();

            waitUtils.waitForVisibilityOfLocator(By.xpath("//div[@class='a-popover-inner']//ul[@role='listbox']/li"));
            assertOptionsNotEmpty();

            WebElement minimumQty = quantityOptions.get(0);
            minimumQty.click();
            System.out.println("Minimum quantity selected.");
        }
        catch(Exception e){
            System.out.println("Quantity dropdown not available, proceeding with default quantity.");
        }
    }

    public void addToCart() {
        waitUtils.waitForVisibilityOfElement(addToCartButton);
        addToCartButton.click();
        System.out.println("Product added to cart.");
    }
    private void assertDropdownClickable() {
        Assert.assertTrue(quantityDropdownLabel.isEnabled(), "Dropdown container is not clickable.");
    }

    private void assertOptionsNotEmpty() {
        Assert.assertFalse(quantityOptions.isEmpty(), "Quantity dropdown options are empty.");
    }
}

