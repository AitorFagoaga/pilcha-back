package pilcha.db.be.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@IdClass(BrandCategoryId.class)
@Table(name = "brand_category")
@Data
public class BrandCategory {

    @Id
    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @Id
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

}