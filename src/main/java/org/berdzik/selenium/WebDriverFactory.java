package org.berdzik.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverFactory {

    public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static synchronized WebDriver getDriver() {
        return driver.get();
    }

    public static WebDriver initFirefoxDriver() {
        WebDriverManager.getInstance(FirefoxDriver.class).setup();
        WebDriver webDriver = new FirefoxDriver();
        driver.set(webDriver);
        return getDriver();
    }

    public static WebDriver initChromeDriver() {
        WebDriverManager.getInstance(ChromeDriver.class).setup();
        WebDriver webDriver = new ChromeDriver();
        driver.set(webDriver);
        return getDriver();
    }
}
