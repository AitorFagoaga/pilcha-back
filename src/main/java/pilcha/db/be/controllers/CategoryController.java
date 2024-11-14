package pilcha.db.be.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pilcha.db.be.dto.Category.CategoryDTO;
import pilcha.db.be.models.Category;
import pilcha.db.be.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category>getAllCategories(){
        return categoryService.getAllCategories();
    }

}
