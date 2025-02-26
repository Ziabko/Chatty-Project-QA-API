import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class GetDraftPostsTest extends BaseTest {
@Test

    public void getDraftPosts(){
    LoginUserRequest loginUserRequest = new LoginUserRequest("z0667272624@gmail.com", "UserOlga1");
    Response responseLogin = postRequest("/api/auth/login", 200, loginUserRequest);
    LoginUserResponse loginUserResponse = responseLogin.as(LoginUserResponse.class);
    String token = loginUserResponse.getAccessToken();

    Response getDraftResponse = getRequest("/api/posts/drafts", 200, token);
    PostResponse postResponse = getDraftResponse.as(PostResponse.class);



}
}
