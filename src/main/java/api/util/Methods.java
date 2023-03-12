package api.util;

import api.model.Courier;
import api.model.Login;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Methods extends Constants {

    @Step ("Удаление логина")
    public void deleteLogin() {
        Login login = new Login(existingLogin, existingLoginPassword);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(login)
                        .when()
                        .post(API_LOGIN);
        String id = response.jsonPath().getString("id");
        given()
                .when()
                .delete(API_LOGIN + id);
    }

    @Step ("Создание аккаунта")
    public void createAccount() {
        Courier successfulCourier = new Courier(existingLogin, existingLoginPassword, existingLoginFirstName);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(successfulCourier)
                .when()
                .post(API_COURIER);
    }
}