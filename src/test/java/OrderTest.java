import jdk.jfr.Description;
import order.Order;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertNotEquals;

public class OrderTest {
    private int orderTrack;
    Order order;

    @Before
    public void setUp() {
        order = new Order();
    }

    @Test
    @Description("Проверка заказа с цветом BLACK")
    public void createOrderBlackColourTest() {
        File json = new File("src/test/resources/order.json");
        order.createOrder(json)
                .assertThat()
                .statusCode(201);
    }

    @Test
    @Description("Проверка заказа с цветом GREY")
    public void createOrderGreyColourTest() {
        File json = new File("src/test/resources/ordergrey.json");
        order.createOrder(json)
                .assertThat()
                .statusCode(201);
    }

    @Test
    @Description("Проверка заказа с двумя цветами")
    public void createOrderTwoColoursTest() {
        File json = new File("src/test/resources/ordertwocolours.json");
        order.createOrder(json)
                .assertThat()
                .statusCode(201);
    }

    @Test
    @Description("Проверка заказа без цвета")
    public void createOrderNoColoursTest() {
        File json = new File("src/test/resources/ordernocolour.json");
        order.createOrder(json)
                .assertThat()
                .statusCode(201);
    }

    @Test
    @Description("Проверка, что тело ответа содержит track")
    public void orderReturnTrackTest() {
        File json = new File("src/test/resources/ordernocolour.json");
        orderTrack = order.createOrder(json)
                .assertThat()
                .statusCode(201)
                .extract().path("track");
        assertNotEquals(0, orderTrack);
    }
}