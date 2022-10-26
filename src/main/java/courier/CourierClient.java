package courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class CourierClient extends BaseClient {
    private final String ROOT = "/courier";
    private final String WRONG_ROOT = "/courieru";
    private final String COURIER = "/courier/{courierId}";
    private final String LOGIN = ROOT + "/login";
    private final String ORDER = "/orders?courierId={courierId}";

    @Step("Создание курьера")
    public ValidatableResponse create(Courier courier) {
        return getSpec()
                .body(courier)
                .when()
                .post(ROOT)
                .then().log().all();
    }

    @Step("Запрос несуществующей ручки")
    public ValidatableResponse createWrongRoot(Courier courier) {
        return getSpec()
                .body(courier)
                .when()
                .post(WRONG_ROOT)
                .then().log().all();
    }

    @Step("Неверный метод запроса GET")
    public ValidatableResponse createWrongMethod(Courier courier) {
        return getSpec()
                .body(courier)
                .when()
                .get(ROOT)
                .then().log().all();
    }

    @Step("Создание курьера с ошибкой")
    public ValidatableResponse createFailed(Courier courier) {
        return getSpec()
                .body(courier)
                .when()
                .post(ROOT)
                .then().log().all();
    }

    @Step("Авторизация курьера")
    public ValidatableResponse login(CourierData data) {
        return getSpec()
                .body(data)
                .when()
                .post(LOGIN)
                .then().log().all();
    }

    @Step("Авторизация с неверным логином")
    public ValidatableResponse loginWrong(CourierData data) {
        return getSpec()
                .body(data)
                .when()
                .post(LOGIN)
                .then().log().all();
    }

    @Step("Удаление курьера")
    public void delete(int courierId) {
        getSpec()
                .pathParam("courierId", courierId)
                .when()
                .delete(COURIER)
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrders(int courierId) {
        return getSpec()
                .pathParam("courierId", courierId)
                .when()
                .get(ORDER)
                .then().log().all();
    }
}

