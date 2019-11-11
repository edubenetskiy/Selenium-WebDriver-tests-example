package ru.ifmo.se.testing.zavoduben.lab3;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.ifmo.se.testing.zavoduben.lab3.fixtures.User;
import ru.ifmo.se.testing.zavoduben.lab3.fixtures.UserFixtureProvider;
import ru.ifmo.se.testing.zavoduben.lab3.pages.InboxPage;
import ru.ifmo.se.testing.zavoduben.lab3.pages.LoginPage;

import static org.testng.Assert.assertEquals;

public class InboxTest extends BaseTestConfiguration {

    private WebDriver driver;

    @BeforeClass
    public void retrieveDriver(ITestContext context) {
        driver = (WebDriver) context.getAttribute("driver");
    }

    // TODO
}
