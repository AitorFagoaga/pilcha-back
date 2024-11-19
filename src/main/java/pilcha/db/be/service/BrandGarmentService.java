package pilcha.db.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pilcha.db.be.dto.BrandGarment.BrandGarmentDTO;
import pilcha.db.be.models.Brand;
import pilcha.db.be.models.BrandGarment;
import pilcha.db.be.models.BrandGarmentId;
import pilcha.db.be.models.Garment;
import pilcha.db.be.repository.BrandGarmentRepository;
import pilcha.db.be.repository.BrandRepository;
import pilcha.db.be.repository.GarmentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BrandGarmentService {
    @Autowired
    private BrandGarmentRepository brandGarmentRepository;

    @Autowired
    private GarmentRepository garmentRepository;

    @Autowired
    private BrandRepository brandRepository;

    public List<BrandGarment> createGarment(BrandGarmentDTO dto){
        Garment garment = new Garment();
        garment.setName(dto.getName());
        garment.setDescription(dto.getDescription());
        garment.setImage_url(dto.getImage_url());

        garmentRepository.save(garment);

        List<BrandGarment> brandGarments = new ArrayList<>();

        for (Long brandId : dto.getBrandId()){
            Optional<Brand> brand = brandRepository.findById(brandId);
            if (brand.isPresent()){
                BrandGarment brandGarment = new BrandGarment();
                brandGarment.setGarment(garment);
                brandGarment.setBrand(brand.get());
                brandGarments.add(brandGarmentRepository.save(brandGarment));
            }else {
                throw new RuntimeException("Brand no encontrada con ID:" + brandId);
            }
        }
        return brandGarments;
    }
}
