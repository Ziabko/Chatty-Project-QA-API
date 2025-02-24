import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SendFeedbackTest extends BaseTest {

    @Test
    public void sendFeedback (){
//        LoginUserRequest loginUserRequest = new LoginUserRequest("z0667272624@gmail.com", "UserOlga1");
//        Response response = postRequest("/api/auth/login", 200, loginUserRequest);
//        LoginUserResponse responseBodyLogin = response.as(LoginUserResponse.class);
//        String token = responseBodyLogin.getAccessToken();

        SendFeedbackRequest sendFeedbackRequest = new SendFeedbackRequest("Leo", "z0667272624@gmail.com", "Testing");
        Response response1 = postRequest("/api/feedback", 201, sendFeedbackRequest);

        SendFeedbackRequest feedbackRequest = response1.as(SendFeedbackRequest.class);
        SendFeedbackResponse feedbackResponse = response1.as(SendFeedbackResponse.class);

        //check that without login should be not feedback

        // 1. Check email in request equals email in response
        assertEquals(feedbackRequest.getEmail(), feedbackResponse.getEmail());
        // 2. Check that content is not empty
        assertFalse(feedbackResponse.getContent().isEmpty());


    }
}
