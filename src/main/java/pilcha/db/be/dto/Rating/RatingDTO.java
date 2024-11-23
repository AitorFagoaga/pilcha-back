package pilcha.db.be.dto.Rating;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDTO {
    private Long id;
    private Integer rating;
    private String comment;
    private Integer price;
    private Integer qualityRating;
    private Long brandId;
    private Long userId;
}
