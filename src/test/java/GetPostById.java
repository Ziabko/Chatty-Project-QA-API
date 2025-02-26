import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class GetPostById extends BaseTest{

    @Test
    public void getPostsByID (){
        LoginUserRequest loginUserRequest = new LoginUserRequest("z0667272624@gmail.com", "UserOlga1");
        Response responseLogin = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse loginUserResponse = responseLogin.as(LoginUserResponse.class);
        String token = loginUserResponse.getAccessToken();

        PostRequest postRequest = new PostRequest("Get post by id", "Get post by id description", "Get post by id body", "", "", false);
        Response createPostResponse = postRequestWithToken("/api/posts", 201, postRequest, token);
        PostResponse postResponse = createPostResponse.as(PostResponse.class);
        String postId = postResponse.getId();

        Response getPostByIdResponse = getRequest("/api/posts/"+ postId, 200, token);




    }

}
