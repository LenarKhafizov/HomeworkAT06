package ru.netology;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataHelper.getAuthInfo;

import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

public class ValidationTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should with invalid login or password")
    void shouldWithInvalidLoginOrPassword() {
        var loginPage = new LoginPage();
        loginPage.invalidLogin(DataHelper.getRandomLogin(), DataHelper.getRandomPassword());
    }

    @Test
    @DisplayName("Should with unfilled login")
    void shouldWithUnfilledLogin() {
        var loginPage = new LoginPage();
        loginPage.unfilledLogin(getAuthInfo().getPassword());
    }

    @Test
    @DisplayName("Should with unfilled password")
    void shouldWithUnfilledPassword() {
        var loginPage = new LoginPage();
        loginPage.unfilledPassword(getAuthInfo().getLogin());
    }

    @Test
    @DisplayName("Should with incorrect verification code")
    void shouldWithIncorrectVerificationCode() {
        var authInfo = DataHelper.getAuthInfo();
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(authInfo.getLogin(), authInfo.getPassword());
        verificationPage.incorrectVerify(DataHelper.getVerificationCodeRandom());
    }

    @Test
    @DisplayName("Should with unfilled verification code")
    void shouldWithUnfilledVerificationCode() {
        var authInfo = DataHelper.getAuthInfo();
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(authInfo.getLogin(), authInfo.getPassword());
        verificationPage.unfilledVerificationCode();
    }
}
