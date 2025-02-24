import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class GetUsersListByAdmin extends BaseTest {
    @Test
    public void getUserListTest (){
        LoginUserRequest loginUserRequest = new LoginUserRequest("zyablik2004@ukr.net", "AdminOlga1");
        Response response = postRequest("/api/auth/login", 200, loginUserRequest);
        LoginUserResponse responseBody = response.as(LoginUserResponse.class);

        String token = responseBody.getAccessToken();
        Response response1 = getRequest("/api/users?skip=0&limit=5", 200, token);



        // 1. Checki that 5 users on the list
        JsonPath jsonPath = response1.jsonPath();  //Extract JSON response
        List<Map<String, Object>> users = jsonPath.getList("");   // get users list
        assertEquals(5, users.size());
    }

    }

