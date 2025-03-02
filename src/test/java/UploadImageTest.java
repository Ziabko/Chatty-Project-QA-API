import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class UploadImageTest extends BaseTest {
//    @Test
//    public void uploadImage() {
//
//        String token = loginAndGetTokenUser();
//
//        File imageFile = new File("src/test/resources/image/smallimage.jpg");
//
//
//        Response uploadResponse = given()
//                .header("Authorization", "Bearer " + token)
//                .contentType("multipart/form-data")
//                .multiPart("image", imageFile, "image/jpg")
//                .when()
//                .log().all()
//                .post("http://chatty.telran-edu.de:8989/api/images")
//                .then()
//                .log().all()
//                .statusCode(201)
//                .extract().response();
//
//        UploadImageResponse uploadImageResponse = uploadResponse.as(UploadImageResponse.class);
//
//    }

    @Test
    public void uploadImage() {

        String token = loginAndGetTokenUser();

        File imageFile = new File("src/test/resources/image/smallimage.jpg");
        assertTrue(imageFile.exists(), "Test image file does not exist!");

        Response uploadResponse = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")                   //???????????
                .multiPart("multipartFile", imageFile, "image/jpeg")
                .when()
                .log().all()
                .post("http://chatty.telran-edu.de:8989/api/images")
                .then()
                .log().all()
                .statusCode(201)
                .extract().response();

        // 1. Check that the response contains an imageUrl
        String imageUrl = uploadResponse.jsonPath().getString("imageUrl");
        assertNotNull(imageUrl, "Image URL is null!");
        assertTrue(imageUrl.startsWith("https://chatty-images-s3.s3.eu-central-1.amazonaws.com/"),
                "Unexpected image URL format!");

        System.out.println("Uploaded image URL: " + imageUrl);
    }



}
