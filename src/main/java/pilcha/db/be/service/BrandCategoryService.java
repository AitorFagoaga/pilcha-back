package pilcha.db.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pilcha.db.be.dto.BrandCategory.BrandCategoryDTO;
import pilcha.db.be.dto.BrandDTO;
import pilcha.db.be.models.Brand;
import pilcha.db.be.models.BrandImages;
import pilcha.db.be.models.Category;
import pilcha.db.be.models.BrandCategory;
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

        // Extraer las categorías de las relaciones
        return brandCategories.stream()
                .map(BrandCategory::getCategory)
                .collect(Collectors.toList());
    }

    public List<BrandCategory> createBrandWithCategory(BrandCategoryDTO dto) {
        // Crear la nueva marca
        Brand brand = new Brand();
        brand.setName(dto.getBrandName());
        brand.setWebsite_url(dto.getWebsiteUrl());
        brand.setInstagram_url(dto.getInstagramUrl());
        brand.setLogoImg(dto.getLogoImg());
        brand.setCountry(dto.getCountry());

        for (String imageUrl : dto.getImageUrls()) {
            BrandImages brandImageUrl = new BrandImages();
            brandImageUrl.setImageUrl(imageUrl);
            brandImageUrl.setBrand(brand);
            brand.getImageUrls().add(brandImageUrl);
        }

        brandRepository.save(brand);

        List<BrandCategory> brandCategories = new ArrayList<>();

        // Manejar categorías existentes
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

        // Manejar nuevas categorías
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
        // Buscar la marca existente
        Optional<Brand> optionalBrand = brandRepository.findById(brandId);
        if (!optionalBrand.isPresent()) {
            throw new RuntimeException("Marca no encontrada con ID: " + brandId);
        }

        Brand brand = optionalBrand.get();

        // Actualizar los atributos de la marca
        brand.setName(dto.getBrandName());
        brand.setWebsite_url(dto.getWebsiteUrl());
        brand.setInstagram_url(dto.getInstagramUrl());
        brand.setLogoImg(dto.getLogoImg());
        brand.setCountry(dto.getCountry());

        // Limpiar las imágenes antiguas y agregar nuevas
        brand.getImageUrls().clear();
        for (String imageUrl : dto.getImageUrls()) {
            BrandImages brandImageUrl = new BrandImages();
            brandImageUrl.setImageUrl(imageUrl);
            brandImageUrl.setBrand(brand);
            brand.getImageUrls().add(brandImageUrl);
        }

        // Guardar la marca actualizada
        brandRepository.save(brand);

        // Manejar categorías existentes
        List<BrandCategory> brandCategories = brandCategoryRepository.findByBrandId(brandId);
        for (BrandCategory brandCategory : brandCategories) {
            if (!dto.getExistingCategoryId().contains(brandCategory.getCategory().getId())) {
                brandCategoryRepository.delete(brandCategory);
            }
        }

        // Manejar categorías existentes
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

        // Manejar nuevas categorías
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

        // Retornar la información actualizada
        return convertToDTO(brand);
    }

    private BrandCategoryDTO convertToDTO(Brand brand) {
        // Obtener las categorías asociadas a la marca
        List<Long> categoryIds = brandCategoryRepository.findByBrandId(brand.getId()).stream()
                .map(bc -> bc.getCategory().getId())
                .collect(Collectors.toList());

        return BrandCategoryDTO.builder()
                .brandName(brand.getName())
                .websiteUrl(brand.getWebsite_url())
                .instagramUrl(brand.getInstagram_url())
                .logoImg(brand.getLogoImg())
                .imageUrls(brand.getImageUrls().stream()
                        .map(BrandImages::getImageUrl)
                        .collect(Collectors.toList()))
                .country(brand.getCountry())
                .existingCategoryId(categoryIds)
                .newCategoryName(null)  // Inicializar como null si no se está usando
                .newCategoryImageUrl(null)  // Inicializar como null si no se está usando
                .build();
    }
}