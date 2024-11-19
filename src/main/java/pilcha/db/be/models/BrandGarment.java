package pilcha.db.be.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@IdClass(BrandGarmentId.class)
@Table(name = "brand_garment")
@Data
public class BrandGarment {
    @Id
    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @Id
    @ManyToOne
    @JoinColumn(name = "garment_id", nullable = false)
    private Garment garment;
}
