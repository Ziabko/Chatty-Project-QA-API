import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GetPostByUserTest extends BaseTest {
    @Test
    public void getPostByUser() {
//        LoginUserRequest loginUserRequest = new LoginUserRequest("z0667272624@gmail.com", "UserOlga1");
//        Response responseLogin = postRequest("/api/auth/login", 200, loginUserRequest);
//        LoginUserResponse loginUserResponse = responseLogin.as(LoginUserResponse.class);
//        String token = loginUserResponse.getAccessToken();

        String token = loginAndGetTokenUser();

        Response response1 = getRequest("/api/me", 200, token);
        UserInformationResponse userInfo = response1.as(UserInformationResponse.class);
        String userId = userInfo.getId();
        int limitSize = 5;

        Response getUsersPost = getRequest("/api/users/" + userId + "/posts?skip=0&limit=" + limitSize, 200, token);

        //1. Check that array of posts in the response
        List<PostByUserIdResponse> posts = getUsersPost.jsonPath().getList(".", PostByUserIdResponse.class);
        assertNotNull(posts, "Response body is null");

        //2. Check that the userId inside the post matches the userId from request
        for (PostByUserIdResponse post : posts) {
            assertNotNull(post.getUser(), "Post does not contain user information");
            assertEquals(userId, post.getUser().getId(), "Post does not belong to the user");
        }

        //3. Check that the post has an ID, title and created date
        for (PostByUserIdResponse post : posts) {
            assertNotNull(post.getId(), "Post ID is null");
            assertFalse(post.getId().isEmpty(), "Post ID is empty");

            assertNotNull(post.getTitle(), "Post title is null");
            assertFalse(post.getTitle().isEmpty(), "Post title is empty");

            assertNotNull(post.getCreatedAt(), "Post createdAt is null");
            assertFalse(post.getCreatedAt().isEmpty(), "Post createdAt is empty");

            //4.  Check that the posts are sorted by descending order createdAt
            for (int i = 1; i < posts.size(); i++) {
                assertTrue(posts.get(i - 1).getCreatedAt().compareTo(posts.get(i).getCreatedAt()) >= 0,
                        "Posts are not sorted by createdAt in descending order");
            }

            //5. Check that the number of posts does not exceed the limit
            assertTrue(posts.size() <= limitSize, "Number of posts exceeds the limit");
        }


    }
}
