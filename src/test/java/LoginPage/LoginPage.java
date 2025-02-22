package LoginPage;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Локаторы
    private final By passwordField = By.id("loginPassword");
    private final By emailField = By.id("loginEmail");
    private final By submitButton = By.id("authButton");
    private final By inputsPage = By.id("inputsPage");
    private final By emailFormatError = By.id("emailFormatError");

    private final By invalidEmailPassword = By.id("invalidEmailPassword");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @Step("Ввод email: {email}")
    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).sendKeys(email);
    }

    @Step("Ввод пароля: {password}")
    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).sendKeys(password);
    }

    @Step("Нажатие на кнопку 'Войти'")
    public void submitForm() {
        driver.findElement(submitButton).click();
    }

    @Step("Проверка: Страница заполнения формы НЕ отображается")
    public void checkInputsPageNotDisplayed(String email, String password) {
        assertFalse(driver.findElement(inputsPage).isDisplayed(),
                "Страница inputsPage загрузилась с невалидными данными: " + email + " / " + password);
    }

    @Step("Проверка: Страница заполнения формы отображается")
    public boolean isInputsPageDisplayed() {
        return driver.findElement(inputsPage).isDisplayed();
    }

    @Step("Проверка: Ошибка формата email отображается")
    public boolean isEmailFormatErrorDisplayed() {
        try {
            return driver.findElement(emailFormatError).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Step("Проверка: Ошибка 'Неверный email или пароль' отображается")
    public boolean isInvalidEmailPasswordDisplayed() {
        try {
            return driver.findElement(invalidEmailPassword).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
