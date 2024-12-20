package pilcha.db.be.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "\"user\"")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String email;
    private Integer age;
    @Column(nullable = false)
    private String password;
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private Boolean is_premium;
    @Column(nullable = false)
    private Boolean is_brand;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}
