import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UploadImageTest extends BaseTest {
    @Test
    public void uploadImage() {

//        LoginUserRequest loginUserRequest = new LoginUserRequest("z0667272624@gmail.com", "UserOlga1");
//        Response response = postRequest("/api/auth/login", 200, loginUserRequest);
//        LoginUserResponse responseBodyLogin = response.as(LoginUserResponse.class);
//        String token = responseBodyLogin.getAccessToken();

        String token = loginAndGetTokenUser();

        File imageFile = new File("src/test/resources/image/smallimage.jpg");


        Response uploadResponse = given()
                .header("Authorization", "Bearer " + token)
                .contentType("multipart/form-data")
                .multiPart("image", imageFile, "image/jpg")
                .when()
                .log().all()
                .post("http://chatty.telran-edu.de:8989/api/images")
                .then()
                .log().all()
                .statusCode(201)
                .extract().response();

        UploadImageResponse uploadImageResponse = uploadResponse.as(UploadImageResponse.class);

    }

}
