import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class LoginUserTest extends BaseTest {

    @Test
    public void successLoginUser (){
        LoginUserRequest requestBody = new LoginUserRequest("usertest3@gmail.com", "user1234");
        Response response = postRequest("/api/auth/login", 200, requestBody);

        CreateUserResponse responseBody = response.as(CreateUserResponse.class);
    }
}
