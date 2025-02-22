package InputsPage;

import Helper.FormData;
import Helper.TestData;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InputsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Локаторы
    private final By passwordField = By.id("loginPassword");
    private final By emailField = By.id("loginEmail");
    private final By submitButton = By.id("authButton");
    private final By dataEmail = By.id("dataEmail");
    private final By dataName = By.id("dataName");
    private final By dataGender = By.id("dataGender");
    private final By maleOption = By.xpath("//select[@id='dataGender']/option[text()='Мужской']");
    private final By femaleOption = By.xpath("//select[@id='dataGender']/option[text()='Женский']");
    private final By dataCheck11 = By.id("dataCheck11");
    private final By dataCheck12 = By.id("dataCheck12");
    private final By dataSelect21 = By.id("dataSelect21");
    private final By dataSelect22 = By.id("dataSelect22");
    private final By dataSelect23 = By.id("dataSelect23");
    private final By dataSend = By.id("dataSend");
    private final By modalDialog = By.className("uk-modal-content");
    private final By modal = By.className("uk-open");
    private final By modalDialogButtonOk = By.cssSelector("div.uk-modal-footer > button");
    private final By dataTable = By.id("dataTable");
    private final By blankNameError = By.id("blankNameError");
    private final By emailFormatError = By.id("emailFormatError");
    private final By dataAlertsHolder = By.id("dataAlertsHolder");


    public InputsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @Step("Авторизация")
    public void signIn() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).sendKeys(TestData.VALID_EMAIL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).sendKeys(TestData.VALID_PASSWORD);
        driver.findElement(submitButton).click();
    }

    @Step("Ввод email: {email}")
    public void enterEmail(String email) {
        WebElement emailField = driver.findElement(dataEmail);
        emailField.sendKeys(email);
    }

    @Step("Ввод имени: {name}")
    public void enterName(String name) {
        driver.findElement(dataName).sendKeys(name);
    }

    @Step("Выбор пола: {gender}")
    public void chooseGender(String gender) {
        driver.findElement(dataGender).click();

        if ("Мужской".equals(gender)) {
            driver.findElement(maleOption).click();
        } else if ("Женский".equals(gender)) {
            driver.findElement(femaleOption).click();
        }
    }

    @Step("Выбор варианта из первой выборки")
    public void chooseCheck(String option) {
        if ("1.1".equals(option)) {
            driver.findElement(dataCheck11).click();
        } else if ("1.2".equals(option)) {
            driver.findElement(dataCheck12).click();
        }
    }

    @Step("Выбор варианта из второй выборки")
    public void chooseSelect(String option) {
        if ("2.1".equals(option)) {
            driver.findElement(dataSelect21).click();
        } else if ("2.2".equals(option)) {
            driver.findElement(dataSelect22).click();
        } else if ("2.3".equals(option)) {
            driver.findElement(dataSelect23).click();
        }
    }

    @Step("Нажатие на кнопку 'Добавить'")
    public void submitForm() {
        driver.findElement(dataSend).click();
    }

    @Step("Проверка: Отображается модальное окно с текстом: {expectedText}")
    public void checkModalWindow(String expectedText) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(modalDialog));
            WebElement modal = driver.findElement(modalDialog);

            String modalText = modal.getText();
            assertEquals(modalText, expectedText, "Модальное окно содержит неправильный текст");

        } catch (TimeoutException e) {
            throw new RuntimeException("Модальное окно не появилось. Ожидание завершилось с ошибкой", e);
        }
    }

    @Step("Скрытие модального окна")
    public void clickOk() {
        driver.findElement(modalDialogButtonOk).click();
    }

    @Step("Заполнение формы случайными данными")
    public void fillFormWithRandomData(FormData formData) {

        enterEmail(formData.getEmail());
        enterName(formData.getName());
        chooseGender(formData.getGender());
        chooseCheck(formData.getOptionOne());
        chooseSelect(formData.getOptionTwo());
        submitForm();
    }
    @Step ("Проверка: значения в таблице соответствуют введенным значениям при заполнении формы")
    public void checkTableRowContainsText(String email, String name, String gender, String optionOne, String optionTwo) {

        WebElement table = driver.findElement(dataTable);
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        WebElement lastRow = rows.get(rows.size() - 1);

        List<WebElement> cells = lastRow.findElements(By.tagName("td"));

        String actualEmail = cells.get(0).getText().trim();
        String actualName = cells.get(1).getText().trim();
        String actualGender = cells.get(2).getText().trim();
        String actualOptionOne = cells.get(3).getText().trim();
        String actualOptionTwo = cells.get(4).getText().trim();

        assertEquals(actualEmail, email, "Email в таблице не соответствует ожидаемому");
        assertEquals(actualName, name, "Имя в таблице не соответствует ожидаемому");
        assertEquals(actualGender, gender, "Пол в таблице не соответствует ожидаемому");
        assertEquals(actualOptionOne, optionOne, "Вариант 1 в таблице не соответствует ожидаемому");
        assertEquals(actualOptionTwo, optionTwo, "Вариант 2 в таблице не соответствует ожидаемому");
    }

    @Step("Проверка: в таблице отображаются ровно две строки для двух форм")
    public void checkTwoRowsInTable() {

        WebElement table = driver.findElement(dataTable);
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        // Проверка, что в таблице ровно две строки
        assertEquals(rows.size(), 3, "В таблице должно быть ровно две строки, но найдено: " + rows.size());
    }

    @Step("Проверка: Ошибка формата email отображается")
    public boolean isEmailFormatErrorDisplayed() {
        try {
            return driver.findElement(emailFormatError).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Step("Проверка: Ошибка незаполнения поля имени отображается")
    public boolean isBlankNameErrorDisplayed() {
        try {
            return driver.findElement(blankNameError).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}
