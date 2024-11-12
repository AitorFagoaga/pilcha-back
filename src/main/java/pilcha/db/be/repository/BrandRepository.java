package pilcha.db.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pilcha.db.be.models.Brand;

@Repository
public interface BrandRepository extends JpaRepository <Brand, Long> {
}
