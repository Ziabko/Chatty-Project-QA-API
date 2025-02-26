import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class GetAllPostsTest extends BaseTest{
    @Test
    public void getAllPosts(){
        LoginUserRequest loginUserRequest = new LoginUserRequest("z0667272624@gmail.com", "UserOlga1");
        Response responseLogin = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse loginUserResponse = responseLogin.as(LoginUserResponse.class);
        String token = loginUserResponse.getAccessToken();

        Response allPostsResponse = getRequest("/api/posts?skip=0&limit=10", 200, token);
        PostResponse postResponse = allPostsResponse.as(PostResponse.class);


        // ???????????? что проверить????
    }
}
