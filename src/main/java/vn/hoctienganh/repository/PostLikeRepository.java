package vn.hoctienganh.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoctienganh.entity.Post;
import vn.hoctienganh.entity.PostLike;
import vn.hoctienganh.entity.User;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Integer> {
    Optional<PostLike> findByUserAndPost(User user, Post post);
}

