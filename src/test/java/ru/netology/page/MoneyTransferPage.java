package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MoneyTransferPage {
    private SelenideElement amountField = $("[data-test-id='amount'] input");
    private SelenideElement fromField = $("[data-test-id='from'] input");
    private SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private SelenideElement errorMessage = $("[data-test-id='error-notification'] .notification__content");
    private SelenideElement cancelButton = $$("button").findBy(Condition.text("Отмена"));

    public MoneyTransferPage() {
        amountField.shouldBe(visible);
    }

    public  DashboardPage cancelTransfer (String amount, String cardNumber) {
        amountField.setValue(amount);
        fromField.setValue(cardNumber);
        cancelButton.click();
        return new DashboardPage();
    }

    private void Transfer(String amount, String cardNumber) {
        amountField.setValue(amount);
        fromField.setValue(cardNumber);
        transferButton.click();
    }

    public DashboardPage validTransfer(String amount, String cardNumber) {
        Transfer(amount, cardNumber);
        return new DashboardPage();
    }

    public MoneyTransferPage transferIncorrectCard(String amount, String cardNumber) {
        Transfer(amount, cardNumber);
        errorMessage.shouldBe(visible).shouldHave(Condition.text("Ошибка! Произошла ошибка"));
        return this;
    }

    public MoneyTransferPage transferUnfilledCardNumber(String amount) {
        Transfer(amount, "");
        errorMessage.shouldBe(visible).shouldHave(Condition.text("Ошибка! Произошла ошибка"));
        return this;
    }

    public MoneyTransferPage transferUnfilledAmount(String cardNumber) {
        Transfer("", cardNumber);
        //errorMessage.shouldBe(visible).shouldHave(Condition.text("Ошибка! Произошла ошибка"));
        return this;
    }
}
