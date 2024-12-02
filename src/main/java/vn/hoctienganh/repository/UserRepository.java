package vn.hoctienganh.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import vn.hoctienganh.entity.User;

public interface UserRepository extends JpaRepository<User, Long> { 
	Optional<User> findByFullName(String fullName);
	Optional<User> findByUsername(String username);
	Page<User> findByFullNameContaining(String fullName, Pageable pageable);
	boolean existsByEmail(String email);
	boolean existsByUsername(String username);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByUsernameAndIdNot(String username, Long id);
    boolean existsByEmailAndIdNot(String email, Long id);   
    boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);
}
