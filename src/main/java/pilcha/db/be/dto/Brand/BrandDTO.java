package pilcha.db.be.dto.Brand;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BrandDTO {
    private Long id;
    private String name;
    private String description;
    private String websiteUrl;
    private String instagramUrl;
    private String logoImg;
    private List<String> imageUrls;
    private String country;
    private List<Long> brandCategoryIds;
    private List<String> brandCategoryNames;
}