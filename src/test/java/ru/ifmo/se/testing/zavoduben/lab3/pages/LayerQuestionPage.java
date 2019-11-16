package ru.ifmo.se.testing.zavoduben.lab3.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LayerQuestionPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public static LayerQuestionPage assumeOpen(WebDriver driver) {
        return new LayerQuestionPage(driver);
    }

    private LayerQuestionPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 5);
    }

    public String getQuestionText() {
        By byXPath = By.xpath("//div[starts-with(@class, 'popup')]//h1");
        WebElement questionLabel = driver.findElement(byXPath);
        return questionLabel.getText();
    }
}
