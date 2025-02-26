import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class UpdatePostTest extends BaseTest {
    @Test
    public void updatePost(){
        LoginUserRequest loginUserRequest = new LoginUserRequest("z0667272624@gmail.com", "UserOlga1");
        Response response = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse responseBodyLogin = response.as(LoginUserResponse.class);
        String token = responseBodyLogin.getAccessToken();

        PostRequest postRequest = new PostRequest("Test title1", "Test description1", "Test body", "", "", false);
        Response createPostResponse = postRequestWithToken("/api/posts", 201, postRequest, token);

        PostResponse postResponse = createPostResponse.as(PostResponse.class);
        String postId = postResponse.getId();

        PostRequest updatePost = new PostRequest("New Title", "New Title", "New Title", "", "", false);// если не хочу менять Title????????????

        Response updatePostResponse = putRequest("/api/posts/" + postId,200, updatePost, token);

    }
}
