package pilcha.db.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pilcha.db.be.dto.BrandCategory.BrandCategoryDTO;
import pilcha.db.be.dto.BrandDTO;
import pilcha.db.be.models.Brand;
import pilcha.db.be.models.BrandCategory;
import pilcha.db.be.models.BrandImages;
import pilcha.db.be.repository.BrandRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;

    public List<BrandDTO> getAllBrands() {
        System.out.println("Fetching all brands");
        List<Brand> brands = brandRepository.findAll();
        System.out.println("Number of brands found: " + brands.size());

        return brands.stream().map(brand -> {
            BrandDTO dto = new BrandDTO();
            dto.setId(brand.getId());
            dto.setName(brand.getName());
            dto.setWebsiteUrl(brand.getWebsite_url());
            dto.setInstagramUrl(brand.getInstagram_url());
            dto.setLogoImg(brand.getLogoImg());
            dto.setCountry(brand.getCountry());

            // Obtener IDs de categorías
            List<Long> categoryIds = brand.getBrandCategories().stream()
                    .map(brandCategory -> {
                        Long categoryId = brandCategory.getCategory().getId();
                        System.out.println("Category ID found: " + categoryId);
                        return categoryId;
                    })
                    .collect(Collectors.toList());
            dto.setBrandCategoryIds(categoryIds);

            System.out.println("Brand ID has " + brand.getId() + " category IDs: " + categoryIds);
            return dto;
        }).collect(Collectors.toList());
    }
}