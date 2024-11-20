package pilcha.db.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pilcha.db.be.dto.BrandCategory.BrandCategoryDTO;
import pilcha.db.be.dto.BrandDTO;
import pilcha.db.be.dto.Category.CategoryDTO;
import pilcha.db.be.models.Brand;
import pilcha.db.be.models.BrandImages;
import pilcha.db.be.models.Category;
import pilcha.db.be.models.BrandCategory;
import pilcha.db.be.repository.CategoryRepository;
import pilcha.db.be.repository.BrandCategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandCategoryRepository brandCategoryRepository;

    public List<CategoryDTO> getAllCategories() {
        List<Category> category = categoryRepository.findAll();

        return category.stream().map(categories -> {
            List<BrandCategory> brandCategories = brandCategoryRepository.findByCategoryId(categories.getId());

            List<Long> brandIds = brandCategories.stream()
                    .map(bc -> bc.getBrand().getId())
                    .collect(Collectors.toList());
            List<String> brandNames = brandCategories.stream()
                    .map(bc -> bc.getBrand().getName())
                    .collect(Collectors.toList());

            CategoryDTO dto = CategoryDTO.builder()
                    .id(categories.getId())
                    .name(categories.getName())
                    .image_url(categories.getImage_url())
                    .brandCategoryIds(brandIds)
                    .brandCategoryNames(brandNames)
                    .build();
            return dto;
        }).collect(Collectors.toList());
    }

    public CategoryDTO updateCategory(Long categoryId, CategoryDTO dto) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (!optionalCategory.isPresent()) {
            throw new RuntimeException("Categoría no encontrada con ID: " + categoryId);
        }

        Category category = optionalCategory.get();
        category.setName(dto.getName());

        categoryRepository.save(category);

        return convertToDTO(category);
    }

    public Category findById(Long categoryId){
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada con id:" + categoryId));
    }

    public CategoryDTO deleteCategory (Long categoryId){
        Category category = findById(categoryId);

        CategoryDTO categoryDTO = CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .image_url(category.getImage_url())
                .brandCategoryIds(brandCategoryRepository.findByCategoryId(category.getId()).stream()
                        .map(bc -> bc.getBrand().getId())
                        .collect(Collectors.toList()))
                .build();
        List<BrandCategory> brandCategories = brandCategoryRepository.findByCategoryId(categoryId);
        for (BrandCategory brandCategory : brandCategories) {
            brandCategoryRepository.delete(brandCategory);
        }
        categoryRepository.delete(category);

        return categoryDTO;
    }

    private CategoryDTO convertToDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .image_url(category.getImage_url())
                .brandCategory(category.getBrandCategories().stream()
                        .map(bc -> BrandCategoryDTO.builder()
                                .brandName(bc.getBrand().getName())
                                .websiteUrl(bc.getBrand().getWebsite_url())
                                .instagramUrl(bc.getBrand().getInstagram_url())
                                .logoImg(bc.getBrand().getLogoImg())
                                .imageUrls(bc.getBrand().getImageUrls().stream()
                                        .map(BrandImages::getImageUrl)
                                        .collect(Collectors.toList()))
                                .country(bc.getBrand().getCountry())
                                .build())
                        .collect(Collectors.toSet()))
                .build();
    }
}