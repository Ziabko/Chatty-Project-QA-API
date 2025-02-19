import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SuccessfulLoginResponse {

    private String accessToken;
    private String refreshToken;
    private long expiration;
}
