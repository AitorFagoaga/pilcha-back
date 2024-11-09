package pilcha.db.be.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "brand")
@Data
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String website_url;
    @Column(nullable = false)
    private String instagram_url;
    @Column(nullable = false)
    private String image_urls;
    private String country;

    @OneToMany(mappedBy = "brand")
    private Set<BrandCategories> brandCategories;
}
