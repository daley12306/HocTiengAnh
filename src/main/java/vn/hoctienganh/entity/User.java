package vn.hoctienganh.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private String fullName;
    private String email;
    private String address;
    private String phoneNumber;
    
    @Column(nullable = false)
    private boolean isAdmin;
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private StudyRecord studyRecords;

    // Getters and Setters
}
