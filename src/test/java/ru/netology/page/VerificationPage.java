package ru.netology.page;
import com.codeborne.selenide.Condition;
import ru.netology.data.DataHelper;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private SelenideElement continueButton = $$("button").findBy(Condition.text("Продолжить"));
    private SelenideElement errorNotification  = $("[data-test-id='error-notification'] .notification__content");
    private SelenideElement codeFieldError = $("[data-test-id='code'].input_invalid .input__sub");

    public VerificationPage() {
        codeField.shouldBe(visible);
    }

    private void Verify(String code) {
        codeField.setValue(code);
        continueButton.click();
    }

    public DashboardPage validVerify(DataHelper.VerificationCode verificationCode) {
        codeField.setValue(verificationCode.getCode());
        verifyButton.click();
        return new DashboardPage();
    }

    public VerificationPage incorrectVerify(DataHelper.VerificationCode code) {
        Verify(code.getCode());
        errorNotification.shouldBe(Condition.visible)
                .shouldHave(Condition.text("Ошибка! Неверно указан код! Попробуйте ещё раз"));
        return this;
    }

    public VerificationPage unfilledVerificationCode() {
        Verify("");
        codeFieldError.shouldBe(Condition.visible)
                .shouldHave(Condition.text("Поле обязательно для заполнения"));
        return this;
    }
}
