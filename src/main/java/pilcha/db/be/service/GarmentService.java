package pilcha.db.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pilcha.db.be.dto.Garment.GarmentDTO;
import pilcha.db.be.models.Garment;
import pilcha.db.be.repository.BrandGarmentRepository;
import pilcha.db.be.repository.GarmentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GarmentService {
    @Autowired
    private GarmentRepository garmentRepository;

    @Autowired
    private BrandGarmentRepository brandGarmentRepository;

    public List<GarmentDTO>getAllGarments(){
        List<Garment> garment = garmentRepository.findAll();

        return garment.stream().map(garments -> {
            GarmentDTO dto = GarmentDTO.builder()
                    .id(garments.getId())
                    .name(garments.getName())
                    .description(garments.getDescription())
                    .image_url(garments.getImage_url())
                    .brandIds(brandGarmentRepository.findByGarmentId(garments.getId()).stream()
                            .map(bg -> bg.getBrand().getId())
                            .collect(Collectors.toList()))
                    .build();
            return dto;
        }).collect(Collectors.toList());
    }
}
