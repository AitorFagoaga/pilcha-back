package pilcha.db.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pilcha.db.be.models.Garment;

public interface GarmentRepository extends JpaRepository<Garment, Long> {
}
