import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateUserTest extends BaseTest {
    Faker faker = new Faker();

    String titleRandom = faker.lorem().word();
    String descriptionRandom = faker.lorem().sentence();
    String bodyRandom = faker.lorem().paragraph();
    String nameRandom = faker.lorem().word();

    @Test
    public void updateUser (){

        String token = loginAndGetTokenUser();

        Response updateUser = getRequest("/api/me", 200, token);
        UserInformationResponse userInfo = updateUser.as(UserInformationResponse.class);
        String userId = userInfo.getId();

        UserInformationRequest updateRequest = new UserInformationRequest();
        updateRequest.setName(nameRandom);
        Response responseUpdate = putRequest("/api/users/" + userId, 200, updateRequest, token);

        // 1. Check that name changed
        Response updatedUserResponse = getRequest("/api/me", 200, token);
        UserInformationResponse updatedUserInfo = updatedUserResponse.as(UserInformationResponse.class);
        assertEquals(nameRandom, updatedUserInfo.getName());

        // 2. Check that the updated email is unchanged
        assertEquals(userInfo.getEmail(), updatedUserInfo.getEmail(), "Email should not be changed!");

        // 3. Check that the user ID has not changed since the update
        assertEquals(userId, updatedUserInfo.getId(), "User ID should remain the same after update!");

        // 4. Check that the response contains the correct status code
        assertEquals(200, responseUpdate.getStatusCode(), "Unexpected status code after user update!");

    }
}
