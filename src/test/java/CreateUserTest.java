import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.*;

public class CreateUserTest extends BaseTest {

    Faker faker = new Faker();
    String userEmail = faker.internet().emailAddress();
    String userPassword = faker.internet().password();
    String invalidField = faker.lorem().word();

    @Test
    public void successfulCreatedUser() {
        CreateUserRequest createUserRequest = new CreateUserRequest(userEmail, userPassword, userPassword, "user");
        Response responseCreate = postRequest("/api/auth/register", 201, createUserRequest);

        CreateUserResponse createUserResponseBody = responseCreate.as(CreateUserResponse.class);

        //1. Check that Access token, refresh token and expiration are not empty
        assertFalse(createUserResponseBody.getAccessToken().isEmpty(), "Access token is empty!");
        assertFalse(createUserResponseBody.getRefreshToken().isEmpty(), "Refresh token is empty!");
        assertNotNull(createUserResponseBody.getExpiration());

    }

    @Test
    public void unsuccessfulCreateUserInvalidEmail() {
        CreateUserRequest createUserRequest = new CreateUserRequest(invalidField, userPassword, userPassword, "user");
        Response responseCreate = postRequest("/api/auth/register", 400, createUserRequest);

        // 1. Check that the accessToken response is empty
        assertNull(responseCreate.jsonPath().getString("accessToken"), "Access token is not empty");

        // 2. Check that the message contains the expected text
        String responseBody = responseCreate.getBody().asString();
        assertTrue(responseBody.contains("Email is not valid."));

    }

    @Test
    public void unsuccessfulCreateUserInvalidPassword() {
        CreateUserRequest createUserRequest = new CreateUserRequest(userEmail, invalidField, invalidField, "user");
        Response responseCreate = postRequest("/api/auth/register", 400, createUserRequest);

        // 1. Check that the accessToken response is empty
        assertNull(responseCreate.jsonPath().getString("accessToken"), "Access token is not empty");

        // 2. Check that the message contains the expected text
        String responseBody = responseCreate.getBody().asString();
        assertTrue(responseBody.contains("Password must contain at least 8 characters"));
        assertTrue(responseBody.contains("Password must contain letters and numbers"), "Unexpected error message");

    }

    @Test
    public void unsuccessfulCreateUserInvalidConfirmPassword() {
        CreateUserRequest createUserRequest = new CreateUserRequest(userEmail, userPassword, invalidField, "user");
        Response responseCreate = postRequest("/api/auth/register", 400, createUserRequest);

        // 1. Check that the accessToken response is empty
        assertNull(responseCreate.jsonPath().getString("accessToken"), "Access token is not empty");

        // 2. Check that the message contains the expected text
        String responseBody = responseCreate.getBody().asString();
        assertTrue(responseBody.contains("Password mismatch!"), "Unexpected error message");

    }

    @Test
    public void unsuccessfulCreateUserEmptyEmail() {
        CreateUserRequest createUserRequest = new CreateUserRequest("", userPassword, userPassword, "user");
        Response responseCreate = postRequest("/api/auth/register", 400, createUserRequest);

        // 1. Check that the accessToken response is empty
        assertNull(responseCreate.jsonPath().getString("accessToken"), "Access token is not empty");

        // 2. Check that the message contains the expected text
        String responseBody = responseCreate.getBody().asString();
        assertTrue(responseBody.contains("Email cannot be empty"), "Unexpected error message");

    }

    @Test
    public void unsuccessfulCreateUserEmptyPassword() {
        CreateUserRequest createUserRequest = new CreateUserRequest(userEmail, "", userPassword, "user");
        Response responseCreate = postRequest("/api/auth/register", 400, createUserRequest);

        // 1. Check that the accessToken response is empty
        assertNull(responseCreate.jsonPath().getString("accessToken"), "Access token is not empty");

        // 2. Check that the message contains the expected text
        String responseBody = responseCreate.getBody().asString();
        assertTrue(responseBody.contains("Password cannot be empty"), "Unexpected error message");

    }

    @Test
    public void unsuccessfulCreateUserEmptyConfirmPassword() {
        CreateUserRequest createUserRequest = new CreateUserRequest(userEmail, userPassword, "", "user");
        Response responseCreate = postRequest("/api/auth/register", 400, createUserRequest);

        // 1. Check that the accessToken response is empty
        assertNull(responseCreate.jsonPath().getString("accessToken"), "Access token is not empty");

        // 2. Check that the message contains the expected text
        String responseBody = responseCreate.getBody().asString();
        assertTrue(responseBody.contains("You need to confirm your password"), "Unexpected error message");

    }
    @Test
    public void createUserWithPutMethod() {
        CreateUserRequest createUserRequest = new CreateUserRequest(userEmail, userPassword, userPassword, "user");
        Response response = putRequest("/api/auth/register", 405, createUserRequest, null);
        assertEquals(405, response.getStatusCode(), "Expected 405 Method Not Allowed for PUT on /api/auth/register");
    }

    @Test
    public void createUserWithDeleteMethod() {
        Response response = deleteRequest("/api/auth/register", 405, null);
        assertEquals(405, response.getStatusCode(), "Expected 405 Method Not Allowed for DELETE on /api/auth/register");
    }

    @Test
    public void createUserWithGetMethod() {
        Response response = getRequest("/api/auth/register", 405, null);
        assertEquals(405, response.getStatusCode(), "Expected 405 Method Not Allowed for GET on /api/auth/register");
    }

}



