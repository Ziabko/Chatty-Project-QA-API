import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LoginUserTest extends BaseTest {
    Faker faker = new Faker();
    String userEmail = faker.internet().emailAddress();
    String userPassword = faker.internet().password();
    String invalidField = faker.lorem().word();

    @Test
    public void successLoginUser() {
        CreateUserRequest createUserRequest = new CreateUserRequest(userEmail, userPassword, userPassword, "user");
        Response response = postRequest("/api/auth/register", 201, createUserRequest);
        CreateUserResponse responseBody = response.as(CreateUserResponse.class);

        LoginUserRequest loginUserRequest = new LoginUserRequest(userEmail, userPassword);
        Response responseLogin = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse loginUserResponse = responseLogin.as(LoginUserResponse.class);

        //1. Check that Access token, refresh token and expiration are not empty
        assertFalse(loginUserResponse.getAccessToken().isEmpty());
        assertFalse(loginUserResponse.getRefreshToken().isEmpty());
        assertNotNull(loginUserResponse.getExpiration());
    }

    @Test
    public void successLoginAdmin() {
        CreateUserRequest createAdminRequest = new CreateUserRequest(userEmail, userPassword, userPassword, "admin");
        Response response = postRequest("/api/auth/register", 201, createAdminRequest);
        CreateUserResponse responseBody = response.as(CreateUserResponse.class);

        LoginUserRequest loginUserRequest = new LoginUserRequest("zyablik2004@ukr.net", "AdminOlga1");
        Response responseLogin = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse loginUserResponse = responseLogin.as(LoginUserResponse.class);

        //1. Check that Access token, refresh token and expiration are not empty
        assertFalse(loginUserResponse.getAccessToken().isEmpty());
        assertFalse(loginUserResponse.getRefreshToken().isEmpty());
        assertNotNull(loginUserResponse.getExpiration());
    }

    @Test
    public void invalidLoginUserWithInvalidEmail() {
        CreateUserRequest createUserRequest = new CreateUserRequest(userEmail, userPassword, userPassword, "user");
        Response response = postRequest("/api/auth/register", 201, createUserRequest);
        CreateUserResponse responseBody = response.as(CreateUserResponse.class);

        LoginUserRequest loginUserRequest = new LoginUserRequest(invalidField + userEmail, userPassword);
        Response responseLogin = postRequest("/api/auth/login", 404, loginUserRequest);

        //1. Check error message
        String responseMessage = responseLogin.getBody().asString();
        assertTrue(responseMessage.contains("User not found!"));

    }

    @Test
    public void invalidLoginUserWithEmailWithoutAt() {
        CreateUserRequest createUserRequest = new CreateUserRequest(userEmail, userPassword, userPassword, "user");
        Response response = postRequest("/api/auth/register", 201, createUserRequest);
        CreateUserResponse responseBody = response.as(CreateUserResponse.class);

        LoginUserRequest loginUserRequest = new LoginUserRequest(invalidField, userPassword);
        Response responseLogin = postRequest("/api/auth/login", 400, loginUserRequest);

        //1. Check error message
        String responseMessage = responseLogin.getBody().asString();
        assertTrue(responseMessage.contains("Invalid email format"));
    }


    @Test
    public void invalidLoginUserWithInvalidPassword() {
        CreateUserRequest createUserRequest = new CreateUserRequest(userEmail, userPassword, userPassword, "user");
        Response response = postRequest("/api/auth/register", 201, createUserRequest);
        CreateUserResponse responseBody = response.as(CreateUserResponse.class);

        LoginUserRequest loginUserRequest = new LoginUserRequest(userEmail, "invalidPassword123");
        Response responseLogin = postRequest("/api/auth/login", 401, loginUserRequest);

        //1. Check error message
        String errorMessage = responseLogin.jsonPath().getString("message");
        assertEquals("The password does not match the one saved in the database!", errorMessage, "Unexpected error message");
    }

    @Test
    public void invalidLoginUserEmptyEmail() {
        CreateUserRequest createUserRequest = new CreateUserRequest(userEmail, userPassword, userPassword, "user");
        Response response = postRequest("/api/auth/register", 201, createUserRequest);

        LoginUserRequest loginUserRequest = new LoginUserRequest("", userPassword);
        Response responseLogin = postRequest("/api/auth/login", 400, loginUserRequest);

        //1.  Check error message
        String responseBody = responseLogin.getBody().asString();
        assertTrue(responseBody.contains("Email cannot be empty"));
        assertTrue(responseBody.contains("Invalid email format"));

    }

    @Test
    public void invalidLoginUserEmptyPassword() {
        CreateUserRequest createUserRequest = new CreateUserRequest(userEmail, userPassword, userPassword, "user");
        Response response = postRequest("/api/auth/register", 201, createUserRequest);

        LoginUserRequest loginUserRequest = new LoginUserRequest(userEmail, "");
        Response responseLogin = postRequest("/api/auth/login", 400, loginUserRequest);

        //1.  Check error message
        String responseBody = responseLogin.getBody().asString();
        assertTrue(responseBody.contains("Password cannot be empty"));
        assertTrue(responseBody.contains("Password must contain letters and numbers"));
        assertTrue(responseBody.contains("Password must contain at least 8 characters"));
    }

    @Test
    public void loginUserWithPutMethod() {
        LoginUserRequest loginUserRequest = new LoginUserRequest(userEmail, userPassword);
        Response response = putRequest("/api/auth/login", 405, loginUserRequest, null);
        assertEquals(405, response.getStatusCode(), "Expected 405 Method Not Allowed for PUT on /api/auth/login");
    }

    @Test
    public void loginUserWithDeleteMethod() {
        Response response = deleteRequest("/api/auth/login", 405, null);
        assertEquals(405, response.getStatusCode(), "Expected 405 Method Not Allowed for DELETE on /api/auth/login");
    }

    @Test
    public void loginUserWithGetMethod() {
        Response response = getRequest("/api/auth/login", 405, null);
        assertEquals(405, response.getStatusCode(), "Expected 405 Method Not Allowed for GET on /api/auth/login");
    }

}
