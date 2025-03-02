import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreatePostTest extends BaseTest {
    Faker faker = new Faker();
    String titleRandom = faker.lorem().word();
    String descriptionRandom = faker.lorem().sentence();
    String bodyRandom = faker.lorem().paragraph();


    @Test
    public void createPost() {
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

    @Test
    public void createPostWithoutTitle() {
        String token = loginAndGetTokenUser();
        PostRequest postRequest = new PostRequest("", descriptionRandom, bodyRandom, "", "", false);
        Response createPostResponse = postRequestWithToken("/api/posts", 400, postRequest, token);

        //1. Check that status code is 400
        assertEquals(400, createPostResponse.getStatusCode(), "Unexpected status code. It must be 400 by empty title");
        //2. Check error message
        String responseBody = createPostResponse.getBody().asString();
        assertTrue(responseBody.contains("Title can not be empty!"));

    }

    @Test
    public void createPostWithoutDescription() {
        String token = loginAndGetTokenUser();
        PostRequest postRequest = new PostRequest(titleRandom, "", bodyRandom, "", "", false);
        Response createPostResponse = postRequestWithToken("/api/posts", 400, postRequest, token);

        //1. Check that status code is 400
        assertEquals(400, createPostResponse.getStatusCode(), "Unexpected status code. It must be 400 by empty Description");
        //2. Check error message
        String responseBody = createPostResponse.getBody().asString();
        assertTrue(responseBody.contains("Description can not be empty!"));

    }

    @Test
    public void createPostWithoutBody() {
        String token = loginAndGetTokenUser();
        PostRequest postRequest = new PostRequest(titleRandom, descriptionRandom, "", "", "", false);
        Response createPostResponse = postRequestWithToken("/api/posts", 400, postRequest, token);

        //1. Check that status code is 400
        assertEquals(400, createPostResponse.getStatusCode(), "Unexpected status code. It must be 400 by empty body.");
        //2. Check error message
        String responseBody = createPostResponse.getBody().asString();
        assertTrue(responseBody.contains("Body can not be empty!"));


    }

    @Test
    public void createPostWithLongTitle() {
        String token = loginAndGetTokenUser();
        String longTitle = "T".repeat(41);
        PostRequest postRequest = new PostRequest(longTitle, descriptionRandom, bodyRandom, "", "", false);
        Response createPostResponse = postRequestWithToken("/api/posts", 400, postRequest, token);

        //1. Check that status code is 400
        assertEquals(400, createPostResponse.getStatusCode(), "Unexpected status code. It must be 400 by long title.");
        //2. Check error message
        String responseBody = createPostResponse.getBody().asString();
        assertTrue(responseBody.contains("Title must contain from 1 to 40 characters"));

    }

    @Test
    public void createPostWithLongDescription() {
        String token = loginAndGetTokenUser();
        String longDescription = "D".repeat(101);
        PostRequest postRequest = new PostRequest(titleRandom, "", bodyRandom, "", "", false);
        Response createPostResponse = postRequestWithToken("/api/posts", 400, postRequest, token);

        //1. Check that status code is 400
        assertEquals(400, createPostResponse.getStatusCode(), "Unexpected status code. It must be 400 by long Description.");
        //2. Check error message
        String responseBody = createPostResponse.getBody().asString();
        assertTrue(responseBody.contains("Description must contain from 1 to 100 characters"));
    }

    @Test
    public void createPostWithLongBody() {
        String token = loginAndGetTokenUser();
        String longBody = "D".repeat(1001);
        PostRequest postRequest = new PostRequest(titleRandom, descriptionRandom, longBody, "", "", false);
        Response createPostResponse = postRequestWithToken("/api/posts", 400, postRequest, token);

        //1. Check that status code is 400
        assertEquals(400, createPostResponse.getStatusCode(), "Unexpected status code. It must be 400 by long body.");
        //2. Check error message
        String responseBody = createPostResponse.getBody().asString();
        assertTrue(responseBody.contains("Body must contain from 1 to 1000 characters"));
    }

}
