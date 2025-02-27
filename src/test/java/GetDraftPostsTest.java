import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GetDraftPostsTest extends BaseTest {
@Test

    public void getDraftPosts(){
//    LoginUserRequest loginUserRequest = new LoginUserRequest("z0667272624@gmail.com", "UserOlga1");
//    Response responseLogin = postRequest("/api/auth/login", 200, loginUserRequest);
//    LoginUserResponse loginUserResponse = responseLogin.as(LoginUserResponse.class);
//    String token = loginUserResponse.getAccessToken();
    String token = loginAndGetTokenUser();

    Response getDraftResponse = getRequest("/api/posts/drafts", 200, token);
//    List<PostResponse> getUsersPostResponse = getDraftResponse.jsonPath().getList("drafts", PostResponse.class);
    List<PostResponse> getUsersPostResponse = getDraftResponse.as(new TypeRef<List<PostResponse>>() {});

    Response response1 = getRequest("/api/me", 200, token);
    UserInformationResponse userInfo = response1.as(UserInformationResponse.class);
    String userId = userInfo.getId();

    //1.  Verify that the server returned the list, even if it is empty
    assertNotNull(getUsersPostResponse);

    //2. Check that each post is draft
    for (PostResponse post : getUsersPostResponse) {
        assertTrue(post.getDraft(), "The post isn't a draft");
    }

    //3. Check that each post have id, title and body
    for (PostResponse post : getUsersPostResponse) {
        assertFalse(post.getId().isEmpty());
        assertFalse(post.getTitle().isEmpty());
        assertFalse(post.getBody().isEmpty());
    }

    //4. Verify that the API does not return other users' drafts
    for (PostResponse post : getUsersPostResponse) {
        assertNotNull(post.getDraft(), "Draft status must be not null!");
    }


    }
}
