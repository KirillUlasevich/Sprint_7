import courier.Courier;
import courier.CourierClient;
import courier.CourierData;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class LoginTest {
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
    @Description("Проверка возможности залогиниться")
    public void courierLoginTest() {
        courierClient.create(courier);
        CourierData data = CourierData.from(courier);
        courierId = courierClient.login(data)
                .extract().path("id");
        courierClient.login(data)
                .assertThat()
                .statusCode(200);

    }

    @Test
    @Description("Проверка невозможности входа без логина")
    public void enterNoLoginTest() {
        courierClient.create(courier);
        CourierData dataNoLogin = CourierData.getWithoutLogin(courier);
        courierClient.loginWrong(dataNoLogin)
                .assertThat()
                .statusCode(400);
    }

    @Test
    @Description("Проверка невозможности входа без пароля")
    public void enterNoPassTest() {
        courierClient.create(courier);
        CourierData dataNoPassword = CourierData.getWithoutPassword(courier);
        courierClient.loginWrong(dataNoPassword)
                .assertThat()
                .statusCode(400);
    }

    @Test
    @Description("Проверка сообщения об ошибке при входе без логина")
    public void enterWithoutLoginMessageTest() {
        courierClient.create(courier);
        CourierData dataNoLogin = CourierData.getWithoutLogin(courier);
        courierClient.loginWrong(dataNoLogin);
        String message = courierClient.loginWrong(dataNoLogin)
                .extract().path("message");
        assertEquals("Недостаточно данных для входа", message);
    }

    @Test
    @Description("Проверка сообщения об ошибке при входе без пароля")
    public void enterWithoutPasswordMessageTest() {
        courierClient.create(courier);
        CourierData dataNoPassword = CourierData.getWithoutPassword(courier);
        courierClient.loginWrong(dataNoPassword)
                .statusCode(400);
        String message = courierClient.loginWrong(dataNoPassword)
                .extract().path("message");
        assertEquals("Недостаточно данных для входа", message);
    }

    @Test
    @Description("Проверка сообщения об ошибке при входе с неверным паролем")
    public void enterWrongPasswordMessageTest() {
        courierClient.create(courier);
        CourierData dataWrongPassword = CourierData.getWrongPassword(courier);
        courierClient.loginWrong(dataWrongPassword)
                .statusCode(404);
        String message = courierClient.loginWrong(dataWrongPassword)
                .extract().path("message");
        assertEquals("Учетная запись не найдена", message);
    }

    @Test
    @Description("Проверка сообщения об ошибке при входе с неверным логином")
    public void enterWrongLoginMessageTest() {
        courierClient.create(courier);
        CourierData dataWrongPLogin = CourierData.getWrongLogin(courier);
        courierClient.loginWrong(dataWrongPLogin)
                .statusCode(404);
        String message = courierClient.loginWrong(dataWrongPLogin)
                .extract().path("message");
        assertEquals("Учетная запись не найдена", message);
    }

    @Test
    @Description("Проверка сообщения об ошибке при входе с несуществующим аккаунтом")
    public void enterWrongCourierMessageTest() {
        courierClient.create(courier);
        CourierData dataWrongCourier = CourierData.getWrongCourier(courier);
        courierClient.loginWrong(dataWrongCourier)
                .statusCode(404);
        String message = courierClient.loginWrong(dataWrongCourier)
                .extract().path("message");
        assertEquals("Учетная запись не найдена", message);
    }

    @Test
    @Description("Проверка возвращения id при успешном входе")
    public void courierLoginReturnIdTest() {
        courierClient.create(courier);
        CourierData data = CourierData.from(courier);
        courierClient.login(data);
        courierId = courierClient.login(data)
                .extract().path("id");
        assertNotEquals(0, courierId);
    }
}