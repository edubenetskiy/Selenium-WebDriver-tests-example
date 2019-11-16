package ru.ifmo.se.testing.zavoduben.lab3.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Envelope {

    private final String subject;
    private final String recipient;
    private final FolderPage folderPage;
    private final WebElement element;

    public Envelope(String subject, String recipient, FolderPage folderPage, WebElement element) {
        this.subject = subject;
        this.recipient = recipient;
        this.folderPage = folderPage;
        this.element = element;
    }

    public static Envelope from(FolderPage folderPage, WebElement element) {

        String subject = element
                .findElement(By.xpath(".//span[@class='ll-sj__normal']"))
                .getText();

        String recipient = element
                .findElement(By.xpath(".//span[@class='ll-crpt']"))
                .getAttribute("title");

        return new Envelope(subject, recipient, folderPage, element);
    }

    public String getSubject() {
        return subject;
    }

    public String getRecipient() {
        return recipient;
    }

    public ComposePage openToCompose() {
        element.click();
        return ComposePage.assumeOpen(folderPage.getDriver());
    }

    WebElement getElement() {
        return element;
    }

    public MessagePage openToRead() {
        element.click();
        return new MessagePage(folderPage.getDriver());
    }
}
