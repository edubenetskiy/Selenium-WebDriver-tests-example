package ru.ifmo.se.testing.zavoduben.lab3.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;

public class LayerSentPage {

    private final WebDriver driver;
    private WebDriverWait wait;

    private LayerSentPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 5);
    }

    public static LayerSentPage assumeOpen(WebDriver driver) {
        return new LayerSentPage(driver);
    }

    public String getHeaderText() {
        return findLayerHeader().getText();
    }

    private WebElement findLayerHeader() {
        return driver.findElement(By.xpath("//div[contains(concat(' ',normalize-space(@class),' '),' layer__header ')]"));
    }

    public FolderPage closeModal() {
        By byXPath = By.xpath("//span[contains(concat(' ',normalize-space(@class),' '),' button2_close ')]");
        WebElement closeButton = driver.findElement(byXPath);
        wait.until(elementToBeClickable(closeButton));
        closeButton.click();

        waitDimmerToHide();

        return FolderPage.assumeOpen(driver);
    }

    private void waitDimmerToHide() {
        By byXPath = By.xpath("//*[@id='dimmer' or @class='dimmer']");
        ExpectedCondition<Boolean> dimmerIsHidden =
                invisibilityOfElementLocated(byXPath);
        wait.until(dimmerIsHidden);
    }
}
