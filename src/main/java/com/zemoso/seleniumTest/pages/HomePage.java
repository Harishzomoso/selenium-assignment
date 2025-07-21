package com.zemoso.seleniumTest.pages;

import com.zemoso.seleniumTest.utils.WaitUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;


public class HomePage {
    WebDriver driver;
    WaitUtils waitUtils;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        waitUtils = new WaitUtils(driver,10);
    }
    @FindBy(id="nav-link-accountList-nav-line-1")
    WebElement accountListLink;
    public void verifyUserLoggedIn() {
        System.out.println("Verifying user login status...");
        waitUtils.waitForVisibilityOfElement(accountListLink);
        String accountText = accountListLink.getText();
        Assert.assertTrue(accountText.contains("Hello,"),
                "Login failed: 'Hello,' prefix not found in account link. Text was: " + accountText);
        System.out.println("User successfully logged in.");
    }
}
