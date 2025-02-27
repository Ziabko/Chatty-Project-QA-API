import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.*;

public class CreateUserTest extends BaseTest {
    Faker faker = new Faker();

    @Test
    public void successCreatedUser() {

        String userEmail = faker.internet().emailAddress();
        String serPassword = faker.internet().password();

        CreateUserRequest createUserRequest = new CreateUserRequest(userEmail, serPassword, serPassword, "user");
        Response responseCreate = postRequest("/api/auth/register", 201, createUserRequest);

        CreateUserResponse createUserResponseBody = responseCreate.as(CreateUserResponse.class);

        //1. Check that Access token, refresh token and expiration are not empty
        assertFalse(createUserResponseBody.getAccessToken().isEmpty(), "Access token is empty!");
        assertFalse(createUserResponseBody.getRefreshToken().isEmpty(), "Refresh token is empty!");
        assertNotNull(createUserResponseBody.getExpiration());

    }

}
