package courier;

import io.qameta.allure.Step;
import lombok.Data;

@Data
public class CourierData {
    private String login;
    private String password;

    public CourierData(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Step("Создание данных для входа")
    public static CourierData from(Courier courier) {
        return new CourierData(courier.getLogin(), courier.getPassword());
    }

    @Step("Создание данных для входа без логина")
    public static CourierData getWithoutLogin(Courier courier) {
        return new CourierData("", courier.getPassword());
    }

    @Step("Создание данных для входа курьера без пароля")
    public static CourierData getWithoutPassword(Courier courier) {
        return new CourierData(courier.getLogin(), "");
    }

    @Step("Создание данных для входа с неверным паролем")
    public static CourierData getWrongPassword(Courier courier) {
        return new CourierData(courier.getLogin(), "Pass");
    }

    @Step("Создание данных для входа с неверным логином")
    public static CourierData getWrongLogin(Courier courier) {
        return new CourierData("Log", courier.getPassword());
    }

    @Step("Создание данных для входа с неверными логином и паролем")
    public static CourierData getWrongCourier(Courier courier) {
        return new CourierData("Log", "Pass");
    }
}