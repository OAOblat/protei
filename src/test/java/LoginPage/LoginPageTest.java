package LoginPage;

import Helper.BaseTest;
import Helper.TestData;
import io.qameta.allure.*;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(AllureJunit5.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Epic("Протей")
@Feature("Форма авторизации")
public class LoginPageTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeEach
    @Step("Открытие страницы авторизации")
    public void openLoginPage() {
        loginPage = new LoginPage(driver);
        String filePath = Paths.get("src/main/qa-test.html").toAbsolutePath().toUri().toString();
        driver.get(filePath);
    }

    @Test
    @Story("Авторизация")
    @DisplayName("Успешный вход с валидными данными")
    @Severity(SeverityLevel.BLOCKER)
    public void testValidFormSubmission() {
        loginPage.enterEmail(TestData.VALID_EMAIL);
        loginPage.enterPassword(TestData.VALID_PASSWORD);
        loginPage.submitForm();

        loginPage.isInputsPageDisplayed();
    }

    @Test
    @Story("Авторизация")
    @DisplayName("Отказ в доступе при вводе невалидных email и пароля")
    @Severity(SeverityLevel.CRITICAL)
    public void testInvalidFormSubmissionWithInvalidEmailAndPassword() {
        String invalidEmail = TestData.generateAlternativeValidEmail();
        String invalidPassword = TestData.generateInvalidPassword();

        loginPage.enterEmail(invalidEmail);
        loginPage.enterPassword(invalidPassword);
        loginPage.submitForm();

        loginPage.checkInputsPageNotDisplayed(invalidEmail, invalidPassword);
    }

    @Test
    @Story("Авторизация")
    @DisplayName("Отказ в доступе при вводе невалидного пароля")
    @Severity(SeverityLevel.CRITICAL)
    public void testInvalidFormSubmissionWithInvalidPassword() {
        String invalidPassword = TestData.generateInvalidPassword();
        String email = TestData.VALID_EMAIL;
        loginPage.enterEmail(email);
        loginPage.enterPassword(invalidPassword);
        loginPage.submitForm();

        loginPage.checkInputsPageNotDisplayed(email, invalidPassword);
    }

    @Test
    @Story("Авторизация")
    @DisplayName("Отказ в доступе при вводе невалидного email")
    @Severity(SeverityLevel.CRITICAL)
    public void testInvalidFormSubmissionWithInvalidEmail() {
        String invalidEmail = TestData.generateAlternativeValidEmail();
        String password = TestData.VALID_PASSWORD;
        loginPage.enterEmail(invalidEmail);
        loginPage.enterPassword(password);
        loginPage.submitForm();

        loginPage.checkInputsPageNotDisplayed(invalidEmail, password);
    }

    @Test
    @Story("Авторизация")
    @DisplayName("Отказ в доступе при авторизации без email")
    @Severity(SeverityLevel.CRITICAL)
    public void testInvalidFormSubmissionWithEmptyEmail() {
        String password = TestData.VALID_PASSWORD;
        loginPage.enterPassword(password);
        loginPage.submitForm();

        loginPage.checkInputsPageNotDisplayed("", password);
    }

    @Test
    @Story("Авторизация")
    @DisplayName("Отказ в доступе при авторизации без пароля")
    @Severity(SeverityLevel.CRITICAL)
    public void testInvalidFormSubmissionWithEmptyPassword() {
        String email = TestData.VALID_EMAIL;
        loginPage.enterEmail(email);
        loginPage.submitForm();

        loginPage.checkInputsPageNotDisplayed(email, "");
    }

    @ParameterizedTest
    @MethodSource("Helper.TestData#invalidEmails")
    @Story("Ошибочные состояния")
    @DisplayName("Отображение ошибки при вводе email в неверном формате")
    @Severity(SeverityLevel.MINOR)
    public void testEmailFormatErrorIsDisplayed(String invalidEmail) {
        loginPage.enterEmail(invalidEmail);
        loginPage.enterPassword(TestData.VALID_PASSWORD);
        loginPage.submitForm();

        assertTrue(loginPage.isEmailFormatErrorDisplayed(),
                "Ожидалось, что появится ошибка формата email, но она не отобразилась. Используемый email: " + invalidEmail);
    }

    @Test
    @Story("Ошибочные состояния")
    @DisplayName("Отображение ошибки неверного email/пароля c невалидным email")
    @Severity(SeverityLevel.MINOR)
    public void testInvalidEmailPasswordErrorIsDisplayedWhenInvalidEmail() {
        String invalidEmail = TestData.generateAlternativeValidEmail();
        loginPage.enterEmail(invalidEmail);
        loginPage.enterPassword(TestData.VALID_PASSWORD);
        loginPage.submitForm();

        assertTrue(loginPage.isInvalidEmailPasswordDisplayed(),
                "Ожидалось, что появится ошибка неверного email/пароля, но она не отобразилась. Используемый email: " + invalidEmail);
    }

    @Test
    @Story("Ошибочные состояния")
    @DisplayName("Отображение ошибки неверного email/пароля c невалидным паролем")
    @Severity(SeverityLevel.MINOR)
    public void testInvalidEmailPasswordErrorIsDisplayedWhenInvalidPassword() {
        String invalidPassword = TestData.generateInvalidPassword();
        loginPage.enterEmail(TestData.VALID_EMAIL);
        loginPage.enterPassword(invalidPassword);
        loginPage.submitForm();

        assertTrue(loginPage.isInvalidEmailPasswordDisplayed(),
                "Ожидалось, что появится ошибка неверного email/пароля, но она не отобразилась. Используемый пароль: " + invalidPassword);
    }

    @Test
    @Story("Ошибочные состояния")
    @DisplayName("Отображение ошибки неверного email/пароля c невалидным email и паролем")
    @Severity(SeverityLevel.MINOR)
    public void testInvalidEmailPasswordErrorIsDisplayedWhenInvalidEmailAndPassword() {
        String invalidEmail = TestData.generateAlternativeValidEmail();
        String invalidPassword = TestData.generateInvalidPassword();
        loginPage.enterEmail(invalidEmail);
        loginPage.enterPassword(invalidPassword);
        loginPage.submitForm();

        assertTrue(loginPage.isInvalidEmailPasswordDisplayed(),
                "Ожидалось, что появится ошибка неверного email/пароля, но она не отобразилась. Используемыe невалидные данные: " + invalidEmail + " / " + invalidPassword);
    }



}