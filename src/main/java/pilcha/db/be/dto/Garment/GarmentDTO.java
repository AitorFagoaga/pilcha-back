package pilcha.db.be.dto.Garment;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GarmentDTO {
    private Long id;
    private String name;
    private String description;
    private String image_url;
    private List<Long> brandIds;
    private List<String> brandNames;
}
