package com.zemoso.seleniumTest.pages;

import com.zemoso.seleniumTest.utils.ScreenshotUtils;
import com.zemoso.seleniumTest.utils.WaitUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;

public class BooksPage {
    WebDriver driver;
    WaitUtils waitUtils;
    JavascriptExecutor js;
    ScreenshotUtils screenshotUtils;

    public BooksPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver, 10);
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
        screenshotUtils = new ScreenshotUtils(driver);
    }

    @FindBy(linkText = "Books")
    private WebElement booksTab;

    @FindBy(xpath = "//label[@for='apb-browse-refinements-checkbox_0']//i[@class='a-icon a-icon-checkbox']")
    private WebElement primeCheckbox;

    @FindBy(css = "div[role='listitem']")
    private List<WebElement> listOfBooks;

    @FindBy(xpath = "//span[@class='a-color-base a-text-bold']")
    private WebElement deliveryDateLabel;

    public void clickBooksTab() {
        booksTab.click();
    }

    public void applyPrimeCheckboxFilter() {
        js.executeScript("arguments[0].scrollIntoView(true);", primeCheckbox);
        waitUtils.waitForClickable(primeCheckbox);
        primeCheckbox.click();
    }

    public void verifyBooksListedAndPrintDeliveryDate() {
        assertBooksListNotEmpty();
        WebElement firstBook = listOfBooks.get(0);
        WebElement deliveryDateElement = firstBook.findElement(By.xpath(".//span[@class='a-color-base a-text-bold']"));
        String deliveryDate = deliveryDateElement.getText();
        assertTextNotEmpty(deliveryDate, "Delivery date for first book is empty.");
        System.out.println("First Book Delivery Date: " + deliveryDate);
        screenshotUtils.takeScreenshot("DeliveryStatusOfFirstBook");
    }

    private void assertBooksListNotEmpty() {
        Assert.assertFalse(listOfBooks.isEmpty(), "No books found after applying Prime filter.");
    }

    private void assertTextNotEmpty(String text, String message) {
        Assert.assertFalse(text == null || text.trim().isEmpty(), message);
    }
}
