package ru.ifmo.se.testing.zavoduben.lab3.pages;

import org.openqa.selenium.WebDriver;
import ru.ifmo.se.testing.zavoduben.lab3.util.Constants;

public enum Folder {
    INBOX("/inbox/"),
    DRAFTS("/drafts/"),
    TRASH("/trash/"),
    SENT("/sent/"),
    ;

    private final String path;

    Folder(String path) {
        this.path = path;
    }

    public FolderPage open(WebDriver driver) {
        return FolderPage.open(this, driver);
    }

    public String getUrl() {
        return Constants.BASE_URL + path;
    }

    public String getPath() {
        return path;
    }
}
