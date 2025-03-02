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

        // 1. Check that the status code is 400.
        assertEquals(400, responseCreate.getStatusCode(), "Status code is not 400");

        // 2. Check that the accessToken response is empty
        assertNull(responseCreate.jsonPath().getString("accessToken"), "Access token is not empty");

        // 3. Check that the message contains the expected text
        List<String> errorMessages = responseCreate.jsonPath().getList("email", String.class);
        assertTrue(errorMessages.contains("Email is not valid."), "Expected error message is missing. Actual: " + errorMessages);
    }


    @Test
    public void unsuccessfulCreateUserInvalidPassword() {

        CreateUserRequest createUserRequest = new CreateUserRequest(userEmail, invalidField, invalidField, "user");
        Response responseCreate = postRequest("/api/auth/register", 400, createUserRequest);

        // 1. Check that the status code is 400.
        assertEquals(400, responseCreate.getStatusCode(), "Status code is not 400");

        // 2. Check that the accessToken response is empty
        assertNull(responseCreate.jsonPath().getString("accessToken"), "Access token is not empty");

        // 3. Check that the message contains the expected text
        String responseBody = responseCreate.getBody().asString();
        assertTrue(responseBody.contains("Password must contain at least 8 characters"));
        assertTrue(responseBody.contains("Password must contain letters and numbers"));



    }
    }



