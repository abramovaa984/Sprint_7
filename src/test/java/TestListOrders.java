import api.util.Constants;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Тесты списка заказов")
public class TestListOrders extends Constants {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Тест получения списка заказов")
    public void testOrdersList() {
        Response response = given()
                .when()
                .header("Content-type", "application/json")
                .get(API_ORDER);
        response.then().log().all()
                .assertThat()
                .body("orders", notNullValue());
    }
}