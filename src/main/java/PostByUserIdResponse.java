import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class PostByUserIdResponse {
    private String id;
    private String title;
    private String description;
    private String body;
    private String imageUrl;
//    private String publishDate;
    private String updatedAt;
    private String createdAt;
    private Boolean draft;
//    private String userId;
private UserInformationResponse user;
}
