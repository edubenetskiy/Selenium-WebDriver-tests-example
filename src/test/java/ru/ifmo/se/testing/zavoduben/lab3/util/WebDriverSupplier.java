package ru.ifmo.se.testing.zavoduben.lab3.util;

import org.openqa.selenium.WebDriver;

import java.util.function.Supplier;

public enum WebDriverSupplier implements Supplier<WebDriver> {
    CHROME(WebDriverFactory::makeChromeDriver),
    FIREFOX(WebDriverFactory::makeFirefoxDriver),
    ;

    private final Supplier<WebDriver> supplier;

    WebDriverSupplier(Supplier<WebDriver> supplier) {
        this.supplier = supplier;
    }

    @Override
    public WebDriver get() {
        return supplier.get();
    }
}
