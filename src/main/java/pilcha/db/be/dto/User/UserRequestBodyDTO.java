package pilcha.db.be.dto.User;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserRequestBodyDTO {
    private String username;
    private String email;
    private Integer age;
    private String password;
    private LocalDateTime createdAt;
    private Boolean isPremium;
    private Boolean isBrand;
}