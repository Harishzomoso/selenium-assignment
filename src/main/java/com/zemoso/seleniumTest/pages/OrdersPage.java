package com.zemoso.seleniumTest.pages;


import com.zemoso.seleniumTest.utils.ConfigReader;
import com.zemoso.seleniumTest.utils.WaitUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.List;

public class OrdersPage {

    private final WebDriver driver;
    private final WaitUtils waitUtils;
    private final Actions actions;

    public OrdersPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver,5);
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "nav-link-accountList")  // Update this with actual ID if different
    private WebElement accountPopup;

    @FindBy(linkText = "Your Orders")
    private WebElement yourOrdersLink;

    @FindBy(xpath = "//select[@name='timeFilter']")
    private WebElement yearDropDown;

    @FindBy(xpath = "//li[@class='order-card__list'][1]")
    private WebElement lastOrderCard;

    @FindBy(xpath = ".//a[@class='a-link-normal']")
    private List<WebElement> productLinksInOrder;  // Used in context of lastOrderCard

    public void goToYourOrdersFromAccountPopup() {
        actions.moveToElement(accountPopup).build().perform();
        assertElementVisible(accountPopup, "Account popup details locator is not displayed.");
        waitUtils.waitForClickable(yourOrdersLink);
        yourOrdersLink.click();
    }

    public void selectPastOrderByIndex() {
        Select select = new Select(yearDropDown);
        select.selectByIndex(ConfigReader.getIndex());

        waitUtils.waitForVisibilityOfElement(yearDropDown);

        yearDropDown = driver.findElement(By.xpath("//select[@name='timeFilter']"));
        select = new Select(yearDropDown);

        String selectedOption = select.getFirstSelectedOption().getText();
        waitUtils.waitForURLContains(ConfigReader.getExpectedTextFromDropDown());
        assertTextEquals(
                selectedOption,
                ConfigReader.getExpectedTextFromDropDown(),
                "Selected option in dropdown does not match expected."
        );
    }

    public void clickProductLinkFromLastOrder() {
        List<WebElement> productLinks = lastOrderCard.findElements(By.xpath(".//a[@class='a-link-normal']"));
        assertListNotEmpty(productLinks, "No product links found in the last order placed.");
        productLinks.get(productLinks.size()-1).click();
    }
    private void assertElementVisible(WebElement element, String message) {
        Assert.assertTrue(element.isDisplayed(), message);
    }

    private void assertTextEquals(String actual, String expected, String message) {
        Assert.assertEquals(actual.trim(), expected.trim(), message);
    }

    private void assertListNotEmpty(List<?> list, String message) {
        Assert.assertFalse(list == null || list.isEmpty(), message);
    }
}