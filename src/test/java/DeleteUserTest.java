import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteUserTest extends BaseTest {

    @Test
    public void successDeleteUser() {
        String token = createRandomUser();

        Response responseUser = getRequest("/api/me", 200, token);
        UserInformationResponse userInfo = responseUser.as(UserInformationResponse.class);
        String userId = userInfo.getId();
        String tokenAdmin = loginAndGetTokenAdmin();
        Response deleteUserResponse = deleteRequestAsAdmin("/api/users/" + userId, 204, tokenAdmin);

        //1. Check that status code is 204
        assertEquals(204, deleteUserResponse.statusCode(), "Unexpected status code. User has not been deleted");
    }

    @Test
    public void unsuccessfulDeleteUserInvalidId() {
        String tokenAdmin = loginAndGetTokenAdmin();

        String invalidUserId = UUID.randomUUID().toString();
        Response deleteResponse = deleteRequestAsAdmin("/api/users/" + invalidUserId, 400, tokenAdmin);

    }
}
