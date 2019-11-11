package ru.ifmo.se.testing.zavoduben.lab3.fixtures;

public class User {
    private final String firstName;
    private final String lastName;
    private final String login;
    private final String domain;
    private final String password;

    public User(String firstName, String lastName, String login, String domain, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.domain = domain;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return login;
    }

    public String getDomain() {
        return domain;
    }

    public String getPassword() {
        return password;
    }

    public String getEmailAddress() {
        return getUsername() + "@" + getDomain();
    }
}
