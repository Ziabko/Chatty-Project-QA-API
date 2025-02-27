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

        //1. Check that titles are equal (description and body too)
        assertEquals(postRequest.getTitle(), postResponse.getTitle(), "Titles are not equal");
        assertEquals(postRequest.getDescription(), postResponse.getDescription(),"Descriptions are not equal");
        assertEquals(postRequest.getBody(), postResponse.getBody(), "Body are not equal");

        //2. Check that created post have id
        assertFalse(postResponse.getId().isEmpty(), "Created post doesn't have id");

        //3. Check that created post have user id
        assertFalse(postResponse.getUserId().isEmpty(), "Created post doesn't have userId");
    }
}
