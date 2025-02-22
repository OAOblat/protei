package Helper;

public class FormData {
    private String email;
    private String name;
    private String gender;
    private String optionOne;
    private String optionTwo;

    // Конструктор
    public FormData(String email, String name, String gender, String optionOne, String optionTwo) {
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.optionOne = optionOne;
        this.optionTwo = optionTwo;
    }

    // Геттеры
    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getOptionOne() {
        return optionOne;
    }

    public String getOptionTwo() {
        return optionTwo;
    }
}