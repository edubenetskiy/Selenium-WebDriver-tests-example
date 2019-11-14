package ru.ifmo.se.testing.zavoduben.lab3.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;

import java.util.concurrent.TimeUnit;

public class WebDriverFactory {
    static {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/web-drivers/ChromeDriver.exe");
        System.setProperty("webdriver.gecko.driver", "src/test/resources/web-drivers/GeckoDriver.exe");
    }

    public static ChromeDriver makeChromeDriver() {
        ChromeDriverService chromeDriverService = ChromeDriverService.createDefaultService();
        ChromeOptions chromeOptions = new ChromeOptions().addArguments("--incognito");
        ChromeDriver webDriver = new ChromeDriver(chromeDriverService, chromeOptions);
        return configureWebDriver(webDriver);
    }

    public static FirefoxDriver makeFirefoxDriver() {
        GeckoDriverService geckoDriverService = GeckoDriverService.createDefaultService();
        FirefoxOptions firefoxOptions = new FirefoxOptions().addArguments("-private-window");
        FirefoxDriver webDriver = new FirefoxDriver(geckoDriverService, firefoxOptions);
        return configureWebDriver(webDriver);
    }

    private static <D extends WebDriver> D configureWebDriver(D webDriver) {
        webDriver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return webDriver;
    }

    public static WebDriver makeDriverForBrowserName(String browserName) {
        switch (browserName) {
            case "chrome":
                return makeChromeDriver();
            case "firefox":
                return makeFirefoxDriver();
            default:
                throw new IllegalArgumentException("Browser name '" + browserName + "' not supported");
        }
    }
}
