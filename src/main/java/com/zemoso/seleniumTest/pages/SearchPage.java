package com.zemoso.seleniumTest.pages;

import com.zemoso.seleniumTest.utils.ConfigReader;
import com.zemoso.seleniumTest.utils.ScreenshotUtils;
import com.zemoso.seleniumTest.utils.WaitUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.Optional;

public class SearchPage {

    private final WebDriver driver;
    private final WaitUtils waitUtils;
    private final ScreenshotUtils screenshotUtils;

    public SearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        waitUtils = new WaitUtils(driver, 5);
        screenshotUtils = new ScreenshotUtils(driver);
    }

    @FindBy(id = "twotabsearchtextbox")
    private WebElement searchTextBox;

    @FindBy(id = "nav-search-submit-button")
    private WebElement searchButton;

    @FindBy(xpath = "(//div[@role='listitem' and @data-component-type='s-search-result'])[last()]")
    private WebElement lastItem;

    @FindBy(id = "nav-hamburger-menu")
    private WebElement hamburgerMenuButton;

    @FindBy(id = "hmenu-content")
    private WebElement hamburgerMenuContent;

    @FindBy(xpath = "//a[@class='hmenu-item']//div[contains(text(),'Mobiles')]")
    private WebElement leftNavMobile;

    @FindBy(xpath = "//div[@class='hmenu hmenu-visible hmenu-translateX']//div[contains(text(),'main menu')]")
    private WebElement mainMenuLink;

    public void openHomePage() {
        String url = ConfigReader.getUrl();
        driver.get(url);
        waitUtils.waitForURLToBe(url);
        screenshotUtils.takeScreenshot("SearchPage_OpenHome");
    }

    public void searchForMobiles(String productName) {
        waitUtils.waitForClickable(searchTextBox);
        searchTextBox.clear();
        searchTextBox.sendKeys(productName);
        assertSearchBoxValue(productName);

        searchButton.click();
        waitUtils.waitForTitleContains("Mobiles");
        assertTitleContains(productName);
        screenshotUtils.takeScreenshot("SearchPage_AfterSearch");
    }

    public void scrollToLastItem() {
        waitUtils.waitForVisibilityOfElement(lastItem);
        assertElementVisible(lastItem, "Last item not visible in search results.");
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", lastItem);
        screenshotUtils.takeScreenshot("SearchPage_ScrolledToLastItem");
    }

    public String retrieveLastProductTitle() {
        String title ="";
        try {
            WebElement titleElement = lastItem.findElement(By.xpath(".//div[@data-cy='title-recipe']/a/h2/span"));
            assertElementVisible(titleElement, "Title element not displayed.");

            title = titleElement.getText();
            assertTextNotEmpty(title, "Product title is empty.");

            screenshotUtils.takeScreenshot("SearchPage_LastProductTitle");

        } catch (NoSuchElementException e) {
            Assert.fail("Could not locate last product title: " + e.getMessage());
        }
        return title;
    }

    public Optional<String> retrieveLastProductRating() {
        try {
            WebElement ratingElement = lastItem.findElement(By.xpath(".//i[contains(@class,'a-icon-star')]//span[@class='a-icon-alt']"));
            String rating = ratingElement.getAttribute("textContent").trim();

            assertTextNotEmpty(rating, "Rating text is empty.");
            assertTextContains(rating, "out of 5 stars", "Rating format is unexpected: " + rating);

            screenshotUtils.takeScreenshot("SearchPage_LastProductRating");
            return Optional.of(rating);
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public void navigateToMobilesFromLeftNavAndReturnToHome() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");

        waitUtils.waitForClickable(hamburgerMenuButton);
        hamburgerMenuButton.click();

        waitUtils.waitForVisibilityOfElement(hamburgerMenuContent);
        assertElementVisible(hamburgerMenuContent, "Hamburger menu is not visible.");

        waitUtils.waitForClickable(leftNavMobile);
        leftNavMobile.click();

        waitUtils.waitForVisibilityOfElement(mainMenuLink);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", mainMenuLink);

        waitUtils.waitForVisibilityOfElement(hamburgerMenuContent);
        assertElementVisible(hamburgerMenuContent, "Hamburger menu not visible after navigating back.");

        screenshotUtils.takeScreenshot("SearchPage_HamburgerNavigation");
    }
    private void assertSearchBoxValue(String expected) {
        Assert.assertEquals(searchTextBox.getAttribute("value"), expected, "Search term not entered correctly.");
    }

    private void assertTitleContains(String keyword) {
        Assert.assertTrue(driver.getTitle().contains(keyword), "Title does not contain search keyword.");
    }

    private void assertElementVisible(WebElement element, String message) {
        Assert.assertTrue(element.isDisplayed(), message);
    }

    private void assertTextNotEmpty(String text, String message) {
        Assert.assertFalse(text.trim().isEmpty(), message);
    }

    private void assertTextContains(String actual, String expected, String message) {
        Assert.assertTrue(actual.contains(expected), message);
    }
}
