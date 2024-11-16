package pilcha.db.be.dto.BrandCategory;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class BrandCategoryDTO {
    private String brandName;
    private String websiteUrl;
    private String instagramUrl;
    private String logoImg;
    private List<String> imageUrls;
    private String country;

    private List<Long> existingCategoryId;
    private List<String> newCategoryName;
    private List<String> newCategoryImageUrl;
}

