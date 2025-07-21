package com.zemoso.seleniumTest.pages;

import com.zemoso.seleniumTest.utils.ScreenshotUtils;
import com.zemoso.seleniumTest.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;
import java.util.Objects;

public class DealsPage {

    WebDriver driver;
    WaitUtils waitUtils;
    ScreenshotUtils screenshotUtils;

    public DealsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        waitUtils = new WaitUtils(driver, 5);
        screenshotUtils = new ScreenshotUtils(driver);
    }
    @FindBy(xpath = "//a[@href='/deals?ref_=nav_cs_gb']")
    WebElement todaysDealTab;

    @FindBy(xpath = "//div[@data-csa-c-item-type='deal']")
    List<WebElement> allDeals;

    public void navigateToTodaysDealPage(String expectedTitle) {
        waitUtils.waitForVisibilityOfElement(todaysDealTab);
        todaysDealTab.click();
        assertOnTitleAndURL(expectedTitle);
        screenshotUtils.takeScreenshot("AfterNavigatingToDealsPage");
    }

    public void selectSpecificDeal(int dealNo) {
        By dealLocator = By.xpath("//div[@data-csa-c-item-type='deal']");
        waitUtils.waitForNumberOfElementsToBeMoreThan(dealLocator, 0);

        assertDealCount(dealNo);

        WebElement targetDeal = allDeals.get(dealNo - 1);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", targetDeal);

        assertDealIsDisplayed(targetDeal, dealNo);
        targetDeal.click();
        screenshotUtils.takeScreenshot("AfterSelectingDeal");
    }

    private void assertOnTitleAndURL(String expextedTitle) {
        Assert.assertEquals(driver.getTitle().trim(), expextedTitle,
                "Page title not contains Deals after clicking Today's Deals tab.");
        Assert.assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("/deals"),
                "URL does not contain '/deals' after navigating to Today's Deals.");
    }

    private void assertDealCount(int dealNo) {
        Assert.assertTrue(allDeals.size() >= dealNo,
                "Less than " + dealNo + " deals found on the page. Cannot click the " + dealNo + "th deal.");
    }

    private void assertDealIsDisplayed(WebElement deal, int dealNo) {
        Assert.assertTrue(deal.isDisplayed(), "Target deal " + dealNo + " is not displayed.");
    }
}
