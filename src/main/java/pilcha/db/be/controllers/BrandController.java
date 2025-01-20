package pilcha.db.be.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pilcha.db.be.dto.Brand.BrandDTO;
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

    @GetMapping("/search")
    public List<BrandDTO> searchBrands(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String garment) {
        return brandService.searchBrands(name, category, garment);
    }

    @DeleteMapping("/{brandId}")
    public ResponseEntity<BrandDTO> deleteBrand(@PathVariable Long brandId) {
        BrandDTO deleteBrand = brandService.deleteBrand(brandId);
        return ResponseEntity.ok(deleteBrand);
    }

}
