package vn.hoctienganh.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Integer id;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String address;
    private String phoneNumber;
    private Boolean isAdmin;
    private Boolean isVerified;

}
