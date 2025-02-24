import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.*;

public class CreateUserTest extends BaseTest{
    @Test
    public void successCreatedUser (){
        CreateUserRequest createUserRequest = new CreateUserRequest("usertes4154@gmail.com", "user1234", "user1234", "user");
        Response response = postRequest("/api/auth/register", 201, createUserRequest);

        CreateUserResponse createUserResponseBody = response.as(CreateUserResponse.class);

        //1. Check that Access token, refresh token and expiration are not empty
        assertFalse(createUserResponseBody.getAccessToken().isEmpty());
        assertFalse(createUserResponseBody.getRefreshToken().isEmpty());
        assertNotNull(createUserResponseBody.getExpiration());

        }

}
