import api.client.CourierClient;
import api.model.Courier;
import api.util.Methods;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

@DisplayName("Тесты создания курьера")
public class TestCreateCourier extends Methods {
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        createAccount();
    }

    @Test
    @DisplayName("Тест возможности создания двух одинаковых курьеров")
    public void testCreatingTwoIdenticalCouriers() {
        CourierClient courierClient = new CourierClient();
        ValidatableResponse duplicateLogin  = courierClient.getCourierResponse(
                new Courier(existingLogin, existingLoginPassword, existingLoginFirstName));
        duplicateLogin
                .statusCode(409)
                .assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Тест возможности создания курьера без поля login")
    public void testCourierCanBeCreatedWithoutLogin() {
        CourierClient courierClient = new CourierClient();
        ValidatableResponse emptyLoginField  = courierClient.getCourierResponse(
                new Courier(null, existingLoginPassword, existingLoginFirstName));
        emptyLoginField
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Тест возможности создания курьера без поля password")
    public void testCourierCanBeCreatedWithoutPassword() {
        CourierClient courierClient = new CourierClient();
        ValidatableResponse emptyPasswordField  = courierClient.getCourierResponse(
                new Courier(existingLogin, null, existingLoginFirstName));
        emptyPasswordField
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Тест возможности создания курьера без поля firstName")
    public void testCourierCanBeCreatedWithoutFirstName() {
        CourierClient courierClient = new CourierClient();
        ValidatableResponse duplicateLogin  = courierClient.getCourierResponse(
                Courier.getRandomCourier());
        duplicateLogin
                .statusCode(201)
                .assertThat()
                .body("ok", equalTo(true));
    }

    @After
    public void tearDown(){
        deleteLogin();
    }
}
