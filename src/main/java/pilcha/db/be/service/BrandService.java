package pilcha.db.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pilcha.db.be.dto.BrandCategory.BrandCategoryDTO;
import pilcha.db.be.dto.BrandDTO;
import pilcha.db.be.models.Brand;
import pilcha.db.be.models.BrandCategory;
import pilcha.db.be.models.BrandImages;
import pilcha.db.be.repository.BrandCategoryRepository;
import pilcha.db.be.repository.BrandRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BrandService {
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private BrandCategoryRepository brandCategoryRepository;

    public List<BrandDTO> getAllBrands() {
        System.out.println("Fetching all brands");
        List<Brand> brands = brandRepository.findAll();
        System.out.println("Number of brands found: " + brands.size());

        return brands.stream().map(brand -> {
            List<BrandCategory> brandCategories = brandCategoryRepository.findByBrandId(brand.getId());

            List<Long> categoryIds = brandCategories.stream()
                    .map(bc -> bc.getCategory().getId())
                    .collect(Collectors.toList());

            List<String> categoryNames = brandCategories.stream()
                    .map(bc -> bc.getCategory().getName())
                    .collect(Collectors.toList());

            BrandDTO dto = BrandDTO.builder()
                    .id(brand.getId())
                    .name(brand.getName())
                    .websiteUrl(brand.getWebsite_url())
                    .instagramUrl(brand.getInstagram_url())
                    .logoImg(brand.getLogoImg())
                    .country(brand.getCountry())
                    .imageUrls(brand.getImageUrls().stream()
                            .map(BrandImages::getImageUrl)
                            .collect(Collectors.toList()))
                    .brandCategoryIds(categoryIds)
                    .brandCategoryNames(categoryNames)
                    .build();

            return dto;
        }).collect(Collectors.toList());
    }


    public Brand findById(Long brandId) {
        return brandRepository.findById(brandId)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada con ID: " + brandId));
    }

    public BrandDTO deleteBrand(Long brandId) {
        Brand brand = findById(brandId);

        BrandDTO brandDTO = BrandDTO.builder()
                .id(brand.getId())
                .name(brand.getName())
                .websiteUrl(brand.getWebsite_url())
                .instagramUrl(brand.getInstagram_url())
                .logoImg(brand.getLogoImg())
                .country(brand.getCountry())
                .brandCategoryIds(brandCategoryRepository.findByBrandId(brand.getId()).stream()
                        .map(bc -> bc.getCategory().getId())
                        .collect(Collectors.toList()))
                .build();

        List<BrandCategory> brandCategories = brandCategoryRepository.findByBrandId(brandId);
        for (BrandCategory brandCategory : brandCategories) {
            brandCategoryRepository.delete(brandCategory);
        }

        brandRepository.delete(brand);

        return brandDTO;
    }
}