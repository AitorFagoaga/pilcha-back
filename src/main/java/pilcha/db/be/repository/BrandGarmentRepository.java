package pilcha.db.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pilcha.db.be.models.BrandGarment;
import pilcha.db.be.models.BrandGarmentId;

import java.util.List;

public interface BrandGarmentRepository extends JpaRepository<BrandGarment, BrandGarmentId> {
    List<BrandGarment> findByGarmentId(Long garmentId);
}
