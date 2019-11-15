package ru.ifmo.se.testing.zavoduben.lab3;

import org.junit.runners.Parameterized;
import ru.ifmo.se.testing.zavoduben.lab3.util.WebDriverSupplier;

import java.util.Arrays;

public class BaseTestConfiguration {
    @Parameterized.Parameters(name = "{0}")
    public static Iterable<WebDriverSupplier> makeWebDrivers() {
        return Arrays.asList(
                WebDriverSupplier.CHROME,
                WebDriverSupplier.FIREFOX);
    }
}
