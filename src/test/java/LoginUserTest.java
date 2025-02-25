import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginUserTest extends BaseTest {

    @Test
    public void successLoginUser (){
        LoginUserRequest loginUserRequest = new LoginUserRequest("z0667272624@gmail.com", "UserOlga1");
        Response responseLogin = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse loginUserResponse = responseLogin.as(LoginUserResponse.class);

        //1. Check that Access token, refresh token and expiration are not empty
        assertFalse(loginUserResponse.getAccessToken().isEmpty());
        assertFalse(loginUserResponse.getRefreshToken().isEmpty());
        assertNotNull(loginUserResponse.getExpiration());

    }
}
