import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class DeleteUserTest extends BaseTest {
    @Test
    public void successDeleteUser() {

        CreateUserRequest createUserRequest = new CreateUserRequest("user233926e4@gmail.com", "user1234", "user1234", "user");
        Response response = postRequest("/api/auth/register", 201, createUserRequest);
        CreateUserResponse responseBody = response.as(CreateUserResponse.class);
        String token = responseBody.getAccessToken();

        Response responseUser = getRequest("/api/me", 200, token);
        UserInformationResponse userInfo = responseUser.as(UserInformationResponse.class);
        String userId = userInfo.getId();

        System.out.println("user Id = " + userId);

        LoginUserRequest loginAdminRequest = new LoginUserRequest("zyablik2004@ukr.net", "AdminOlga1");
        Response response1 = postRequest("/api/auth/login", 200, loginAdminRequest);
        LoginUserResponse responseAdmin = response1.as(LoginUserResponse.class);

        String tokenAdmin = responseAdmin.getAccessToken();
        System.out.println("Admin Token: " + tokenAdmin);

        deleteRequestAsAdmin("/api/users/" + userId, 204, tokenAdmin);

    }
}
