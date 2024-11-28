package vn.hoctienganh.models;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class UserModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	private String username;
	private String password;
	private String fullName;
	private String email;
	private String address;
	private String phoneNumber;
	private boolean isAdmin;
}
