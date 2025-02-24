import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginUserTest extends BaseTest {

    @Test
    public void successLoginUser (){
        LoginUserRequest loginUserRequest = new LoginUserRequest("z0667272624@gmail.com", "UserOlga1");
        Response response = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse responseBodyLogin = response.as(LoginUserResponse.class);

        //1. Check that Access token, refresh token and expiration are not empty
        assertFalse(responseBodyLogin.getAccessToken().isEmpty());
        assertFalse(responseBodyLogin.getRefreshToken().isEmpty());
        assertNotNull(responseBodyLogin.getExpiration());

    }
}
