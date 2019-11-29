package ru.ifmo.se.testing.zavoduben.lab3.fixtures;

import org.tomlj.Toml;
import org.tomlj.TomlParseResult;
import org.tomlj.TomlTable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public final class UserFixtures {

    private static final String USERS_FIXTURES_FILE = "/fixtures/users.toml";
    private static final TomlParseResult USERS;

    static {
        InputStream inputStream = UserFixtures.class.getResourceAsStream(USERS_FIXTURES_FILE);
        try {
            USERS = Toml.parse(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException("Unreachable: File " + USERS_FIXTURES_FILE + "must be available in resources");
        }
    }

    public static User getAnyUser() {
        TomlTable userTable = USERS.getTable("users").getTable("max");
        Domain domain = Domain.getByName(userTable.getString("domain"));
        Mailbox mailbox = new Mailbox(userTable.getString("login"), domain);
        return new User(
                userTable.getString("name"),
                userTable.getString("surname"),
                mailbox,
                userTable.getString("password")
        );
    }

    public static Mailbox getNonExistingMailbox() {
        return new Mailbox(generateRandomUsername(), getRandomSupportedDomain());
    }

    private static String generateRandomUsername() {
        String alphabet = "abcdefghijklmnopqrstuvwxyz0123456789-._";
        Random random = new Random();
        int usernameLength = random.nextInt(10) + 7;
        StringBuilder builder = new StringBuilder(usernameLength);
        for (int i = 0; i < usernameLength; i++) {
            int charIndex = random.nextInt(alphabet.length());
            builder.append(alphabet.charAt(charIndex));
        }
        return builder.toString();
    }

    private static Domain getRandomSupportedDomain() {
        Domain[] allDomains = Domain.values();
        Random random = new Random();
        int domainIndex = random.nextInt(allDomains.length);
        return allDomains[domainIndex];
    }
}
