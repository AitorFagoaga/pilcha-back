package pilcha.db.be.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table (name = "brand_categorie")
@Data
public class BrandCategories {
    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

}
