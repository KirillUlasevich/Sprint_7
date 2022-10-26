import courier.Courier;
import courier.CourierClient;
import courier.CourierData;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class CourierCreationTest {
    Courier courier;
    CourierClient courierClient;
    private int courierId;

    @Before
    public void setup() {
        courier = Courier.getRandomCourier();
        courierClient = new CourierClient();
    }

    @After
    public void deleteCourier() {
        CourierData data = CourierData.from(courier);
        courierId = courierClient.login(data)
                .extract().path("id");
        courierClient.delete(courierId);
    }

    @Test
    @Description("Проверка возможности создания курьера")
    public void courierCreateTest() {
        courierClient.create(courier)
                .assertThat()
                .statusCode(201);
    }

    @Test
    @Description("Проверка невозможности создания двух одинаковых курьеров")
    public void createTwoCouriersFailTest() {
        courierClient.create(courier);
        courierClient.create(courier)
                .assertThat()
                .statusCode(409);
    }

    @Test
    @Description("Првоерка получения списка заказов")
    public void getOrdersTest() {
        courierClient.create(courier);
        CourierData data = CourierData.from(courier);
        courierId = courierClient.login(data)
                .extract().path("id");
        ArrayList orderResponse = courierClient.getOrders(courierId)
                .extract().path("orders");
        assertNotNull(orderResponse);
    }

    @Test
    @Description("Проверка ошибки при создании второго курьера с существующими данными")
    public void createTwoCouriersMessageTest() {
        courierClient.create(courier);
        String loginAlreadyExists = courierClient.create(courier)
                .statusCode(409)
                .extract().path("message");
        assertEquals("Этот логин уже используется. Попробуйте другой.", loginAlreadyExists);
    }

    @Test
    @Description("Проверка, что успешный запрос возвращает ok: true")
    public void courierTestReturnTrue() {
        boolean isOk = courierClient.create(courier)
                .extract().path("ok");
        assertTrue(isOk);
    }
}