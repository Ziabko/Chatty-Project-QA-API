import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.hamcrest.core.IsNull.notNullValue;

public class UpdatePasswordTest extends BaseTest {

    @Test
    public void updatePassword (){
        String oldPassword = "UserOlga2";
        String newPassword = "UserOlga1";
//        LoginUserRequest loginUserRequest = new LoginUserRequest("z0667272624@gmail.com", oldPassword);
//        Response response = postRequest("/api/auth/login", 200, loginUserRequest);
//        LoginUserResponse responseBodyLogin = response.as(LoginUserResponse.class);
//        String token = responseBodyLogin.getAccessToken();
        String token = loginAndGetTokenUser();

        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest(oldPassword, newPassword, newPassword);
        Response updatePasswordResponse = putRequest("/api/user/password/update", 200, updatePasswordRequest, token);

        // 1. Login verification with new password
        LoginUserRequest newPasswordLogin = new LoginUserRequest("z0667272624@gmail.com", newPassword);
        Response newLoginResponse = postRequest("/api/auth/login", 200, newPasswordLogin);

        // 2. Check that a new token has been received
        newLoginResponse.then().body("accessToken", notNullValue());


    }

}
