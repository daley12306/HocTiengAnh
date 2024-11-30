package vn.hoctienganh.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoctienganh.models.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> { 
	Optional<UserModel> findByName(String name);
	Page<UserModel> findByNameContaining(String name, Pageable pageable);
	Optional<UserModel> findByUsername(String username);
    Optional<UserModel> findByEmail(String email); // Tìm người dùng qua email
	boolean existsByUsername(String username); // Kiểm tra sự tồn tại của username
    boolean existsByEmail(String email); // Kiểm tra sự tồn tại của email
}
