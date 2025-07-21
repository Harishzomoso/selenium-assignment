package com.zemoso.seleniumTest;

import com.zemoso.seleniumTest.listeners.TestListener;
import com.zemoso.seleniumTest.pages.BooksPage;
import com.zemoso.seleniumTest.pages.CheckoutPage;
import com.zemoso.seleniumTest.pages.LoginPage;
import com.zemoso.seleniumTest.pages.OrdersPage;
import com.zemoso.seleniumTest.utils.ConfigReader;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
public class AmazonFlowSecondTest extends BaseTest{


    @Test(priority = 1)
    void loginTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage();
        loginPage.loginApplicationWithValidUserName(ConfigReader.getUsername());
        loginPage.loginApplicationWithValidPassword(ConfigReader.getPassword());
    }
    @Test(priority = 2)
    public void bookTest() {
        BooksPage booksPage = new BooksPage(driver);
        booksPage.clickBooksTab();
        booksPage.applyPrimeCheckboxFilter();
        booksPage.verifyBooksListedAndPrintDeliveryDate();
    }
    @Test(priority = 3)
    void ordersTest() {
        OrdersPage page = new OrdersPage(driver);
        page.goToYourOrdersFromAccountPopup();
        page.selectPastOrderByIndex();
        page.clickProductLinkFromLastOrder();
    }
    @Test(priority = 4)
    void checkoutTest() {
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.completeCheckoutFlow();
    }

}
