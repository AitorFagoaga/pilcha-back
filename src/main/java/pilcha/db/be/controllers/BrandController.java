package pilcha.db.be.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pilcha.db.be.dto.BrandDTO;
import pilcha.db.be.models.Brand;
import pilcha.db.be.service.BrandService;

import java.util.List;

@RestController
@RequestMapping("/brands")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping
    public List<BrandDTO>getAllBrands(){
        return brandService.getAllBrands();
    }

}
