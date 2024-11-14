package pilcha.db.be.dto;

import lombok.Data;
import pilcha.db.be.models.BrandImages;

import java.util.List;

@Data
public class BrandDTO {
    private Long id;
    private String name;
    private String websiteUrl;
    private String instagramUrl;
    private String logoImg;
    private List<String> imageUrls;
    private String country;
    private List<Long> brandCategoryIds;
}