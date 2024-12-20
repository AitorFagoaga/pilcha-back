package pilcha.db.be.dto.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {
    private String token;
    private String id;
    private String email;
    private Integer age;
    private String username;
    private boolean isPremium;
    private boolean isBrand;
}
