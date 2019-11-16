package ru.ifmo.se.testing.zavoduben.lab3;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.ifmo.se.testing.zavoduben.lab3.fixtures.User;
import ru.ifmo.se.testing.zavoduben.lab3.fixtures.UserFixtures;
import ru.ifmo.se.testing.zavoduben.lab3.pages.*;
import ru.ifmo.se.testing.zavoduben.lab3.util.WebDriverSupplier;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

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
        Envelope recentlyRemovedMessage = trashPage.getEnvelopes().get(0);
        assertEquals(subject, recentlyRemovedMessage.getSubject());
    }

    @Test
    public void markAsSpamMovesToSpamFolder() {
        Envelope envelop = inboxPage.getAnyEnvelope();
        String subject = envelop.getSubject();
        MessagePage messagePage = inboxPage.openEnvelope(envelop);
        FolderPage inboxPage = messagePage.markAsSpam();

        FolderPage spamPage = inboxPage.goToFolder(Folder.SPAM);
        Envelope recentlyMarkedMessage = spamPage.getEnvelopes().get(0);
        assertEquals(subject, recentlyMarkedMessage.getSubject());
    }

    @Test
    public void unmarkAsSpamMovesToInbox() {
        FolderPage spamPage = inboxPage.goToFolder(Folder.SPAM);
        Envelope envelop = spamPage.getAnyEnvelope();
        String subject = envelop.getSubject();
        MessagePage messagePage = spamPage.openEnvelope(envelop);
        FolderPage newInboxPage = messagePage.unmarkAsSpam();

        FolderPage trashPage = newInboxPage.goToFolder(Folder.TRASH);
        Envelope recentlyRemovedMessage = trashPage.getEnvelopes().get(0);
        assertEquals(subject, recentlyRemovedMessage.getSubject());
    }

    @Test
    public void removeFromSpamMovesToTrash() {
        FolderPage spamPage = inboxPage.goToFolder(Folder.SPAM);
        Envelope envelop = spamPage.getAnyEnvelope();
        String subject = envelop.getSubject();
        MessagePage messagePage = spamPage.openEnvelope(envelop);
        FolderPage newSpamPage = messagePage.remove();

        FolderPage trashPage = newSpamPage.goToFolder(Folder.TRASH);
        Envelope recentlyRemovedMessage = trashPage.getEnvelopes().get(0);
        assertEquals(subject, recentlyRemovedMessage.getSubject());
    }

    @Test
    public void removeFromTrashDeletesPermanently() {
        FolderPage trashPage = inboxPage.goToFolder(Folder.TRASH);
        Envelope envelop = trashPage.getAnyEnvelope();
        String subject = envelop.getSubject();
        MessagePage messagePage = trashPage.openEnvelope(envelop);
        FolderPage newTrashPage = messagePage.remove();
        // TODO EGOR HELP!
        assertTrue(true);
    }

    @Ignore
    @Test
    public void moveToAnotherFolder() {
        fail();
    }

    @Ignore
    @Test
    public void moveToArchive() {
        fail();
    }

    @Ignore
    @Test
    public void markUnread() {
        fail();
    }

    @Ignore
    @Test
    public void markRead() {
        fail();
    }

    @Ignore
    @Test
    public void markImportant() {
        fail();
    }

    @Ignore
    @Test
    public void unmarkImportant() {
        fail();
    }

    @Ignore
    @Test
    public void replyFillsSubjectAndRecipient() {
        fail();
    }

    @Ignore
    @Test
    public void forwardFillsSubjectButNoRecipient() {
        fail();
    }
}
