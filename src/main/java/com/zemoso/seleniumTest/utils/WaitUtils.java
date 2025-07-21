package com.zemoso.seleniumTest.utils;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class WaitUtils {

    private WebDriverWait wait;

    public WaitUtils(WebDriver driver, int timeoutInSeconds) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
    }

    public void waitForVisibilityOfElement(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }
    public void waitForVisibilityOfLocator(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    public void waitForPresence(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    public void waitForNumberOfElementsToBeMoreThan(By locator, int number) {
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(locator, number));
    }
    public void waitForURLToBe(String homePageUrl) {
        wait.until(ExpectedConditions.urlToBe(homePageUrl));
    }
    public void waitForURLContains(String urlPart) {
        wait.until(ExpectedConditions.urlContains(urlPart));
    }
    public void waitForTitleContains(String mobiles) {
        wait.until(ExpectedConditions.titleContains(mobiles));
    }
}
