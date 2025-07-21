package com.zemoso.seleniumTest;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class SampleTests {

    @Test
    public void sampleTest() {
        System.out.println("This is a sample test method.");
        WebDriver driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/AutomationPractice/");

//        List<WebElement> listOfRadio = driver.findElements(By.cssSelector("div[id='radio-btn-example'] input"));
//        System.out.println(listOfRadio.size());
//
//        listOfRadio.get(1).click();
//
//        WebElement autoComplete = driver.findElement(By.xpath("//input[@id='autocomplete']"));
//        autoComplete.sendKeys("in");

//        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[@class='ui-menu-item']")));
//        List<WebElement> listOfValues = driver.findElements(By.xpath("//li[@class='ui-menu-item']"));
//        System.out.println(listOfValues.size());
//        for(WebElement sugg:listOfValues){
//            System.out.println(sugg.getText());
//            if(sugg.getText().equalsIgnoreCase("india")){
//                sugg.click();
//            }
//        }

//        WebElement openWindowElement = driver.findElement(By.cssSelector("div.left-align button.btn-style.class1"));
//        openWindowElement.click();
//        Set<String> window = driver.getWindowHandles();
//        for(String handle:window){
//            if(!handle.equals(driver.getWindowHandle())){
//                driver.switchTo().window(handle);
//            }
//
//        }
        Actions actions = new Actions(driver);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0,1000);");

        WebElement alertControl = driver.findElement(By.cssSelector("fieldset.pull-right input.inputs"));
        actions.moveToElement(alertControl).click().keyDown(Keys.SHIFT).sendKeys("harish").keyUp(Keys.SHIFT).build().perform();

        WebElement alertButton = driver.findElement(By.cssSelector("fieldset.pull-right input[id='alertbtn']"));
        alertButton.click();
        Alert alert  = driver.switchTo().alert();

        System.out.println(alert.getText());
        alert.accept();
    }
}
