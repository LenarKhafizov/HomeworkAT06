package ru.netology.page;
import ru.netology.data.DataHelper;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class LoginPage {
    private SelenideElement loginField = $("[data-test-id='login'] input");
    private SelenideElement passwordField = $("[data-test-id='password'] input");
    private SelenideElement buttonField = $$("button").findBy(Condition.text("Продолжить"));
    private SelenideElement errorMesssage = $("[data-test-id='error-notification'] .notification__content");
    private SelenideElement unfilledLogin = $("[data-test-id='login'].input_invalid .input__sub");
    private SelenideElement unfilledPassword = $("[data-test-id='password'].input_invalid .input__sub");

    private void login (String login, String password){
        loginField.setValue(login);
        passwordField.setValue(password);
        buttonField.click();
    }

    public VerificationPage validLogin(String login, String password) {
        login(login, password);
        return new VerificationPage();
    }

    public LoginPage invalidLogin(String login, String password) {
        login(login, password);
        errorMesssage.shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль"));
        return this;
    }

    public LoginPage unfilledLogin(String password) {
        login("", password);
        unfilledLogin.shouldBe(Condition.visible)
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
        return this;
    }

    public LoginPage unfilledPassword(String login) {
        login(login, "");
        unfilledPassword.shouldBe(Condition.visible)
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
        return this;
    }
}
