package ru.ifmo.se.testing.zavoduben.lab3;

import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.ifmo.se.testing.zavoduben.lab3.util.WebDriverFactory;

import java.util.Arrays;
import java.util.function.Supplier;

public class BaseTestConfiguration {
    @Parameterized.Parameters(name = "{1}")
    public static Iterable<Object[]> makeWebDrivers() {
        return Arrays.asList(new Object[][]{
                { (Supplier<WebDriver>) WebDriverFactory::makeChromeDriver, "chrome" },
                { (Supplier<WebDriver>) WebDriverFactory::makeFirefoxDriver, "firefox" },
        });
    }
}
