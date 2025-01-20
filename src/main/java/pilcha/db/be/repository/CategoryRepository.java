package pilcha.db.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pilcha.db.be.models.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByNameIgnoreCase(String name);
}
