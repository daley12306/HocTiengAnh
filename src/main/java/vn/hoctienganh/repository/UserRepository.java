package vn.hoctienganh.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import vn.hoctienganh.models.UserModel;

public interface UserRepository  { //extends JpaRepository<UserModel, Long>
//	Optional<UserModel> findByName(String name);
//	Page<UserModel> findByNameContaining(String name, Pageable pageable);
}
