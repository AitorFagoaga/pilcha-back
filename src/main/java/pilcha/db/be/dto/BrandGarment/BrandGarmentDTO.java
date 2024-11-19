package pilcha.db.be.dto.BrandGarment;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BrandGarmentDTO {
    private String name;
    private String description;
    private String image_url;

    private List<Long> brandId;
}
