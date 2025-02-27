import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeletePostTest extends BaseTest {

    Faker faker = new Faker();

    @Test
    public void deletePost() {

        String titleRandom = faker.lorem().word();
        String descriptionRandom = faker.lorem().sentence();
        String bodyRandom = faker.lorem().paragraph();

//        LoginUserRequest loginUserRequest = new LoginUserRequest("z0667272624@gmail.com", "UserOlga1");
//        Response response = postRequest("/api/auth/login", 200, loginUserRequest);
//        LoginUserResponse responseBodyLogin = response.as(LoginUserResponse.class);
//        String token = responseBodyLogin.getAccessToken();
        String token = loginAndGetTokenUser();

        PostRequest postRequest = new PostRequest(titleRandom, descriptionRandom, bodyRandom, "", "", false);
        Response createPostResponse = postRequestWithToken("/api/posts", 201, postRequest, token);

        PostResponse postResponse = createPostResponse.as(PostResponse.class);
        String postId = postResponse.getId();
        Response deletePostResponse = deleteRequest("/api/posts/" + postId, 204, token);

        //1. Check that status code is 204
        assertEquals(204, deletePostResponse.statusCode());


    }
}
