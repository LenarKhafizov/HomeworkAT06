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

    public void verify(String code) {
        verifySetValue(code, continueButton);
    }

    private void verifySetValue(String code, SelenideElement button) {
        codeField.setValue(code);
        button.click();
    }

    public DashboardPage validVerify(DataHelper.VerificationCode verificationCode) {
        verifySetValue(verificationCode.getCode(), verifyButton);
        return new DashboardPage();
    }

    public VerificationPage checkErrorMessage(SelenideElement errorField, String errorText) {
        errorField.shouldBe(Condition.visible)
                .shouldHave(Condition.text(errorText));
        return this;
    }


    public SelenideElement getErrorNotification() {
        return errorNotification;
    }

    public SelenideElement getCodeFieldError() {
        return codeFieldError;
    }
}
