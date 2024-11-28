package vn.hoctienganh.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoctienganh.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> { 
	Optional<User> findByFullName(String fullName);
	Page<User> findByFullNameContaining(String fullName, Pageable pageable);

}
