package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;
import java.util.Locale;

public class DataHelper {
    private static final Faker FAKER = new Faker(new Locale("en"));

    private DataHelper() {
    }

    public static String getRandomLogin() {
        return FAKER.name().firstName();
    }

    public static String getRandomPassword() {
        return FAKER.internet().password();
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    public static VerificationCode getVerificationCodeRandom() {
        return new VerificationCode(FAKER.numerify("#####"));
    }

    @Value
    public static class CardInfo {
        String cardNumber;
        String testId;
    }

    public static CardInfo getFirstCard() {
        return new CardInfo("5559 0000 0000 0001", "92df3f1c-a033-48e6-8390-206f6b1f56c0");
    }

    public static CardInfo getSecondCard() {
        return new CardInfo("5559 0000 0000 0002", "0f3f5c2a-249e-4c3d-8287-09f7a039391d");
    }
}
