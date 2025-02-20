import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class PostResponse {
    private String title;
    private String description;
    private String body;
    private String imageUrl;
    private String publishDate;
    private Boolean draft;

}
