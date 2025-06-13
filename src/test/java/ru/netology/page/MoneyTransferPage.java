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
        transferPrepare(amount, cardNumber);
        cancelButton.click();
        return new DashboardPage();
    }

    //private
    public void transfer(String amount, String cardNumber) {
        transferPrepare(amount, cardNumber);
        transferButton.click();
    }

    private void transferPrepare(String amount, String cardNumber) {
        amountField.setValue(amount);
        fromField.setValue(cardNumber);
    }

    public DashboardPage validTransfer(String amount, String cardNumber) {
        transfer(amount, cardNumber);
        return new DashboardPage();
    }

    public void checkErrorMessage() {
        //transfer(amount, cardNumber);
        errorMessage.shouldBe(visible).shouldHave(Condition.text("Ошибка! Произошла ошибка"));
    }
}
