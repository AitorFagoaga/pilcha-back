package pilcha.db.be.dto.User;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private Integer age;
    private String email;
    private LocalDateTime createdAt;
    private Boolean isPremium;
    private Boolean isBrand;
}