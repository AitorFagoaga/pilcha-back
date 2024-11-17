package pilcha.db.be.models;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@IdClass(UserFavoriteId.class)
@Table(name = "user_favorite")
@Data
public class UserFavorite {
    @Id
    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
