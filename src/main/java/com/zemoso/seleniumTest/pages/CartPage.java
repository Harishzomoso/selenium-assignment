package com.zemoso.seleniumTest.pages;

import com.zemoso.seleniumTest.utils.ScreenshotUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class CartPage {

    private WebDriver driver;
    private WebDriverWait wait;
    ScreenshotUtils screenshotUtils;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        screenshotUtils = new ScreenshotUtils(driver);
    }

    @FindBy(xpath = "//span[@id='sw-gtc']/span/a")
    private WebElement goToCartButton;

    @FindBy(xpath = "//div[@class='a-declarative']/span[@data-a-selector='value']")
    private List<WebElement> cartQuantityElements;

    public void navigateToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(goToCartButton));
        goToCartButton.click();
        assertOnTitleAndURL();
        screenshotUtils.takeScreenshot("CartPage");
    }

    public void verifyCartQuantity(String expectedQuantity) {
        assertCartNotEmpty();
        assertCartQuantity(expectedQuantity);
    }
    private void assertOnTitleAndURL() {
        Assert.assertTrue(wait.until(ExpectedConditions.urlContains("/cart")), "Did not navigate to cart page.");
        Assert.assertEquals(driver.getTitle(), "Amazon.in Shopping Cart", "Cart page title mismatch.");
    }

    private void assertCartNotEmpty() {
        Assert.assertFalse(cartQuantityElements.isEmpty(), "Cart is empty or quantity element not found.");
    }

    private void assertCartQuantity(String expectedQuantity) {
        String actualQuantity = cartQuantityElements.get(0).getText();
        Assert.assertEquals(actualQuantity, expectedQuantity,
                "Cart quantity mismatch. Expected: " + expectedQuantity + ", Actual: " + actualQuantity);
    }
}

