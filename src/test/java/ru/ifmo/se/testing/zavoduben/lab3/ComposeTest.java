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

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ComposeTest extends BaseTestConfiguration {

    private final WebDriver driver;
    private FolderPage inboxPage;
    private ComposePage composePage;

    public ComposeTest(WebDriverSupplier driverSupplier) {
        this.driver = driverSupplier.get();
    }

    @Before
    public void logInAndOpenInbox() {
        User user = UserFixtures.getAnyUser();
        LoginPage loginPage = LoginPage.open(driver);
        this.inboxPage = loginPage.loginAs(user);
        this.composePage = inboxPage.compose();
    }

    @After
    public void quitBrowser() {
        driver.quit();
    }

    @Test
    public void cannotSendEmptyWithoutRecipient() {
        String errorMessage = composePage
                .sendExpectingError()
                .getErrorMessage();

        assertEquals("Не указан адрес получателя", errorMessage);
    }

    @Ignore
    @Test
    public void closingDoesNotSaveDraft() {
        fail();
    }

    @Ignore
    @Test
    public void discardingDoesNotSaveDraft() {
        fail();
    }

    @Test
    public void savingComposedMessagePutsItToDraftBox() {
        LocalDateTime time = LocalDateTime.now();
        String messageSubject = "A saved draft message " + time.toString();

        FolderPage inboxPage = composePage
                .typeSubject(messageSubject)
                .saveDraft().closeModal();

        FolderPage draftsPage = inboxPage.goToFolder(Folder.DRAFTS);
        List<Envelope> drafts = draftsPage.getEnvelopes();

        assertTrue(drafts.stream().anyMatch(it ->
                messageSubject.equals(it.getSubject())
        ));
    }

    @Ignore
    @Test
    public void sendingWithoutSubjectRaisesQuestion() {
        fail();
    }

    @Ignore
    @Test
    public void sendPositiveOneRecipient() {
        fail();
    }

    @Ignore
    @Test
    public void sendPositiveTwoRecipients() {
        fail();
    }

    @Ignore
    @Test
    public void sendPositiveRecipientAndCarbonCopy() {
        fail();
    }

    @Ignore
    @Test
    public void sendPositiveRecipientAndBlindCarbonCopy() {
        fail();
    }
}
