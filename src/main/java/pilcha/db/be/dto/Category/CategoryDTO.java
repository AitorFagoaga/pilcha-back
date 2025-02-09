package pilcha.db.be.dto.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pilcha.db.be.dto.BrandCategory.BrandCategoryDTO;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class CategoryDTO {
    private Long id;
    private String name;
    private String image_url;
    private Set<BrandCategoryDTO> brandCategory;
    private List<Long> brandCategoryIds;
    private List<String> brandCategoryNames;

}
