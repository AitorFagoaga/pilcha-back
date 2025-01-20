package pilcha.db.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pilcha.db.be.models.Brand;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository <Brand, Long>, JpaSpecificationExecutor<Brand> {
}
