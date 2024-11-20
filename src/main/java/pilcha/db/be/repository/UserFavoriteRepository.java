package pilcha.db.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pilcha.db.be.models.*;

import java.util.List;
import java.util.Optional;

public interface UserFavoriteRepository extends JpaRepository<UserFavorite, UserFavoriteId> {
    Optional<UserFavorite> findByUserAndBrand(User user, Brand brand);
}
