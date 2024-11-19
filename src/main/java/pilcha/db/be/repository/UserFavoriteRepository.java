package pilcha.db.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pilcha.db.be.models.UserFavorite;
import pilcha.db.be.models.UserFavoriteId;

public interface UserFavoriteRepository extends JpaRepository<UserFavorite, UserFavoriteId> {
}
