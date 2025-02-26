import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GetAllPostsTest extends BaseTest {

    @Test
    public void getAllPosts() {
        LoginUserRequest loginUserRequest = new LoginUserRequest("z0667272624@gmail.com", "UserOlga1");
        Response responseLogin = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse loginUserResponse = responseLogin.as(LoginUserResponse.class);
        String token = loginUserResponse.getAccessToken();

        int postsNumber = 3;
        Response allPostsResponse = getRequest("/api/posts?skip=0&limit=" + postsNumber, 200, token);
//        List<PostResponse> getUsersPostResponse = allPostsResponse.jsonPath().getList("posts", PostResponse.class);
        List<PostResponse> getUsersPostResponse = allPostsResponse.as(new TypeRef<List<PostResponse>>() {});


        //1. Check that list is not empty
        assertFalse(getUsersPostResponse.isEmpty(), "The post list is empty!");

        //2. Check that the number of posts <= postsNumber
        assertTrue(getUsersPostResponse.size() <= postsNumber, "Posts number is more than the specified");

        //3. Check that each post has a title
        for (PostResponse post : getUsersPostResponse) {
            assertFalse(post.getTitle().isEmpty(), "Post title must not be empty");
        }

        //4. Check that each post has a description
        for (PostResponse post : getUsersPostResponse) {
            assertFalse(post.getDescription().isEmpty(), "Post Description must not be empty");
        }

        //5. Check that each post has a body
        for (PostResponse post : getUsersPostResponse) {
            assertFalse(post.getBody().isEmpty(), "Post Body must not be empty");
        }
    }
}
