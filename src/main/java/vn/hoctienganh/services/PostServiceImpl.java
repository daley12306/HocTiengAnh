package vn.hoctienganh.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import vn.hoctienganh.entity.Comment;
import vn.hoctienganh.entity.Post;
import vn.hoctienganh.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Page<Post> getPosts(String keyword, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        if (keyword == null || keyword.isEmpty()) {
            return postRepository.findAllByOrderByPostDateDesc(pageable);
        }
        return postRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrderByPostDateDesc(keyword, keyword, pageable);
    }

    @Override
	public Post getPostById(Integer id) {
        return postRepository.findById(id).orElse(null);
    }

    @Override
    public Post savePost(Post post) {
        // Kiểm tra và khởi tạo danh sách comment nếu nó là null
        if (post.getComments() == null) {
            post.setComments(new ArrayList<>());
        }

        // Nếu post đã tồn tại (chỉnh sửa), lấy bài viết cũ và giữ lại dữ liệu cũ
        if (post.getId() != null) {
            Post existingPost = postRepository.findById(post.getId()).orElse(null);
            if (existingPost != null) {
                // Giữ lại thông tin như ngày đăng và tác giả
                post.setPostDate(existingPost.getPostDate()); // Giữ lại ngày đăng cũ
                //post.setAuthor(existingPost.getAuthor()); // Giữ lại tác giả cũ
                post.setUser(existingPost.getUser());
                post.setLikes(existingPost.getLikes()); // Giữ lại số lượt like cũ
                post.setComments(existingPost.getComments()); // Giữ lại các comment cũ
            }
        }

        // Thiết lập lại mối quan hệ với các bình luận
        for (Comment comment : post.getComments()) {
            comment.setPost(post); // Đảm bảo mối quan hệ giữa Comment và Post được thiết lập
        }

        return postRepository.save(post);
    }



    @Override
	public void deletePost(Integer id) {
        postRepository.deleteById(id);
    }
    @Override
	public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
    
    @Override
	public void likePost(Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setLikes(post.getLikes() + 1); // Tăng số lượt like
        postRepository.save(post); // Lưu lại thay đổi
    }
    
 // Lấy 5 bài viết có nhiều like nhất
    @Override
	public List<Post> getTopLikedPosts() {
        return postRepository.findTopPosts(PageRequest.of(0, 5)); // Lấy 5 bài viết đầu tiên
    }
    
    
}
