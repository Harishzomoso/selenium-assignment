package com.zemoso.seleniumTest;

import com.zemoso.seleniumTest.listeners.TestListener;
import com.zemoso.seleniumTest.pages.*;
import com.zemoso.seleniumTest.utils.ConfigReader;
import com.zemoso.seleniumTest.utils.WebDriverFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.Optional;

@Listeners(TestListener.class)
public class AmazonFlowFirstTest extends BaseTest{

    @Test(priority =1)
    void selectDealsFromTodaysDeals(){
        DealsPage dealsPage = new DealsPage(driver);
        dealsPage.navigateToTodaysDealPage("Amazon.in - Deals");
        dealsPage.selectSpecificDeal(ConfigReader.getDealNo());
    }

    @Test(priority =2)
    void selectMinimumQuantityFromDropdown(){
        ProductPage productPage = new ProductPage(driver);
        productPage.selectMinimumQuantityFromDropdown();
        productPage.addToCart();
    }
    @Test(priority =3)
    void verifyQtyInCartPage(){
        CartPage cartPage = new CartPage(driver);
        cartPage.navigateToCart();
        cartPage.verifyCartQuantity("1");
    }

    @Test(priority =4)
    void searchForProductAndDisplayTheDetails(){
        SearchPage searchPage = new SearchPage(driver);

        searchPage.openHomePage();
        searchPage.searchForMobiles(ConfigReader.getProductName());
        searchPage.scrollToLastItem();

        String title = searchPage.retrieveLastProductTitle();
        System.out.println("Last Displayed item details: " + title);
        Optional<String> rating = searchPage.retrieveLastProductRating();

        Assert.assertFalse(title.isEmpty(), "Received empty title.");
        rating.ifPresent(r -> Assert.assertTrue(r.contains("out of 5 stars"), "Rating format invalid"));
    }

    @Test(priority =5)
    void navigateToLeftSideNav() {
        SearchPage searchPage = new SearchPage(driver);
        searchPage.navigateToMobilesFromLeftNavAndReturnToHome();
    }


    @Test(groups="smoke",priority = 1, dataProvider = "invalidUsernames")
    void invalidUserNameForLogin(String username) {
        driver = WebDriverFactory.createDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.manage().window().maximize();

        LandingPage landingPage = new LandingPage(driver);
        landingPage.navigateToLandingPage();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage();
        loginPage.loginWithInvalidUsername(username);

        if (driver != null) {
            driver.quit();
        }
    }
    @Test(groups="smoke",priority = 2,dataProvider = "invalidPasswords")
    void invalidPasswordForLogin(String password) {
        driver = WebDriverFactory.createDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.manage().window().maximize();

        LandingPage landingPage = new LandingPage(driver);
        landingPage.navigateToLandingPage();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage();
        loginPage.loginApplicationWithValidUserName(ConfigReader.getUsername());
        loginPage.loginWithInvalidPassword(password);

        if (driver != null) {
            driver.quit();
        }
    }
    @DataProvider(name = "invalidUsernames")
    public Object[][] invalidUsernames() {
        return new Object[][] {
                {" "},
                { "wrongUser" },
                { "admin123" },
                { "user!" }
        };
    }

    @DataProvider(name = "invalidPasswords")
    public Object[][] invalidPasswords() {
        return new Object[][] {
                { "wrongPass" },
                { "password!" },{" "}
        };
    }

}
