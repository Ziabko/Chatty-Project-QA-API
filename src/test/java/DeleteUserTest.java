import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteUserTest extends BaseTest {
    @Test
    public void successDeleteUser() {

        CreateUserRequest createUserRequest = new CreateUserRequest("user2333926e4@gmail.com", "user1234", "user1234", "user");
        Response response = postRequest("/api/auth/register", 201, createUserRequest);
        CreateUserResponse responseBody = response.as(CreateUserResponse.class);
        String token = responseBody.getAccessToken();

        Response responseUser = getRequest("/api/me", 200, token);
        UserInformationResponse userInfo = responseUser.as(UserInformationResponse.class);
        String userId = userInfo.getId();

        LoginUserRequest loginAdminRequest = new LoginUserRequest("zyablik2004@ukr.net", "AdminOlga1");
        Response response1 = postRequest("/api/auth/login", 200, loginAdminRequest);
        LoginUserResponse responseAdmin = response1.as(LoginUserResponse.class);
        String tokenAdmin = responseAdmin.getAccessToken();

        Response deleteUserResponse = deleteRequestAsAdmin("/api/users/" + userId, 204, tokenAdmin);

        //1. Check that status code is 204
        assertEquals(204, deleteUserResponse.statusCode());

    }
}
