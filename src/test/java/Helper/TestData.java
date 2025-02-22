package Helper;

import net.datafaker.Faker;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TestData {
    private static final Faker faker = new Faker();
    private static final Random random = new Random();

    // Валидные данные
    public static final String VALID_EMAIL = "test@protei.ru";
    public static final String VALID_PASSWORD = "test";

    // Список недействительных email
    public static List<String> invalidEmails() {
        return Arrays.asList(
                "plainaddress", "missing@dotcom", "missingatsign.com",
                "user@.com", "user@com", "user@domain..com", "user@", ""
        );
    }

    // Генерация случайного пароля
    public static String generateInvalidPassword() {
        return faker.lorem().characters(1, 5);
    }

    // Генерация альтернативного валидного email
    public static String generateAlternativeValidEmail() {
        String[] domains = {"protei.com", "test.com", "example.org", "mail.ru"};
        return "test" + random.nextInt(1000) + "@" + domains[random.nextInt(domains.length)];
    }

    // Генерация случайного имени
    private static final List<String> NAMES = Arrays.asList("Маша", "Иван", "Анна", "Алексей", "Мария", "Дмитрий");

    public static String generateRandomName() {
        return NAMES.get(random.nextInt(NAMES.size()));
    }

    // Генерация случайного пола
    private static final List<String> GENDERS = Arrays.asList("Мужской", "Женский");

    public static String generateRandomGender() {
        return GENDERS.get(random.nextInt(GENDERS.size()));
    }

    // Генерация случайного варианта для опции 1
    private static final List<String> OPTIONS_1 = Arrays.asList("1.1", "1.2");

    public static String generateRandomOptionOne() {
        return OPTIONS_1.get(random.nextInt(OPTIONS_1.size()));
    }

    // Генерация случайного варианта для опции 2
    private static final List<String> OPTIONS_2 = Arrays.asList("2.1", "2.2", "2.3");

    public static String generateRandomOptionTwo() {
        return OPTIONS_2.get(random.nextInt(OPTIONS_2.size()));
    }

    // Генерация данных формы
    public static FormData generateFormData() {
        return new FormData(
                generateAlternativeValidEmail(),
                generateRandomName(),
                generateRandomGender(),
                generateRandomOptionOne(),
                generateRandomOptionTwo()
        );
    }
}