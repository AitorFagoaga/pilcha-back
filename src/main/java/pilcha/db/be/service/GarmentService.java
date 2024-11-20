package pilcha.db.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pilcha.db.be.dto.Garment.GarmentDTO;
import pilcha.db.be.models.BrandGarment;
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
            List<BrandGarment> brandGarment = brandGarmentRepository.findByGarmentId(garments.getId());

            List<Long> brandIds = brandGarment.stream()
                    .map(bc -> bc.getBrand().getId())
                    .collect(Collectors.toList());

            List<String> brandNames = brandGarment.stream()
                    .map(bc -> bc.getBrand().getName())
                    .collect(Collectors.toList());

            GarmentDTO dto = GarmentDTO.builder()
                    .id(garments.getId())
                    .name(garments.getName())
                    .description(garments.getDescription())
                    .image_url(garments.getImage_url())
                    .brandIds(brandIds)
                    .brandNames(brandNames)
                    .build();
            return dto;
        }).collect(Collectors.toList());
    }

}
