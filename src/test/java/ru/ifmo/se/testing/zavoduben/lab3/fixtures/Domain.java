package ru.ifmo.se.testing.zavoduben.lab3.fixtures;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum Domain {
    MAIL_RU("mail.ru"),
    INBOX_RU("inbox.ru"),
    LIST_RU("list.ru"),
    BK_RU("bk.ru"),
    ;

    private final String name;

    Domain(String name) {
        this.name = name;
    }

    public static Domain getByName(String domainName) {
        return Arrays.stream(values())
                .parallel()
                .filter(it -> it.getDomainName().equals(domainName))
                .findAny()
                .orElseThrow(() -> {
                    String errorMessage =
                            "Unknown domain name: ‘" + domainName + "’. " +
                            "Known domains: " +
                            Arrays.stream(values())
                                    .map(Domain::getDomainName)
                                    .collect(Collectors.joining(", ", "[", "]"));
                    return new IllegalArgumentException(errorMessage);
                });
    }

    public String getDomainName() {
        return name;
    }
}
