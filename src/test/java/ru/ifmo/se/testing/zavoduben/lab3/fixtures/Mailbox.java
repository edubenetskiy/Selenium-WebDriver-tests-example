package ru.ifmo.se.testing.zavoduben.lab3.fixtures;

public class Mailbox {
    private final String name;
    private final Domain domain;

    public Mailbox(String name, Domain domain) {
        this.name = name;
        this.domain = domain;
    }

    public String getName() {
        return name;
    }

    public Domain getDomain() {
        return domain;
    }

    String getAddress() {
        return name + "@" + domain.getDomainName();
    }
}
