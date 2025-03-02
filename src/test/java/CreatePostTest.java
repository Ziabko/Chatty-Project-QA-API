import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreatePostTest extends BaseTest {

    Faker faker = new Faker();

    @Test
    public void createPost() {
        String titleRandom = faker.lorem().word();
        String descriptionRandom = faker.lorem().sentence();
        String bodyRandom = faker.lorem().paragraph();

        String token = loginAndGetTokenUser();

        PostRequest postRequest = new PostRequest(titleRandom, descriptionRandom, bodyRandom, "", "", false);
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
