package pilcha.db.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pilcha.db.be.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
