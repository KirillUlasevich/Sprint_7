package order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.io.File;

import static io.restassured.RestAssured.given;

public class Order {
    public final String ORDER_ROOT = "/orders";

    @Step("Создание заказа")
    public ValidatableResponse createOrder(File file) {
        return given().log().all()
                .header("Content-Type", "application/json")
                .baseUri("https://qa-scooter.praktikum-services.ru/api/v1")
                .body(file)
                .when()
                .post(ORDER_ROOT)
                .then().log().all();
    }
}