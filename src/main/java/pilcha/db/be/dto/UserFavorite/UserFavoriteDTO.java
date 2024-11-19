package pilcha.db.be.dto.UserFavorite;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserFavoriteDTO {
    private Long brandId;
    private Long userId;
}
