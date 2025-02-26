import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class GetPostByUserTest extends BaseTest {
    @Test
    public void getPostByUser(){
        LoginUserRequest loginUserRequest = new LoginUserRequest("z0667272624@gmail.com", "UserOlga1");
        Response responseLogin = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse loginUserResponse = responseLogin.as(LoginUserResponse.class);
        String token = loginUserResponse.getAccessToken();

        Response response1 = getRequest("/api/me", 200, token);
        UserInformationResponse userInfo = response1.as(UserInformationResponse.class);
        String userId = userInfo.getId();

        Response getUsersPost = getRequest("/api/users/" + userId + "/posts?skip=0&limit=10", 200, token);

        /// что проверить??

    }
}
