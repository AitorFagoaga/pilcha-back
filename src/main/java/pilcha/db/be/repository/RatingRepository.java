package pilcha.db.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pilcha.db.be.models.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}
