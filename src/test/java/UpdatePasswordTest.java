import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UpdatePasswordTest extends BaseTest {

    Faker faker = new Faker();
    String userEmail = faker.internet().emailAddress();
    String userPassword = faker.internet().password();
    String userNewPassword = faker.internet().password();
    String invalidField = faker.lorem().word();

    @Test
    public void updatePassword() {
        CreateUserRequest createUserRequest = new CreateUserRequest(userEmail, userPassword, userPassword, "user");
        Response responseCreate = postRequest("/api/auth/register", 201, createUserRequest);
        CreateUserResponse createUserResponseBody = responseCreate.as(CreateUserResponse.class);

        LoginUserRequest loginUserRequest = new LoginUserRequest(userEmail, userPassword);
        Response responseLogin = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse loginUserResponse = responseLogin.as(LoginUserResponse.class);
        String token = loginUserResponse.getAccessToken();

        UpdatePasswordRequest updatePasswordRequest = new UpdatePasswordRequest(userPassword, userNewPassword, userNewPassword);
        Response updatePasswordResponse = putRequest("/api/user/password/update", 200, updatePasswordRequest, token);

        // 1. Login verification with new password
        LoginUserRequest newPasswordLogin = new LoginUserRequest(userEmail, userNewPassword);
        Response newLoginResponse = postRequest("/api/auth/login", 200, newPasswordLogin);
        LoginUserResponse newLoginUserResponse = responseLogin.as(LoginUserResponse.class);
        assertFalse(newLoginUserResponse.getAccessToken().isEmpty());
        assertFalse(newLoginUserResponse.getRefreshToken().isEmpty());
        assertNotNull(newLoginUserResponse.getExpiration());

    }

}
