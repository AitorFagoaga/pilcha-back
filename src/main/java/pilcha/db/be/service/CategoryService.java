package pilcha.db.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pilcha.db.be.dto.BrandCategory.BrandCategoryDTO;
import pilcha.db.be.dto.Category.CategoryDTO;
import pilcha.db.be.models.Category;
import pilcha.db.be.models.BrandCategory;
import pilcha.db.be.repository.CategoryRepository;
import pilcha.db.be.repository.BrandCategoryRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandCategoryRepository brandCategoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}