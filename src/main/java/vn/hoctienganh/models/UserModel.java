package vn.hoctienganh.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserModel {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động tăng ID
	private Long id;
	private String username;
	private String password;
	private String firstName;
	private String name;
	private String email;
	private String phone;
	
}
