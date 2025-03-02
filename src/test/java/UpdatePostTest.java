import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UpdatePostTest extends BaseTest {
    Faker faker = new Faker();
    String titleRandom = faker.lorem().word();
    String descriptionRandom = faker.lorem().sentence();
    String bodyRandom = faker.lorem().paragraph();
    String wordRandom = faker.lorem().word();

    @Test
    public void updatePost(){

        String token = loginAndGetTokenUser();

        PostRequest postRequest = new PostRequest(titleRandom, descriptionRandom, bodyRandom, "", "", false);
        Response createPostResponse = postRequestWithToken("/api/posts", 201, postRequest, token);
        PostResponse postResponse = createPostResponse.as(PostResponse.class);
        String postId = postResponse.getId();

        PostRequest updatePost = new PostRequest(wordRandom + titleRandom, wordRandom + descriptionRandom, wordRandom + bodyRandom, "", "", false);// если не хочу менять Title????????????
        Response updatePostResponse = putRequest("/api/posts/" + postId,200, updatePost, token);
        PostResponse postResponse1 = updatePostResponse.as(PostResponse.class);

        //1. Check that Title, Description and Body in request is not equal Title, Description and Body in response
        assertNotEquals(postResponse.getTitle(), postResponse1.getTitle(), "Titles are equal!");
        assertNotEquals(postResponse.getDescription(), postResponse1.getDescription(), "Description was not updated");
        assertNotEquals(postResponse.getBody(), postResponse1.getBody(), "Body was not updated");

        // 2. Check that the post ID hasn't changed after the update
        assertEquals(postResponse.getId(), postResponse1.getId(), "Post ID should remain the same after update");
    }

}

