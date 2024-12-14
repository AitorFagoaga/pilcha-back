package pilcha.db.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pilcha.db.be.dto.BrandCategory.BrandCategoryDTO;
import pilcha.db.be.models.Brand;
import pilcha.db.be.models.Category;
import pilcha.db.be.models.BrandCategory;
import pilcha.db.be.models.Post;
import pilcha.db.be.repository.BrandCategoryRepository;
import pilcha.db.be.repository.BrandRepository;
import pilcha.db.be.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BrandCategoryService {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandCategoryRepository brandCategoryRepository;

    public List<BrandCategory> getBrandCategoriesByCategoryId(Long categoryId) {
        return brandCategoryRepository.findByCategoryId(categoryId);
    }

    public List<Category> getCategoriesByBrandId(Long brandId) {
        List<BrandCategory> brandCategories = brandCategoryRepository.findByBrandId(brandId);

        return brandCategories.stream()
                .map(BrandCategory::getCategory)
                .collect(Collectors.toList());
    }

    public List<BrandCategory> createBrandWithCategory(BrandCategoryDTO dto) {
        Brand brand = new Brand();
        brand.setName(dto.getBrandName());
        brand.setWebsite_url(dto.getWebsiteUrl());
        brand.setInstagram_url(dto.getInstagramUrl());
        brand.setLogoImg(dto.getLogoImg());
        brand.setCountry(dto.getCountry());

        for (String imageUrl : dto.getImageUrls()) {
            Post brandImageUrl = new Post();
            brandImageUrl.setImageUrl(imageUrl);
            brandImageUrl.setBrand(brand);
            brand.getImageUrls().add(brandImageUrl);
        }

        brandRepository.save(brand);

        List<BrandCategory> brandCategories = new ArrayList<>();

        if (dto.getExistingCategoryId() != null) {
            for (Long existingCategoryId : dto.getExistingCategoryId()) {
                Optional<Category> existingCategory = categoryRepository.findById(existingCategoryId);
                if (existingCategory.isPresent()) {
                    BrandCategory brandCategory = new BrandCategory();
                    brandCategory.setBrand(brand);
                    brandCategory.setCategory(existingCategory.get());
                    brandCategories.add(brandCategoryRepository.save(brandCategory));
                } else {
                    throw new RuntimeException("Categoría no encontrada con ID: " + existingCategoryId);
                }
            }
        }

        if (dto.getNewCategoryName() != null && dto.getNewCategoryImageUrl() != null) {
            for (int i = 0; i < dto.getNewCategoryName().size(); i++) {
                Category newCategory = new Category();
                newCategory.setName(dto.getNewCategoryName().get(i));
                newCategory.setImage_url(dto.getNewCategoryImageUrl().get(i));
                categoryRepository.save(newCategory);

                BrandCategory brandCategory = new BrandCategory();
                brandCategory.setBrand(brand);
                brandCategory.setCategory(newCategory);
                brandCategories.add(brandCategoryRepository.save(brandCategory));
            }
        }

        return brandCategories;
    }

    public BrandCategoryDTO updateBrandWithCategory(Long brandId, BrandCategoryDTO dto) {
        Optional<Brand> optionalBrand = brandRepository.findById(brandId);
        if (!optionalBrand.isPresent()) {
            throw new RuntimeException("Marca no encontrada con ID: " + brandId);
        }

        Brand brand = optionalBrand.get();

        brand.setName(dto.getBrandName());
        brand.setWebsite_url(dto.getWebsiteUrl());
        brand.setInstagram_url(dto.getInstagramUrl());
        brand.setLogoImg(dto.getLogoImg());
        brand.setCountry(dto.getCountry());

        brand.getImageUrls().clear();
        for (String imageUrl : dto.getImageUrls()) {
            Post brandImageUrl = new Post();
            brandImageUrl.setImageUrl(imageUrl);
            brandImageUrl.setBrand(brand);
            brand.getImageUrls().add(brandImageUrl);
        }

        brandRepository.save(brand);

        List<BrandCategory> brandCategories = brandCategoryRepository.findByBrandId(brandId);
        for (BrandCategory brandCategory : brandCategories) {
            if (!dto.getExistingCategoryId().contains(brandCategory.getCategory().getId())) {
                brandCategoryRepository.delete(brandCategory);
            }
        }

        if (dto.getExistingCategoryId() != null) {
            for (Long existingCategoryId : dto.getExistingCategoryId()) {
                Optional<Category> existingCategory = categoryRepository.findById(existingCategoryId);
                if (existingCategory.isPresent()) {
                    // Solo agregar si no existe ya
                    if (brandCategories.stream().noneMatch(bc -> bc.getCategory().getId().equals(existingCategoryId))) {
                        BrandCategory brandCategory = new BrandCategory();
                        brandCategory.setBrand(brand);
                        brandCategory.setCategory(existingCategory.get());
                        brandCategoryRepository.save(brandCategory);
                    }
                } else {
                    throw new RuntimeException("Categoría no encontrada con ID: " + existingCategoryId);
                }
            }
        }

        if (dto.getNewCategoryName() != null && dto.getNewCategoryImageUrl() != null) {
            for (int i = 0; i < dto.getNewCategoryName().size(); i++) {
                Category newCategory = new Category();
                newCategory.setName(dto.getNewCategoryName().get(i));
                newCategory.setImage_url(dto.getNewCategoryImageUrl().get(i));
                categoryRepository.save(newCategory);

                BrandCategory brandCategory = new BrandCategory();
                brandCategory.setBrand(brand);
                brandCategory.setCategory(newCategory);
                brandCategoryRepository.save(brandCategory);
            }
        }

        return convertToDTO(brand);
    }

    private BrandCategoryDTO convertToDTO(Brand brand) {
        List<Long> categoryIds = brandCategoryRepository.findByBrandId(brand.getId()).stream()
                .map(bc -> bc.getCategory().getId())
                .collect(Collectors.toList());

        return BrandCategoryDTO.builder()
                .brandName(brand.getName())
                .websiteUrl(brand.getWebsite_url())
                .instagramUrl(brand.getInstagram_url())
                .logoImg(brand.getLogoImg())
                .imageUrls(brand.getImageUrls().stream()
                        .map(Post::getImageUrl)
                        .collect(Collectors.toList()))
                .country(brand.getCountry())
                .existingCategoryId(categoryIds)
                .newCategoryName(null)
                .newCategoryImageUrl(null)
                .build();
    }
}