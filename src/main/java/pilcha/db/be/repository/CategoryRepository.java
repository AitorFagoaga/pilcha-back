package pilcha.db.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pilcha.db.be.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
