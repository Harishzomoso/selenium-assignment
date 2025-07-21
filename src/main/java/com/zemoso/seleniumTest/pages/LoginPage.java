package com.zemoso.seleniumTest.pages;

import com.zemoso.seleniumTest.utils.ConfigReader;
import com.zemoso.seleniumTest.utils.ScreenshotUtils;
import com.zemoso.seleniumTest.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class LoginPage {
    WebDriver driver;
    WaitUtils waitUtils;
    ScreenshotUtils screenshotUtils;
    Actions actions;
    public LoginPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
        waitUtils = new WaitUtils(driver,10);
        screenshotUtils = new ScreenshotUtils(driver);
        actions = new Actions(driver);
    }
    @FindBy(css = "span[id='nav-link-accountList-nav-line-1']")
    WebElement signInPopupLocator;
    @FindBy(xpath = "//span[@class='nav-action-inner']")
    WebElement dynamicSignInButton;


    @FindBy(id="ap_email_login")
    WebElement txtBoxEmail;
    @FindBy(xpath = "//input[@type='submit']")
    WebElement continueButton;
    @FindBy(xpath = "//input[@id='ap_password']")
    WebElement txtBoxPassword;
    @FindBy(xpath = "//input[@id='signInSubmit']")
    WebElement submitPasswordButton;

    @FindBy(xpath = "//span[@id='nav-link-accountList-nav-line-1']")
    WebElement signInTextLocator;

   @FindBy(css = "div[id='invalid-email-alert'] div[class='a-alert-content']")
//    @FindBy(xpath = "//div[@id='empty-claim-alert']//div[contains(text(),'Enter your mobile number or email')]")
    WebElement userNameInvalidError;

   @FindBy(xpath = "//div[contains(text(),'Your password is incorrect')]")
   WebElement passwordInvalidError;
   @FindBy(xpath = "//div[@id='empty-claim-alert']")
   WebElement emptyEmailError;
   @FindBy(xpath = "//div[contains(text(),'Enter your password')]")
   WebElement enterPasswordError;

    public void navigateToLoginPage() {
        actions.moveToElement(signInPopupLocator).build().perform();
        assertElementVisible(signInPopupLocator, "Sign-in locator is not displayed.");

        assertElementVisible(dynamicSignInButton, "Sign-in button is not displayed.");
        actions.moveToElement(dynamicSignInButton).click().build().perform();
        assertURLContains("ap/signin?");
    }

    public void loginApplicationWithValidUserName(String userName){
        assertElementVisible(txtBoxEmail, "Email text box is not displayed.");
        waitUtils.waitForVisibilityOfElement(txtBoxEmail);

        txtBoxEmail.sendKeys(userName);
        assertInputValue(txtBoxEmail, userName, "Email not entered correctly.");

        screenshotUtils.takeScreenshot("LoginPage_AfterEnteringEmail");

        waitUtils.waitForClickable(continueButton);
        assertElementEnabled(continueButton, "Continue button is not enabled.");
        continueButton.click();
    }
    public void loginApplicationWithValidPassword(String passWord) {
        assertElementEnabled(txtBoxPassword, "Password text box is not enabled.");
        waitUtils.waitForVisibilityOfElement(txtBoxPassword);

        txtBoxPassword.sendKeys(passWord);
        assertInputValue(txtBoxPassword, passWord, "Password not entered correctly.");
        screenshotUtils.takeScreenshot("afterEnteringPassword");

        waitUtils.waitForClickable(submitPasswordButton);
        submitPasswordButton.click();

        assertTextContains(signInTextLocator, ConfigReader.getFullName(), "Signed-in name not matched.");

        screenshotUtils.takeScreenshot("LoginPage_AfterLogin");
    }
    public void loginWithInvalidUsername(String userName) {
        assertElementVisible(txtBoxEmail, "Email text box is not displayed.");
        waitUtils.waitForVisibilityOfElement(txtBoxEmail);
        txtBoxEmail.sendKeys(userName);
        continueButton.click();

        if(userName.trim().isEmpty()){
            waitUtils.waitForVisibilityOfElement(emptyEmailError);
            assertElementTextEquals(emptyEmailError, "Enter your mobile number or email");
        }
        else{
            waitUtils.waitForVisibilityOfElement(userNameInvalidError);
            String alertText = userNameInvalidError.getText().trim();
            Assert.assertTrue(alertText.contains("Invalid"), "Username error does not contain 'Invalid'");
        }
    }

    public void loginWithInvalidPassword(String password) {
        assertElementVisible(txtBoxPassword, "Password text box is not displayed.");
        waitUtils.waitForVisibilityOfElement(txtBoxPassword);
        txtBoxPassword.sendKeys(password.trim());
        submitPasswordButton.click();

        if(password.trim().isEmpty()){
            waitUtils.waitForVisibilityOfElement(enterPasswordError);
            assertElementTextEquals(enterPasswordError, "Enter your password");
        }
        else{
            waitUtils.waitForVisibilityOfElement(passwordInvalidError);
            assertElementTextEquals(passwordInvalidError, "Your password is incorrect");
        }
    }
    private void assertElementVisible(WebElement element, String message) {
        Assert.assertTrue(element.isDisplayed(), message);
    }

    private void assertElementEnabled(WebElement element, String message) {
        Assert.assertTrue(element.isEnabled(), message);
    }

    private void assertURLContains(String partialUrl) {
        Assert.assertTrue(driver.getCurrentUrl().contains(partialUrl),
                "Current URL does not contain: " + partialUrl);
    }

    private void assertInputValue(WebElement element, String expectedValue, String message) {
        Assert.assertEquals(element.getAttribute("value"), expectedValue, message);
    }

    private void assertTextContains(WebElement element, String expectedSubstring, String message) {
        Assert.assertTrue(element.getText().contains(expectedSubstring), message);
    }

    private void assertElementTextEquals(WebElement element, String expectedText) {
        Assert.assertEquals(element.getText().trim(), expectedText, "Error message mismatch.");
    }
}
