package ru.ifmo.se.testing.zavoduben.lab3.fixtures;

import java.time.Instant;

public class SubjectFixtures {
    public static String getFakeSubject() {
        return "Письмо от " + Instant.now().toString();
    }
}
