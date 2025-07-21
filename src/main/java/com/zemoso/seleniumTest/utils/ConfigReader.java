package com.zemoso.seleniumTest.utils;

import java.io.*;
import java.util.Properties;

public class ConfigReader {
    private static final Properties props = new Properties();

    static {
        try (InputStream input =
                     Thread.currentThread()
                             .getContextClassLoader()
                             .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new FileNotFoundException("config.properties not found in test classpath");
            }
            props.load(input);
        }catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String getUrl() {
        return props.getProperty("url");
    }

    public static String getUsername() {
        return props.getProperty("username");
    }

    public static String getPassword() {
        return props.getProperty("password");
    }

    public static String getBrowser() {
        return  props.getProperty("browser", "chrome");
    }
    public static String getFullName() {
        return props.getProperty("fullName");
    }

    public static String getPhone() {
        return props.getProperty("phone");
    }

    public static String getPincode() {
        return props.getProperty("pincode");
    }

    public static String getAddressLine1() {
        return props.getProperty("addressLine1");
    }

    public static String getAddressLine2() {
        return props.getProperty("addressLine2");
    }

    public static String getState() {
        return props.getProperty("state");
    }
    public static int getIndex(){
        return Integer.parseInt(props.getProperty("index"));
    }
    public static String getExpectedTextFromDropDown() {
        return props.getProperty("textExpectedInDropDown");
    }
    public static int getDealNo(){
        return Integer.parseInt(props.getProperty("dealToBeSelected"));
    }
    public static String getProductName() {
        return props.getProperty("productName");
    }
}

