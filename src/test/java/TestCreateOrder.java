

import api.client.OrdersClient;
import api.model.Order;
import api.util.Constants;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
@DisplayName("Тесты создания заказа")
public class TestCreateOrder extends Constants {
    private final String firstNameValue;
    private final String lastNameValue;
    private final String addressValue;
    private final int metroStationValue;
    private final String phoneValue;
    private final int rentTimeValue;
    private final String deliveryDateValue;
    private final String commentValue;
    private final List<String> colorValue;
    public int track;

    public TestCreateOrder(String firstNameValue, String lastNameValue, String addressValue, int metroStationValue, String phoneValue, int rentTimeValue, String deliveryDateValue, String commentValue, List<String> colorValue) {
        this.firstNameValue = firstNameValue;
        this.lastNameValue = lastNameValue;
        this.addressValue = addressValue;
        this.metroStationValue = metroStationValue;
        this.phoneValue = phoneValue;
        this.rentTimeValue = rentTimeValue;
        this.deliveryDateValue = deliveryDateValue;
        this.commentValue = commentValue;
        this.colorValue = colorValue;
    }

    @Parameterized.Parameters
    public static Object[][] getTestDataCreateOrder() {
        return new Object[][]{
                {"Тест", "Тестович", "Комсомольская 10", 10, "8348358374", 2, "2023-01-15", "2-й подъезд", null},
                {"Иван", "Иванов", "Комсомольская 10", 10, "89965474376", 2, "2023-01-15", "2-й подъезд", List.of("BLACK")},
                {"Иван", "Иванов", "Комсомольская 10", 10, "89965474376", 2, "2023-01-15", "2-й подъезд", List.of("GREY")},
                {"Иван", "Иванов", "Комсомольская 10", 10, "89965474376", 2, "2023-01-15", "2-й подъезд", List.of("BLACK", "GREY")},
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void testCodeAndBody(){
        OrdersClient ordersClient = new OrdersClient();
        ValidatableResponse emptyPasswordField  = ordersClient.getOrdersResponse(
                new Order(firstNameValue, lastNameValue, addressValue,
                        metroStationValue, phoneValue, rentTimeValue, deliveryDateValue, commentValue, colorValue));
        emptyPasswordField
                .assertThat()
                .statusCode(201)
                .and()
                .body("track", notNullValue());
    }

    @After
    public void tearDown() {
        String json = "{\"track\": " + track + "}";
        Response responseCancel =
                given().header("Content-type", "application/json")
                        .and()
                        .body(json)
                        .when()
                        .put("/api/v1/orders/cancel");
        int statusCancel = responseCancel.statusCode();
        if (statusCancel != 200) {
            System.out.println("Не удалось отменить заказ");
        }
    }
}