import api.client.CourierClient;
import api.model.Login;
import api.util.Methods;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Тесты логина курьера")
public class TestLoginCourier extends Methods {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
        createAccount();
    }

    @Test
    @DisplayName("Тест возможности авторизоваться курьеру")
    public void testCourierCanBeAuthorized() {
        CourierClient courierClient = new CourierClient();
        ValidatableResponse dataCourier = courierClient.getLoginResponse(
                new Login(existingLogin, existingLoginPassword));
        dataCourier
                .statusCode(200)
                .assertThat()
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Тест возможности авторизации курьера без поля login")
    public void testCourierCanBeAuthorizationWithoutLogin() {
        CourierClient courierClient = new CourierClient();
        ValidatableResponse dataCourierWithoutLogin = courierClient.getLoginResponse(
                new Login(null, existingLoginPassword));
        dataCourierWithoutLogin
                .statusCode(400)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Тест возможности авторизации курьера без поля password")
    public void testCourierCanBeAuthorizationWithoutPassword() {
        CourierClient courierClient = new CourierClient();
        ValidatableResponse dataCourierWithoutLogin = courierClient.getLoginResponse(
                new Login(existingLogin, ""));
        dataCourierWithoutLogin
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Тест возможности авторизации курьера c неправильным полем password или login")
    public void testCourierCanBeAuthorizationWithWrongPasswordOrLogin() {
        CourierClient courierClient = new CourierClient();
        ValidatableResponse nonExistentData = courierClient.getLoginResponse(
                Login.getRandomLoginData());
        nonExistentData
                .statusCode(404)
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void tearDown() {
        deleteLogin();
    }
}
