package ru.ifmo.se.testing.zavoduben.lab3.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Envelope {

    private final String subject;

    public Envelope(String subject) {
        this.subject = subject;
    }

    public static Envelope from(WebElement element) {
        By byXPath = By.xpath(".//span[@class='ll-sj__normal']");
        WebElement messageElement = element.findElement(byXPath);
        String subject = messageElement.getText();
        return new Envelope(subject);
    }

    public String getSubject() {
        return subject;
    }
}
