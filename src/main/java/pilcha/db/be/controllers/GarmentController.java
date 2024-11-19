package pilcha.db.be.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pilcha.db.be.dto.Garment.GarmentDTO;
import pilcha.db.be.service.GarmentService;

import java.util.List;

@RestController
@RequestMapping("/garments")
public class GarmentController {

    @Autowired
    private GarmentService garmentService;

    @GetMapping
    public List<GarmentDTO> getAllGarments(){
        return garmentService.getAllGarments();
    }
}
