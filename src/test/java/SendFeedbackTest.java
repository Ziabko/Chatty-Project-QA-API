import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SendFeedbackTest extends BaseTest {

    Faker faker = new Faker();
    String userEmail = faker.internet().emailAddress();
    String content = faker.lorem().paragraph();
    String name = faker.lorem().word();

    @Test
    public void sendFeedback (){
//        String token = loginAndGetTokenUser();

        SendFeedbackRequest sendFeedbackRequest = new SendFeedbackRequest(name, userEmail, content);
        Response response1 = postRequest("/api/feedback", 201, sendFeedbackRequest);

        SendFeedbackRequest feedbackRequest = response1.as(SendFeedbackRequest.class);
        SendFeedbackResponse feedbackResponse = response1.as(SendFeedbackResponse.class);

        // 1. Check email in request equals email in response
        assertEquals(feedbackRequest.getEmail(), feedbackResponse.getEmail());

        // 2. Check that content is not empty
        assertFalse(feedbackResponse.getContent().isEmpty());

        //3. check that without login should be not feedback
        assertEquals(401, response1.getStatusCode(), "Feedback should not be allowed without authentication!");
    }
}
