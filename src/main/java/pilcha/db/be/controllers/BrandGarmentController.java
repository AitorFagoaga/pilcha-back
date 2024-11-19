package pilcha.db.be.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pilcha.db.be.dto.BrandGarment.BrandGarmentDTO;
import pilcha.db.be.models.BrandGarment;
import pilcha.db.be.service.BrandGarmentService;

import java.util.List;

@RestController
@RequestMapping("/brandGarment")
public class BrandGarmentController {
    @Autowired
    private BrandGarmentService brandGarmentService;

    @PostMapping
    public ResponseEntity<List<BrandGarment>> createGarment(@RequestBody BrandGarmentDTO dto){
        List<BrandGarment> brandGarments = brandGarmentService.createGarment(dto);
        return ResponseEntity.ok(brandGarments);
    }
}
