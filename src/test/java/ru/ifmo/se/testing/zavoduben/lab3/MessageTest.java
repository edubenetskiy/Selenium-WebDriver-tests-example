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
import ru.ifmo.se.testing.zavoduben.lab3.pages.InboxPage;
import ru.ifmo.se.testing.zavoduben.lab3.pages.LoginPage;
import ru.ifmo.se.testing.zavoduben.lab3.util.WebDriverSupplier;

import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class MessageTest extends BaseTestConfiguration {

    private final WebDriver driver;
    private InboxPage inboxPage;

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

    @Ignore
    @Test
    public void removeFromInboxMovesToTrash() {
        /*
        MessagePage messagePage = inboxPage.openAnyMessage();
        String subject = messagePage.getSubject();
        InboxPage inboxPage = messagePage.remove();

        TrashPage trashPage = inboxPage.goToFolder(Folder.TRASH);
        MessageSummary recentlyRemovedMessage = trashPage.getMessageSummaries().first();
        assertEquals(subject, recentlyRemovedMessage.getSummary)
         */
        fail();
    }

    @Ignore
    @Test
    public void removeFromTrashDeletesPermanently() {
        fail();
    }

    @Ignore
    @Test
    public void moveToAnotherFolder() {
        fail();
    }

    @Ignore
    @Test
    public void markAsSpamMovesToSpamFolder() {
        fail();
    }

    @Ignore
    @Test
    public void unmarkAsSpamMovesToInbox() {
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
