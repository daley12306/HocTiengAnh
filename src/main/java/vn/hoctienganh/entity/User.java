package vn.hoctienganh.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED) // Because Student and Admin inherit this class
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
    
    private Boolean isVerified = false; // Mặc định là false, khi chưa xác thực
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<StudyRecord> studyRecords;

    // Phương thức kiểm tra vai trò
    public boolean isAdmin() {
        return this.isAdmin;
    }
    public void login() {}
    public void register() {}
    public void resetPassword() {}

    // Getters and Setters
}
