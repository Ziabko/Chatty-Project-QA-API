import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginUserTest extends BaseTest {
    Faker faker = new Faker();

    @Test
    public void successLoginUser() {
        LoginUserRequest loginUserRequest = new LoginUserRequest("z0667272624@gmail.com", "UserOlga1");
        Response responseLogin = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse loginUserResponse = responseLogin.as(LoginUserResponse.class);

        //1. Check that Access token, refresh token and expiration are not empty
        assertFalse(loginUserResponse.getAccessToken().isEmpty());
        assertFalse(loginUserResponse.getRefreshToken().isEmpty());
        assertNotNull(loginUserResponse.getExpiration());
    }

    @Test
    public void successLoginAdmin() {
        LoginUserRequest loginUserRequest = new LoginUserRequest("zyablik2004@ukr.net", "AdminOlga1");
        Response responseLogin = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse loginUserResponse = responseLogin.as(LoginUserResponse.class);

        //1. Check that Access token, refresh token and expiration are not empty
        assertFalse(loginUserResponse.getAccessToken().isEmpty());
        assertFalse(loginUserResponse.getRefreshToken().isEmpty());
        assertNotNull(loginUserResponse.getExpiration());
    }

    @Test
    public void loginUserWithInvalidPassword() {
        LoginUserRequest loginUserRequest = new LoginUserRequest("z0667272624@gmail.com", "WrongPassword123");
        Response responseLogin = postRequest("/api/auth/login", 401, loginUserRequest);

        //1. Check that
        String errorMessage = responseLogin.jsonPath().getString("message");
        assertEquals("The password does not match the one saved in the database!", errorMessage, "Unexpected error message");
    }

    @Test
    public void invalidSuccessLoginUserEmailEmpty() {
        String email = "";
        String password = faker.internet().password();

        LoginUserRequest loginUserRequest = new LoginUserRequest(email, password);
        Response responseLogin = postRequest("/api/auth/login", 400, loginUserRequest);

        assertEquals(400, responseLogin.getStatusCode());
        String responseBody = responseLogin.getBody().asString();
        assertTrue(responseBody.contains("Email cannot be empty"));
        assertTrue(responseBody.contains("Invalid email format"));
    }
}
