package com.zemoso.seleniumTest.pages;

import com.zemoso.seleniumTest.utils.ConfigReader;
import com.zemoso.seleniumTest.utils.ScreenshotUtils;
import com.zemoso.seleniumTest.utils.WaitUtils;
import org.openqa.selenium.WebDriver;

public class LandingPage {
    WebDriver driver;
    WaitUtils waitUtils;
    ScreenshotUtils screenshotUtils;
    public LandingPage(WebDriver driver){
        this.driver = driver;
        waitUtils = new WaitUtils(driver,10);
        screenshotUtils = new ScreenshotUtils(driver);
    }

    public void navigateToLandingPage() {
        driver.get(ConfigReader.getUrl());
        waitUtils.waitForURLToBe(ConfigReader.getUrl());
        screenshotUtils.takeScreenshot("LandingPage_Open");
    }

}
