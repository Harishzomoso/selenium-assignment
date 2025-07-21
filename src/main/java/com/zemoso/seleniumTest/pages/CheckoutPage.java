package com.zemoso.seleniumTest.pages;

import com.zemoso.seleniumTest.utils.ConfigReader;
import com.zemoso.seleniumTest.utils.WaitUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class CheckoutPage {

    private final WebDriver driver;
    private final WaitUtils wait;
    private final JavascriptExecutor js;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
        wait = new WaitUtils(driver, 4);
    }

    @FindBy(css = "input[id='buy-now-button']")
    private WebElement buyNowButton;

    @FindBy(xpath = "//a[@data-args='redirectReason=shipaddressselectChangeClicked']")
    private WebElement addressChangeLink;

    private final By deliveryAddressTextBy = By.xpath("//div[@class='a-row a-spacing-base']//h2[contains(.,'Select a delivery')]");
    @FindBy(xpath = "//div[@class='a-row a-spacing-base']//h2[contains(.,'Select a delivery')]")
    private WebElement deliveryAddressText;

    @FindBy(id = "add-new-address-desktop-sasp-tango-link")
    private WebElement addNewAddressLink;

    private final By addressPopupWrapperBy = By.xpath("//div[@class='a-popover-wrapper']");
    @FindBy(xpath = "//div[@class='a-popover-wrapper']")
    private WebElement addressPopupWrapper;

    @FindBy(id = "address-ui-widgets-enterAddressFullName")
    private WebElement nameField;

    @FindBy(id = "address-ui-widgets-enterAddressPhoneNumber")
    private WebElement phoneField;

    @FindBy(id = "address-ui-widgets-enterAddressPostalCode")
    private WebElement pinCodeField;

    @FindBy(id = "address-ui-widgets-enterAddressLine1")
    private WebElement addressLine1;

    @FindBy(id = "address-ui-widgets-enterAddressLine2")
    private WebElement addressLine2;

    @FindBy(name = "address-ui-widgets-enterAddressStateOrRegion")
    private WebElement stateDropdown;

    @FindBy(xpath = "//input[@aria-labelledby='checkout-primary-continue-button-id-announce']")
    private WebElement confirmAddressButton;

    @FindBy(xpath = "//div[@class='a-section']//h2[@id='deliver-to-customer-text']")
    private WebElement deliveryConfirmationText;

    @FindBy(xpath = "//span[contains(.,'Cash on Delivery')]")
    private WebElement cashOnDeliveryOption;

    @FindBy(xpath = "//span[@id='checkout-primary-continue-button-id']//input[@type='submit']")
    private WebElement confirmPaymentButton;

    private static final String javaScriptExecutorMouseScrollScript ="arguments[0].click();";


    public void clickBuyNowButton() {
        js.executeScript("arguments[0].scrollIntoView(true);", buyNowButton);
        wait.waitForClickable(buyNowButton);
        buyNowButton.click();

        wait.waitForURLContains("checkout");
        Assert.assertTrue(driver.getCurrentUrl().contains("checkout"), "Did not navigate to checkout page.");
    }

    public void changeAddressAndValidate() {
        wait.waitForClickable(addressChangeLink);
        addressChangeLink.click();

        wait.waitForVisibilityOfLocator(deliveryAddressTextBy);
        Assert.assertEquals(deliveryAddressText.getText(), "Select a delivery address");
    }

    public void clickAddNewAddressAndFillForm() {
        wait.waitForClickable(addNewAddressLink);
        Assert.assertEquals(addNewAddressLink.getText(), "Add a new delivery address");
        addNewAddressLink.click();

        wait.waitForVisibilityOfLocator(addressPopupWrapperBy);

        nameField.sendKeys(ConfigReader.getFullName());
        phoneField.sendKeys(ConfigReader.getPhone());

        wait.waitForClickable(pinCodeField);
        pinCodeField.sendKeys(ConfigReader.getPincode());

        wait.waitForVisibilityOfElement(addressLine1);
        addressLine1.sendKeys(ConfigReader.getAddressLine1());

        wait.waitForVisibilityOfElement(addressLine2);
        addressLine2.sendKeys(ConfigReader.getAddressLine2());

        js.executeScript("arguments[0].scrollIntoView(true);", stateDropdown);
        wait.waitForVisibilityOfElement(stateDropdown);
        new Select(stateDropdown).selectByValue(ConfigReader.getState());
    }

    public void confirmAddress() {
        wait.waitForClickable(confirmAddressButton);
        js.executeScript(javaScriptExecutorMouseScrollScript, confirmAddressButton);

        wait.waitForVisibilityOfElement(deliveryConfirmationText);
        Assert.assertTrue(deliveryConfirmationText.getText().contains("Delivering to"));
    }

    public void selectCashOnDeliveryAndSubmit() {
        js.executeScript(javaScriptExecutorMouseScrollScript, cashOnDeliveryOption);
        wait.waitForVisibilityOfElement(confirmPaymentButton);
        wait.waitForClickable(confirmPaymentButton);
        Assert.assertTrue(confirmPaymentButton.isEnabled(), "Payment method not selected");
        js.executeScript(javaScriptExecutorMouseScrollScript, confirmPaymentButton);
    }

    public void completeCheckoutFlow() {
        clickBuyNowButton();
        changeAddressAndValidate();
        clickAddNewAddressAndFillForm();
        confirmAddress();
        selectCashOnDeliveryAndSubmit();
    }
}
