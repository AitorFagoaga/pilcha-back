package pilcha.db.be.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "garment")
@Data
public class Garment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String image_url;

    @OneToMany(mappedBy = "garment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<BrandGarment> brandGarment = new HashSet<>();
}
