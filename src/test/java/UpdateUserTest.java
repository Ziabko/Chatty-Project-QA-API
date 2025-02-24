import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateUserTest extends BaseTest {
    @Test
    public void updateUser (){
        LoginUserRequest loginUserRequest = new LoginUserRequest("z0667272624@gmail.com", "UserOlga1");
        Response response = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse responseBody = response.as(LoginUserResponse.class);

        String token = responseBody.getAccessToken();

        Response updateUser = getRequest("/api/me", 200, token);
        UserInformationResponse userInfo = updateUser.as(UserInformationResponse.class);
        String userId = userInfo.getId();

        UserInformationRequest updateRequest = new UserInformationRequest();
        updateRequest.setName("Mia");
        Response responseUpdate = putRequest("/api/users/" + userId, 200, updateRequest, token);

        // 1. Check that name changed
        Response updatedUserResponse = getRequest("/api/me", 200, token);
        UserInformationResponse updatedUserInfo = updatedUserResponse.as(UserInformationResponse.class);

        assertEquals("Mia", updatedUserInfo.getName());


    }
}
