import courier.Courier;
import courier.CourierClient;
import jdk.jfr.Description;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CreateWithMistakesTest {
    Courier courier;
    CourierClient courierClient;

    @Before
    public void setup() {
        courier = Courier.getRandomCourier();
        courierClient = new CourierClient();
    }

    @Test
    @Description("Проверка невозможности создания курьера без пароля")
    public void createWithoutPasswordTest() {
        courier = Courier.getWithoutPassword();
        courierClient.createFailed(courier)
                .assertThat()
                .statusCode(400);
    }

    @Test
    @Description("Проверка невозможности создания курьера без логина")
    public void createWithoutLoginTest() {
        courier = Courier.getWithoutLogin();
        courierClient.createFailed(courier)
                .assertThat()
                .statusCode(400);
    }

    @Test
    @Description("Проверка ответа на запрос на несуществующую ручку")
    public void createCourierWrongRootStatusTest() {
        courierClient.createWrongRoot(courier)
                .assertThat()
                .statusCode(404);
    }

    @Test
    @Description("Проверка ответа на неверный метод запроса GET")
    public void createCourierWrongMethodStatusTest() {
        courierClient.createWrongMethod(courier)
                .assertThat()
                .statusCode(404);
    }

    @Test
    @Description("Проверка сообщения о невведенном пароле при создании аккаунта")
    public void createWithoutPasswordMessageTest() {
        courier = Courier.getWithoutPassword();
        String requestWithoutLoginOrPassword = courierClient.createFailed(courier)
                .statusCode(400)
                .extract().path("message");
        assertEquals("Недостаточно данных для создания учетной записи", requestWithoutLoginOrPassword);
    }

    @Test
    @Description("Проверка сообщения о невведенном логине при создании аккаунта")
    public void createWithoutLoginMessageTest() {
        courier = Courier.getWithoutLogin();
        String requestWithoutLoginOrPassword = courierClient.createFailed(courier)
                .statusCode(400)
                .extract().path("message");
        assertEquals("Недостаточно данных для создания учетной записи", requestWithoutLoginOrPassword);
    }
}