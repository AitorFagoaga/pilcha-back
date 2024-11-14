package pilcha.db.be.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "brand_categories")
@Data
public class BrandCategories {

    @Id
    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @Id
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;


}