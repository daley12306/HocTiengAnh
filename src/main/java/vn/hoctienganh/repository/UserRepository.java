package vn.hoctienganh.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import vn.hoctienganh.entity.User;
import vn.hoctienganh.models.UserModel;

public interface UserRepository extends JpaRepository<User, Long> { 
	Optional<User> findByFullName(String fullName);
	Page<User> findByFullNameContaining(String fullName, Pageable pageable);
}
