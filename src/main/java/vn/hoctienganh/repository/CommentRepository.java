package vn.hoctienganh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.hoctienganh.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	
}
