package ru.ifmo.se.testing.zavoduben.lab3.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Envelope {

    private final String subject;
    private final String recipient;

    public Envelope(String subject, String recipient) {
        this.subject = subject;
        this.recipient = recipient;
    }

    public static Envelope from(WebElement element) {

        String subject = element
                .findElement(By.xpath(".//span[@class='ll-sj__normal']"))
                .getText();

        String recipient = element
                .findElement(By.xpath(".//span[@class='ll-crpt']"))
                .getAttribute("title");

        return new Envelope(subject, recipient);
    }

    public String getSubject() {
        return subject;
    }

    public String getRecipient() {
        return recipient;
    }
}
