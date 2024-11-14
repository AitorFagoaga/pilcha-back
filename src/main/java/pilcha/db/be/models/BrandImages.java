package pilcha.db.be.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "brand_image")
@Data
public class BrandImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Column(name = "image_url")
    private String imageUrl;
}