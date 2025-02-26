import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreatePostTest extends BaseTest {
    @Test
    public void createPost() {
        LoginUserRequest loginUserRequest = new LoginUserRequest("z0667272624@gmail.com", "UserOlga1");
        Response response = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse responseBodyLogin = response.as(LoginUserResponse.class);
        String token = responseBodyLogin.getAccessToken();

        PostRequest postRequest = new PostRequest("Test title1", "Test description1", "Test body", "", "", false);
        Response createPostResponse = postRequestWithToken("/api/posts", 201, postRequest, token);

        PostResponse postResponse = createPostResponse.as(PostResponse.class);

        //1. Check that title are equal (description and body too)
        assertEquals(postRequest.getTitle(), postResponse.getTitle());
        assertEquals(postRequest.getDescription(), postResponse.getDescription());
        assertEquals(postRequest.getBody(), postResponse.getBody());

        //2. Check that created post have id
        assertFalse(postResponse.getId().isEmpty());

        //3. Check that created post have ussr id
        assertFalse(postResponse.getUserId().isEmpty());
    }
}
