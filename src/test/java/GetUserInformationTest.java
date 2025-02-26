import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class GetUserInformationTest extends BaseTest {

    @Test
    public void getMyUserInformation(){
        LoginUserRequest requestBody = new LoginUserRequest("z0667272624@gmail.com", "UserOlga1");
        Response response = postRequest("/api/auth/login", 200, requestBody);
        LoginUserResponse responseBody = response.as(LoginUserResponse.class);
        String token = responseBody.getAccessToken();

        Response response1 = getRequest("/api/me", 200, token);
        UserInformationResponse userInfo = response1.as(UserInformationResponse.class);

       //1. Check that fields are not empty
        assertFalse(userInfo.getId().isEmpty());
        assertFalse(userInfo.getName().isEmpty());
        assertFalse(userInfo.getSurname().isEmpty());
        assertFalse(userInfo.getPhone().isEmpty());

        //2. Check that email in the request is equal to the email in the response
        assertEquals(requestBody.getEmail(), userInfo.getEmail());

        System.out.println("User Info: " + userInfo);
    }

}
