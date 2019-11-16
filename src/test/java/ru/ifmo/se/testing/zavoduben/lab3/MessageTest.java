package ru.ifmo.se.testing.zavoduben.lab3;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.ifmo.se.testing.zavoduben.lab3.fixtures.User;
import ru.ifmo.se.testing.zavoduben.lab3.fixtures.UserFixtures;
import ru.ifmo.se.testing.zavoduben.lab3.pages.*;
import ru.ifmo.se.testing.zavoduben.lab3.util.WebDriverSupplier;

import java.util.Optional;

import static junit.framework.TestCase.assertTrue;

@RunWith(Parameterized.class)
public class MessageTest extends BaseTestConfiguration {

    private final WebDriver driver;
    private FolderPage inboxPage;

    public MessageTest(WebDriverSupplier driverSupplier) {
        this.driver = driverSupplier.get();
    }

    @Before
    public void logInAndOpenInbox() {
        User user = UserFixtures.getAnyUser();
        LoginPage loginPage = LoginPage.open(driver);
        this.inboxPage = loginPage.loginAs(user);
    }

    @After
    public void quitBrowser() {
        driver.quit();
    }

    @Test
    public void removeFromInboxMovesToTrash() {
        Envelope envelop = inboxPage.getAnyEnvelope();
        String subject = envelop.getSubject();
        MessagePage messagePage = inboxPage.openEnvelope(envelop);
        FolderPage inboxPage = messagePage.remove();

        FolderPage trashPage = inboxPage.goToFolder(Folder.TRASH);

        Optional<Envelope> envelope = trashPage.getEnvelopes().stream()
                .filter(it -> it.getSubject().equals(subject))
                .findAny();

        assertTrue(envelope.isPresent());
    }

    @Test
    public void markAsSpamMovesToSpamFolder() {
        Envelope envelop = inboxPage.getAnyEnvelope();
        String subject = envelop.getSubject();
        MessagePage messagePage = inboxPage.openEnvelope(envelop);
        FolderPage inboxPage = messagePage.markAsSpam();

        FolderPage spamPage = inboxPage.goToFolder(Folder.SPAM);

        Optional<Envelope> envelope = spamPage.getEnvelopes().stream()
                .filter(it -> it.getSubject().equals(subject))
                .findAny();

        assertTrue(envelope.isPresent());
    }

    @Test
    public void unmarkAsSpamMovesToInbox() {
        // Ensure there is at least one message in SPAM folder
        FolderPage inboxPage = this.inboxPage.getEnvelopes().get(0).openToRead().markAsSpam();

        FolderPage spamPage = inboxPage.goToFolder(Folder.SPAM);
        Envelope envelop = spamPage.getAnyEnvelope();
        String subject = envelop.getSubject();
        MessagePage messagePage = spamPage.openEnvelope(envelop);
        FolderPage newInboxPage = messagePage.unmarkAsSpam().goToFolder(Folder.INBOX);

        Optional<Envelope> envelope = newInboxPage.getEnvelopes().stream()
                .filter(it -> it.getSubject().equals(subject))
                .findAny();

        assertTrue(envelope.isPresent());
    }

    @Test
    public void removeFromSpamMovesToTrash() {
        FolderPage spamPage = inboxPage.goToFolder(Folder.SPAM);
        Envelope envelop = spamPage.getAnyEnvelope();
        String subject = envelop.getSubject();
        MessagePage messagePage = spamPage.openEnvelope(envelop);
        FolderPage newSpamPage = messagePage.remove();

        FolderPage trashPage = newSpamPage.goToFolder(Folder.TRASH);

        Optional<Envelope> envelope = trashPage.getEnvelopes().stream()
                .filter(it -> it.getSubject().equals(subject))
                .findAny();

        assertTrue(envelope.isPresent());
    }

    @Test
    public void removeFromTrashDeletesPermanently() {
        FolderPage trashPage = inboxPage.goToFolder(Folder.TRASH);
        Envelope envelop = trashPage.getAnyEnvelope();
        String subject = envelop.getSubject();
        MessagePage messagePage = trashPage.openEnvelope(envelop);
        FolderPage newTrashPage = messagePage.remove();

        assertTrue(newTrashPage.getEnvelopes().stream()
                .noneMatch(it -> it.getSubject().equals(subject)));
    }

}
