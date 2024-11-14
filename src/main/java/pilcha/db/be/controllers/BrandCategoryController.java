package pilcha.db.be.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pilcha.db.be.dto.BrandCategory.BrandCategoryDTO;
import pilcha.db.be.models.BrandCategory;
import pilcha.db.be.models.Category;
import pilcha.db.be.service.BrandCategoryService;

import java.util.List;

@RestController
@RequestMapping("/brandCategory")
public class BrandCategoryController {
    @Autowired
    private BrandCategoryService brandCategoryService;

    @GetMapping("/categoryId/{categoryId}")
    public ResponseEntity<List<BrandCategory>> getByCategory(@PathVariable Long categoryId) {
        List<BrandCategory> brandCategories = brandCategoryService.getBrandCategoriesByCategoryId(categoryId);
        return ResponseEntity.ok(brandCategories);
    }

    @GetMapping("/brandId/{brandId}")
    public ResponseEntity<List<Category>> getCategoriesByBrand(@PathVariable Long brandId) {
        List<Category> categories = brandCategoryService.getCategoriesByBrandId(brandId);
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<List<BrandCategory>> createBrandWithCategories(@RequestBody BrandCategoryDTO dto) {
        List<BrandCategory> brandCategories = brandCategoryService.createBrandWithCategory(dto);
        return ResponseEntity.ok(brandCategories);
    }

}