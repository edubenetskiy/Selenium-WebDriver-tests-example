package ru.ifmo.se.testing.zavoduben.lab3;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import ru.ifmo.se.testing.zavoduben.lab3.util.WebDriverFactory;

public class BaseTestConfiguration {

    private WebDriver driver;

    @BeforeClass
    @Parameters({ "browser" })
    public void setUpDriver(ITestContext context, String browser) {
        driver = WebDriverFactory.makeDriverForBrowserName(browser);
        context.setAttribute("driver", driver);
    }

    @AfterClass
    public void teardown(ITestContext context) {
        driver.close();
    }
}
