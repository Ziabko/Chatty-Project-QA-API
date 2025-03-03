import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GetPostById extends BaseTest {

    @Test
    public void getPostsByID() {
        String token = loginAndGetTokenUser();

        PostRequest postRequest = new PostRequest("Get post by id", "Get post by id description", "Get post by id body", "", "", false);
        Response createPostResponse = postRequestWithToken("/api/posts", 201, postRequest, token);
        PostResponse postResponse = createPostResponse.as(PostResponse.class);
        String postId = postResponse.getId();

        Response getPostByIdResponse = getRequest("/api/posts/" + postId, 200, token);
        PostResponse getPostResponse = getPostByIdResponse.as(PostResponse.class);

        //1. Check that the id of the received post matches the id of the created post
        assertEquals(postId, getPostResponse.getId(), "The post ID doesn't match!");

        //2. Check that the title matches
        assertEquals(postRequest.getTitle(), getPostResponse.getTitle(), "The title doesn't match!");

        //3. Check that the body matches
        assertEquals(postRequest.getBody(), getPostResponse.getBody(), "The body doesn't match!");

        //4. Check that the description matches
        assertEquals(postRequest.getDescription(), getPostResponse.getDescription(), "The description doesn't match!");

        //5.  Check that created date is not empty
        assertFalse(getPostResponse.getCreatedAt().isEmpty(), "Created date field is empty");


    }


    //invalid id

}
