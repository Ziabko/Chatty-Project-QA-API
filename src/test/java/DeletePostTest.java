import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeletePostTest extends BaseTest {

    Faker faker = new Faker();

    @Test
    public void successfulDeletePost() {
        String titleRandom = faker.lorem().word();
        String descriptionRandom = faker.lorem().sentence();
        String bodyRandom = faker.lorem().paragraph();

        String token = loginAndGetTokenUser();

        PostRequest postRequest = new PostRequest(titleRandom, descriptionRandom, bodyRandom, "", "", false);
        Response createPostResponse = postRequestWithToken("/api/posts", 201, postRequest, token);

        PostResponse postResponse = createPostResponse.as(PostResponse.class);
        String postId = postResponse.getId();
        Response deletePostResponse = deleteRequest("/api/posts/" + postId, 204, token);

        //1. Check that status code is 204
        assertEquals(204, deletePostResponse.statusCode());
    }

    @Test
    public void unsuccessfulDeletePostInvalidId() {
        String token = loginAndGetTokenUser();
        String InvalidId = UUID.randomUUID().toString();

        Response deletePostResponse = deleteRequest("/api/posts/" + InvalidId, 404, token);

        //1. Check error message
        String responseBody = deletePostResponse.getBody().asString();
        assertTrue(responseBody.contains("Post not found!"));

    }
}
