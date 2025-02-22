package InputsPage;

import Helper.BaseTest;
import Helper.FormData;
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
@Feature("Форма ввода")
public class InputsPageTest extends BaseTest {

    private InputsPage inputsPage;

    @BeforeEach
    @Step("Открытие страницы авторизации")
    public void openLoginPage() {
        inputsPage = new InputsPage(driver);
        String filePath = Paths.get("src/main/qa-test.html").toAbsolutePath().toUri().toString();
        driver.get(filePath);
        inputsPage.signIn();
    }

    @Test
    @Story("Заполнение формы")
    @DisplayName("При отправке формы со всеми валидными данными - данные отображаются в таблице")
    @Severity(SeverityLevel.CRITICAL)
    public void testSendDataDisplayInTable() {
        FormData formData = TestData.generateFormData();
        inputsPage.fillFormWithRandomData(formData);
        inputsPage.clickOk();
        inputsPage.checkTableRowContainsText(formData.getEmail(), formData.getName(), formData.getGender(),
                formData.getOptionOne(), formData.getOptionTwo());
    }

    @Test
    @Story("Заполнение формы")
    @DisplayName("При отправке формы дважды с разными данными - оба результата отображаются в таблице")
    @Severity(SeverityLevel.NORMAL)
    public void testSendDataTwiceDisplayInTable() {
        FormData formData = TestData.generateFormData();
        inputsPage.fillFormWithRandomData(formData);
        inputsPage.clickOk();
        inputsPage.submitForm();
        inputsPage.clickOk();

        inputsPage.checkTwoRowsInTable();
    }

    @Test
    @Story("Заполнение формы")
    @DisplayName("При отправке формы в двумя выбранными пунктами в первом выборе - данные отображаются в таблице")
    @Severity(SeverityLevel.NORMAL)
    public void testSendDataWithTwoOptionsDisplayInTable() {
        FormData formData = TestData.generateFormData();

        String email = formData.getEmail();
        String name = formData.getName();
        String gender = formData.getGender();
        String optionOne1 = "1.1";
        String optionOne2 = "1.2";
        String optionTwo = formData.getOptionTwo();

        inputsPage.enterEmail(email);
        inputsPage.enterName(name);
        inputsPage.chooseGender(gender);
        inputsPage.chooseCheck(optionOne1);
        inputsPage.chooseCheck(optionOne2);
        inputsPage.chooseSelect(optionTwo);
        inputsPage.submitForm();
        inputsPage.clickOk();
        inputsPage.checkTableRowContainsText(email, name, gender, "1.1, 1.2", optionTwo);
    }

    @Test
    @Story("Заполнение формы")
    @DisplayName("При отправке формы c не выбранными вариантами - соответствующие данные отображаются в таблице")
    @Severity(SeverityLevel.NORMAL)
    public void testSendDataWithNoOptionsDisplayInTable() {
        FormData formData = TestData.generateFormData();

        String email = formData.getEmail();
        String name = formData.getName();
        String gender = formData.getGender();

        inputsPage.enterEmail(email);
        inputsPage.enterName(name);
        inputsPage.chooseGender(gender);
        inputsPage.submitForm();
        inputsPage.clickOk();
        inputsPage.checkTableRowContainsText(email, name, gender, "Нет", "");
    }

    @Test
    @Story("Ошибочные состояния")
    @DisplayName("При отправке формы с незаполненным полем имени - отображается ошибка, что поле не может быть пустым")
    @Severity(SeverityLevel.MINOR)
    public void testBlankNameErrorDisplay() {
        FormData formData = TestData.generateFormData();

        String email = formData.getEmail();
        String gender = formData.getGender();
        String optionOne = formData.getOptionOne();
        String optionTwo = formData.getOptionTwo();

        inputsPage.enterEmail(email);
        inputsPage.enterName("");
        inputsPage.chooseGender(gender);
        inputsPage.chooseCheck(optionOne);
        inputsPage.chooseSelect(optionTwo);
        inputsPage.submitForm();
        assertTrue(inputsPage.isBlankNameErrorDisplayed(),
                "Ожидалось, что появится ошибка пустого поля имени, но она не отобразилась");

    }


    @ParameterizedTest
    @MethodSource("Helper.TestData#invalidEmails")
    @Story("Ошибочные состояния")
    @DisplayName("Отображение ошибки при вводе email в неверном формате")
    @Severity(SeverityLevel.MINOR)
    public void testEmailFormatErrorIsDisplayed(String invalidEmail) {
        FormData formData = TestData.generateFormData();

        String name = formData.getName();
        String gender = formData.getGender();
        String optionOne = formData.getOptionOne();
        String optionTwo = formData.getOptionTwo();

        inputsPage.enterEmail(invalidEmail);
        inputsPage.enterName(name);
        inputsPage.chooseGender(gender);
        inputsPage.chooseCheck(optionOne);
        inputsPage.chooseSelect(optionTwo);
        inputsPage.submitForm();
        assertTrue(inputsPage.isEmailFormatErrorDisplayed(),
                "Ожидалось, что появится ошибка формата email, но она не отобразилась. Используемый email: " + invalidEmail);
    }

    @Test
    @Story("Заполнение формы")
    @DisplayName("При отправке формы со всеми валидными данными  - отображается модальное окно об успешном добавлении")
    @Severity(SeverityLevel.MINOR)
    public void testModalWindowDisplay() {
        FormData formData = TestData.generateFormData();
        inputsPage.fillFormWithRandomData(formData);
        inputsPage.checkModalWindow("Данные добавлены.");
    }



}