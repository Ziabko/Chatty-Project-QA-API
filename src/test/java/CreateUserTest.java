import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateUserTest extends BaseTest{
    @Test
    public void successCreatedUser (){
        CreateUserRequest requestBody = new CreateUserRequest("usertest3@gmail.com", "user1234", "user1234", "user");
        Response response = postRequest("/api/auth/register", 201, requestBody);

        SuccessfulCreateUserResponse responseBody = response.as(SuccessfulCreateUserResponse.class);
    }

}
