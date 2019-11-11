package ru.ifmo.se.testing.zavoduben.lab3.fixtures;

import org.tomlj.Toml;
import org.tomlj.TomlParseResult;
import org.tomlj.TomlTable;

import java.io.IOException;
import java.io.InputStream;

public class UserFixtureProvider {

    private static final String USERS_FIXTURES_FILE = "/fixtures/users.toml";
    private final TomlParseResult users;

    private static UserFixtureProvider instance;

    public static UserFixtureProvider getInstance() {
        if (instance == null) {
            synchronized (UserFixtureProvider.class) {
                if (instance == null) {
                    instance = new UserFixtureProvider();
                }
            }
        }
        return instance;
    }

    private UserFixtureProvider() {
        InputStream inputStream = UserFixtureProvider.class.getResourceAsStream(USERS_FIXTURES_FILE);
        try {
            this.users = Toml.parse(inputStream);
        } catch (IOException e) {
            throw new IllegalStateException("Unreachable: File " + USERS_FIXTURES_FILE + "must be available in resources");
        }
    }

    public User getAnyUser() {
        TomlTable userTable = users.getTable("users").getTable("alice");
        return new User(
                userTable.getString("name"),
                userTable.getString("surname"),
                userTable.getString("login"),
                userTable.getString("domain"),
                userTable.getString("password")
        );
    }
}
