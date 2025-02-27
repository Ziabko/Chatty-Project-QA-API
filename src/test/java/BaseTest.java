import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;

import static io.restassured.RestAssured.given;

public class BaseTest {
    static  String BASE_URI = "http://chatty.telran-edu.de:8989";
    static RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri(BASE_URI)
            .setContentType(ContentType.JSON)
            .build();

    public String loginAndGetTokenUser() {
        LoginUserRequest loginUserRequest = new LoginUserRequest("z0667272624@gmail.com", "UserOlga1");
        Response responseLogin = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse loginUserResponse = responseLogin.as(LoginUserResponse.class);
        return loginUserResponse.getAccessToken();
    }


    //postRequest method
    public static Response postRequest (String endPoint, Integer expectedStatusCode, Object body) {  //endPoint - адрес после базового адреса
        Response response = given()
                .spec(requestSpecification)
                .body(body)
                .when()
                .log().all()
                .post(endPoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;
    }

    public static Response postRequestWithToken (String endPoint, Integer expectedStatusCode, Object body, String token) {  //endPoint - адрес после базового адреса
        Response response = given()
                .spec(requestSpecification)
                .header("Authorization", "Bearer " + token)
                .body(body)
                .when()
                .log().all()
                .post(endPoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;
    }

    //getRequest method
    public static Response getRequest(String endPoint, Integer expectedStatusCode, String token){
        Response response = given()
                .spec(requestSpecification)
                .header("Authorization", "Bearer " + token)
                .when()
                .log().all()
                .get(endPoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;

    }
    public static Response putRequest(String endPoint, Integer expectedStatusCode, Object body, String token){
        Response response = given()
                .spec(requestSpecification)
                .header("Authorization", "Bearer " + token)
                .body(body)
                .when()
                .log().all()
                .put(endPoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;

    }

    //deleteRequest method
    public static Response deleteRequestAsAdmin(String endPoint, Integer expectedStatusCode, String tokenAdmin){
        Response response = given()
                .spec(requestSpecification)
                .header("Authorization", "Bearer " + tokenAdmin)
                .when()
                .log().all()
                .delete(endPoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;
    }

    public static Response deleteRequest(String endPoint, Integer expectedStatusCode, String token){
        Response response = given()
                .spec(requestSpecification)
                .header("Authorization", "Bearer " + token)
                .when()
                .log().all()
                .delete(endPoint)
                .then()
                .log().all()
                .statusCode(expectedStatusCode)
                .extract().response();
        return response;
    }
    
}
