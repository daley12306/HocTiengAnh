package vn.hoctienganh.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import vn.hoctienganh.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
    // Tìm kiếm bài viết với sắp xếp theo ngày tạo mới nhất
    Page<Post> findAllByOrderByPostDateDesc(Pageable pageable);

    // Tìm kiếm với từ khóa, sắp xếp theo ngày tạo mới nhất
    Page<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrderByPostDateDesc(String keyword, String keywordContent, Pageable pageable);

    @Query("SELECT p FROM Post p ORDER BY p.likes DESC")
    List<Post> findTopPosts(Pageable pageable);
}
