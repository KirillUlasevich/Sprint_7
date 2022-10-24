package courier;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
public class BaseClient {
    protected RequestSpecification getSpec() {
        return given().log().all()
                .header("Content-Type", "application/json")
                .baseUri("https://qa-scooter.praktikum-services.ru/api/v1");
    }
}