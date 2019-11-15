package ru.ifmo.se.testing.zavoduben.lab3.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

class WebDriverFactory {

    private static final int PAGE_LOAD_TIMEOUT_SECONDS = 45;
    private static final int IMPLICIT_WAIT_SECONDS = 30;

    static {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/web-drivers/win/ChromeDriver.exe");
        System.setProperty("webdriver.gecko.driver", "src/test/resources/web-drivers/win/GeckoDriver.exe");
    }

    static ChromeDriver makeChromeDriver() {
        ChromeDriverService chromeDriverService = ChromeDriverService.createDefaultService();
        ChromeOptions chromeOptions = new ChromeOptions()
                .addArguments("--incognito")
                .addArguments("--lang=ru")
                .setExperimentalOption("prefs",
                        Collections.singletonMap("intl.accept_languages", "ru"));
        ChromeDriver webDriver = new ChromeDriver(chromeDriverService, chromeOptions);
        return configureWebDriver(webDriver);
    }

    static FirefoxDriver makeFirefoxDriver() {
        GeckoDriverService geckoDriverService = GeckoDriverService.createDefaultService();
        FirefoxOptions firefoxOptions = new FirefoxOptions()
                .addArguments("-private-window")
                .addPreference("intl.accept_languages", "ru-RU");
        FirefoxDriver webDriver = new FirefoxDriver(geckoDriverService, firefoxOptions);
        return configureWebDriver(webDriver);
    }

    private static <D extends WebDriver> D configureWebDriver(D webDriver) {
        webDriver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        webDriver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_SECONDS, TimeUnit.SECONDS);
        return webDriver;
    }
}
