package ru.ifmo.se.testing.zavoduben.lab3.fixtures;

public class User {
    private final String firstName;
    private final String lastName;
    private final String password;
    private final Mailbox mailbox;

    public User(String firstName, String lastName, Mailbox mailbox, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mailbox = mailbox;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Mailbox getMailbox() {
        return mailbox;
    }

    public String getUsername() {
        return mailbox.getName();
    }

    public Domain getDomain() {
        return mailbox.getDomain();
    }

    public String getPassword() {
        return password;
    }

    public String getEmailAddress() {
        return mailbox.getAddress();
    }
}
