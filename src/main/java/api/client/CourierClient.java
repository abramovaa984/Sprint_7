package api.client;

import api.model.Courier;
import api.model.Login;
import api.util.Constants;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends Constants {

    @Step("Get response for api.client.Courier")
    public ValidatableResponse getCourierResponse(Courier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(API_COURIER)
                .then();
    }

    @Step("Get response for Login")
    public ValidatableResponse getLoginResponse(Login login) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(login)
                .when()
                .post(API_LOGIN)
                .then();
    }
}