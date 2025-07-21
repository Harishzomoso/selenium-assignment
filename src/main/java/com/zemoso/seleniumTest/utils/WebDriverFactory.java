package com.zemoso.seleniumTest.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class WebDriverFactory {
    static WebDriver driver ;

    public static WebDriver createDriver() {
        String browser = ConfigReader.getBrowser();
        driver = createLocalDriver(browser);
        return driver;
    }

    private static WebDriver createLocalDriver(String browser) {
        return switch (browser.toLowerCase()) {
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                yield new FirefoxDriver(new FirefoxOptions());
            }
            case "chrome" -> {
                WebDriverManager.chromedriver().setup();
                yield new ChromeDriver(new ChromeOptions());
            }
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        };
    }
    public static WebDriver getDriver(){
        return  driver;
    }
}
