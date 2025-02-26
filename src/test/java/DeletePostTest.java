import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeletePostTest extends BaseTest {

    @Test
    public void deletePost() {
        LoginUserRequest loginUserRequest = new LoginUserRequest("z0667272624@gmail.com", "UserOlga1");
        Response response = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse responseBodyLogin = response.as(LoginUserResponse.class);
        String token = responseBodyLogin.getAccessToken();

        PostRequest postRequest = new PostRequest("Test title2", "Test description2", "Test body", "", "", false);
        Response createPostResponse = postRequestWithToken("/api/posts", 201, postRequest, token);

        PostResponse postResponse = createPostResponse.as(PostResponse.class);
        String postId = postResponse.getId();
        Response deletePostResponse = deleteRequest("/api/posts/" + postId, 204, token);

        //1. Check that status code is 204
        assertEquals(204, deletePostResponse.statusCode());


    }
}
