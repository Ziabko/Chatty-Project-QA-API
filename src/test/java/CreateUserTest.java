import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.*;

public class CreateUserTest extends BaseTest{
    @Test
    public void successCreatedUser (){
        CreateUserRequest createUserRequest = new CreateUserRequest("usertes54@gmail.com", "user1234", "user1234", "user");
        Response response = postRequest("/api/auth/register", 201, createUserRequest);

        CreateUserResponse createUserResponseBody = response.as(CreateUserResponse.class);
        assertFalse(createUserResponseBody.getAccessToken().isEmpty());
        assertFalse(createUserResponseBody.getRefreshToken().isEmpty());
//        assertTrue(createUserResponseBody.getExpiration() > 0);
        assertNotNull(createUserResponseBody.getExpiration());



        }

}
