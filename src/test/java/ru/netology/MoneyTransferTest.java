package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {
    private DashboardPage dashboardPage;

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo.getLogin(), authInfo.getPassword());
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Test
    @DisplayName("1. Transfer money from first to second card")
    void shouldFromFirstToSecondCard() {
        var firstCard = DataHelper.getFirstCard();
        var secondCard = DataHelper.getSecondCard();
        var amount = "1000";
        var initialFirstCardBalance = dashboardPage.getCardBalance(firstCard);
        var initialSecondCardBalance = dashboardPage.getCardBalance(secondCard);

        var transferPage = dashboardPage.selectCard(secondCard);
        dashboardPage = transferPage.validTransfer(amount, firstCard.getCardNumber());

        var expectedFirstCardBalance = initialFirstCardBalance - Integer.parseInt(amount);
        var expectedSecondCardBalance = initialSecondCardBalance + Integer.parseInt(amount);
        var actualFirstCardBalance = dashboardPage.getCardBalance(firstCard);
        var actualSecondCardBalance = dashboardPage.getCardBalance(secondCard);

        assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    @DisplayName("2. Transfer money from second to first card")
    void shouldFromSecondToFirstCard() {
        var firstCard = DataHelper.getFirstCard();
        var secondCard = DataHelper.getSecondCard();
        var amount = "500";

        var initialFirstCardBalance = dashboardPage.getCardBalance(firstCard);
        var initialSecondCardBalance = dashboardPage.getCardBalance(secondCard);

        var transferPage = dashboardPage.selectCard(firstCard);
        dashboardPage = transferPage.validTransfer(amount, secondCard.getCardNumber());

        var expectedFirstCardBalance = initialFirstCardBalance + Integer.parseInt(amount);
        var expectedSecondCardBalance = initialSecondCardBalance - Integer.parseInt(amount);
        var actualFirstCardBalance = dashboardPage.getCardBalance(firstCard);
        var actualSecondCardBalance = dashboardPage.getCardBalance(secondCard);

        assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    @DisplayName("3. Transfer with unfilled card field")
    void shouldWithUnfilledCard() {
        var firstCard = DataHelper.getFirstCard();
        var transferPage = dashboardPage.selectCard(firstCard);
        transferPage.transfer("1000", "");
        transferPage.checkErrorMessage();
    }

    @Test
    @DisplayName("4. Check cancel button")
    void checkCancelButton() {
        var firstCard = DataHelper.getFirstCard();
        var transferPage = dashboardPage.selectCard(firstCard);
        transferPage.cancelTransfer("1000", firstCard.getCardNumber());
    }

    @Test
    @DisplayName("5. Transfer with incorrect card number")
    void shouldWithIncorrectCardNumber() {
        var firstCard = DataHelper.getNonExistCard();
        var secondCard = DataHelper.getSecondCard();
        var transferPage = dashboardPage.selectCard(secondCard);
        transferPage.transfer("1000", firstCard.getCardNumber());
        transferPage.checkErrorMessage();
    }

    @Test
    @DisplayName("6. Transfer with unfilled amount")
    void shouldWithUnfilledAmount() {
        var firstCard = DataHelper.getFirstCard();
        var secondCard = DataHelper.getSecondCard();
        var transferPage = dashboardPage.selectCard(secondCard);
        transferPage.transfer("", firstCard.getCardNumber());
    }

    @Test
    @DisplayName("7. Transfer money above initial balance")
    void shouldTransferAboveInitialBalance() {
        var firstCard = DataHelper.getFirstCard();
        var secondCard = DataHelper.getSecondCard();
        var amount = "55555";
        var expectedSecondCardBalance = dashboardPage.getCardBalance(secondCard);

        var transferPage = dashboardPage.selectCard(firstCard);
        dashboardPage = transferPage.validTransfer(amount, secondCard.getCardNumber());
        var actualSecondCardBalance = dashboardPage.getCardBalance(secondCard);

        assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }
}
