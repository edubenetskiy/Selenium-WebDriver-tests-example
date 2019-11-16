package ru.ifmo.se.testing.zavoduben.lab3;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.ifmo.se.testing.zavoduben.lab3.fixtures.SubjectFixtures;
import ru.ifmo.se.testing.zavoduben.lab3.fixtures.User;
import ru.ifmo.se.testing.zavoduben.lab3.fixtures.UserFixtures;
import ru.ifmo.se.testing.zavoduben.lab3.pages.*;
import ru.ifmo.se.testing.zavoduben.lab3.util.WebDriverSupplier;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class ComposeTest extends BaseTestConfiguration {

    private final WebDriver driver;
    private ComposePage composePage;

    public ComposeTest(WebDriverSupplier driverSupplier) {
        this.driver = driverSupplier.get();
    }

    @Before
    public void logInAndOpenInbox() {
        User user = UserFixtures.getAnyUser();
        LoginPage loginPage = LoginPage.open(driver);
        this.composePage = loginPage.loginAs(user).compose();
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


    @Test
    public void savingComposedMessagePutsItToDraftBox() {
        String messageSubject = SubjectFixtures.getFakeSubject();
        String recipientAddress = UserFixtures.getAnyUser().getEmailAddress();
        String messageText = UUID.randomUUID().toString();

        FolderPage inboxPage = composePage
                .typeRecipient(recipientAddress)
                .typeSubject(messageSubject)
                .typeBody(messageText)
                .saveDraft().closeModal();

        FolderPage draftsPage = inboxPage.goToFolder(Folder.DRAFTS);
        List<Envelope> drafts = draftsPage.getEnvelopes();

        Optional<Envelope> envelope = drafts.stream()
                .filter(it -> messageSubject.equals(it.getSubject()) &&
                              it.getRecipient().contains(recipientAddress))
                .findAny();

        // TODO: Check creation time
        assertTrue(envelope.isPresent());

        assertEquals(messageText, envelope.get().openToCompose().getBody());
    }

    @Test
    public void sendingWithoutBodyRaisesQuestion() {
        String messageSubject = SubjectFixtures.getFakeSubject();
        String recipientAddress = UserFixtures.getAnyUser().getEmailAddress();

        LayerQuestionPage questionPage = composePage
                .typeRecipient(recipientAddress)
                .typeSubject(messageSubject)
                .sendExpectingQuestion();

        assertEquals("Вы действительно хотите отправить пустое письмо?",
                questionPage.getQuestionText());
    }

    @Test
    public void sendPositiveOneRecipient() {
        String messageSubject = SubjectFixtures.getFakeSubject();
        String recipientAddress = UserFixtures.getAnyUser().getEmailAddress();
        String messageText = UUID.randomUUID().toString();

        LayerSentPage layerSentPage = composePage
                .typeRecipient(recipientAddress)
                .typeSubject(messageSubject)
                .typeBody(messageText)
                .send();

        assertEquals("Письмо отправлено", layerSentPage.getHeaderText());
        FolderPage inboxPage = layerSentPage.closeModal();

        FolderPage sentPage = inboxPage.goToFolder(Folder.SENT);
        List<Envelope> sentMessages = sentPage.getEnvelopes();

        Optional<Envelope> envelope = sentMessages.stream()
                .filter(it -> messageSubject.equals(it.getSubject()) &&
                              it.getRecipient().contains(recipientAddress))
                .findAny();

        assertTrue(envelope.isPresent());
        assertEquals(messageText, envelope.get().openToRead().getBody());
    }

}
