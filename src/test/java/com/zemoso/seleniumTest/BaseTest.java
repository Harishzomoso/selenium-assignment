package com.zemoso.seleniumTest;

import com.zemoso.seleniumTest.pages.LandingPage;
import com.zemoso.seleniumTest.utils.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;

    @BeforeTest
    public void setUp() {
        System.out.println("Inside line 15");
        driver = WebDriverFactory.createDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.manage().window().maximize();

        LandingPage landingPage = new LandingPage(driver);
        landingPage.navigateToLandingPage();
    }

    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
