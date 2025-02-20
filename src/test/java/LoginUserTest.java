import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginUserTest extends BaseTest {

    @Test
    public void successLoginUser (){
        LoginUserRequest loginUserRequest = new LoginUserRequest("z0667272624@gmail.com", "UserOlga1");
        Response response = postRequest("/api/auth/login", 200, loginUserRequest);

//      CreateUserResponse responseBody = response.as(CreateUserResponse.class);
        LoginUserResponse responseBodyLogin = response.as(LoginUserResponse.class);
//        String token = responseBodyLogin.getAccessToken();
        assertFalse(responseBodyLogin.getAccessToken().isEmpty());

    }
}
