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
    @DisplayName("1. Should with invalid login or password")
    void shouldWithInvalidLoginOrPassword() {
        var loginPage = new LoginPage();
        loginPage.login(DataHelper.getRandomLogin(), DataHelper.getRandomPassword());
        loginPage.checkErrorMessage(loginPage.getErrorMessage(), "Ошибка! Неверно указан логин или пароль");
    }

    @Test
    @DisplayName("2. Should with unfilled login")
    void shouldWithUnfilledLogin() {
        var loginPage = new LoginPage();
        loginPage.login("", getAuthInfo().getPassword());
        loginPage.checkErrorMessage(loginPage.getUnfilledLogin(), "Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("3. Should with unfilled password")
    void shouldWithUnfilledPassword() {
        var loginPage = new LoginPage();
        loginPage.login(getAuthInfo().getLogin(), "");
        loginPage.checkErrorMessage(loginPage.getUnfilledPassword(), "Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("4. Should with incorrect verification code")
    void shouldWithIncorrectVerificationCode() {
        var authInfo = DataHelper.getAuthInfo();
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(authInfo.getLogin(), authInfo.getPassword());

        verificationPage.verify(DataHelper.getVerificationCodeRandom().toString());
        verificationPage.checkErrorMessage(verificationPage
                .getErrorNotification(), "Ошибка! Неверно указан код! Попробуйте ещё раз");
    }

    @Test
    @DisplayName("5. Should with unfilled verification code")
    void shouldWithUnfilledVerificationCode() {
        var authInfo = DataHelper.getAuthInfo();
        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(authInfo.getLogin(), authInfo.getPassword());
        verificationPage.verify("");
        verificationPage.checkErrorMessage(verificationPage
                .getCodeFieldError() ,"Поле обязательно для заполнения");
    }
}
