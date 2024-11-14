package pilcha.db.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pilcha.db.be.models.BrandCategory;
import pilcha.db.be.models.BrandCategoryId;

import java.util.List;

public interface BrandCategoryRepository extends JpaRepository<BrandCategory, BrandCategoryId> {
    List<BrandCategory> findByCategoryId(Long categoryId);
    List<BrandCategory> findByBrandId(Long brandId);
}
