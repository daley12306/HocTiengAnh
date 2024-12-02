package vn.hoctienganh.entity;

import java.io.Serializable;
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
@Inheritance(strategy = InheritanceType.JOINED) // Because Student and Admin inherit this class
public class User implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
    
    @Column(columnDefinition = "nvarchar(255)")
    private String fullName;
    private String email;
    private String address;
    private String phoneNumber;
    
    private String avatar;
    
    @Column(nullable = false)
    private Boolean isAdmin;
    
    private Boolean isVerified = false; // Mặc định là false, khi chưa xác thực
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<StudyRecord> studyRecords;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PostLike> postLikes;

    // Phương thức kiểm tra vai trò
    public boolean isAdmin() {
        return this.isAdmin;
    }
    public void login() {}
    public void register() {}
    public void resetPassword() {}

    // Getters and Setters
}
