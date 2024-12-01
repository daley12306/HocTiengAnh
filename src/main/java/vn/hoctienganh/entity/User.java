package vn.hoctienganh.entity;

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
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private String fullName;
    private String email;
    private String address;
    private String phoneNumber;
    
    private String avatar;
    
    @Column(nullable = false)
    private Boolean isAdmin;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<StudyRecord> studyRecords;

    public void login() {}
    public void register() {}
    public void resetPassword() {}

    // Getters and Setters
}
