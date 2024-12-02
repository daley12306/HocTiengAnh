package vn.hoctienganh.services;

import java.util.List;

import org.springframework.data.domain.Page;

import vn.hoctienganh.entity.Post;
import vn.hoctienganh.models.PostModel;

public interface PostService {

	void deletePost(Integer id);

	Post savePost(Post post);

	Post getPostById(Integer id);

	Page<Post> getPosts(String keyword, int page, int pageSize);

	List<Post> getAllPosts();

	void likePost(Integer postId);

	List<Post> getTopLikedPosts();


}
