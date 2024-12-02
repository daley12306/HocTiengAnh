package vn.hoctienganh.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import vn.hoctienganh.entity.Post;
import vn.hoctienganh.entity.PostLike;
import vn.hoctienganh.entity.User;
import vn.hoctienganh.repository.PostLikeRepository;
import vn.hoctienganh.services.FileStorageService;
import vn.hoctienganh.services.PostService;
import vn.hoctienganh.services.UserService;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private PostLikeRepository postLikeRepository;
    @Autowired
    private UserService userService; // Service để truy vấn user từ database
    @Autowired
    private FileStorageService fileStorageService;
    
    private static final int PAGE_SIZE = 10; // Define page size

    // Tạo bài đăng mới
    @GetMapping("/create")
    public String createPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "forum/post-create";
    }

    @PostMapping("/create")
    public String createPost(@ModelAttribute Post post, @RequestParam("file") MultipartFile file, @SessionAttribute("user") User user, Model model) {
    	//post.setAuthor("Anonymous");
        post.setUser(user); // Set the author to the logged-in user
        post.setPostDate(new Date()); // Set the post date to the current date
        
        try {
            if (!file.isEmpty()) {
                // Lưu file và gán đường dẫn vào bài viết
                String filePath = fileStorageService.saveFile(file);
                post.setFilePath(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/posts?error=true"; // Xử lý lỗi nếu cần
        }
        postService.savePost(post);
        return "redirect:/posts?success=create"; // Quay lại trang danh sách bài viết với thông báo tạo bài viết thành công";
    }
    
    @GetMapping
    public String listPosts(
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            Model model) {
        // Lấy danh sách bài đăng theo tìm kiếm và phân trang
        Page<Post> posts = postService.getPosts(keyword, page, PAGE_SIZE);

        // Lấy top 5 bài viết nhiều like nhất (cho phần Active Topics)
        List<Post> topLikedPosts = postService.getTopLikedPosts();

        // Đưa dữ liệu vào model để hiển thị trong view
        model.addAttribute("posts", posts.getContent()); // Nội dung bài viết trong trang hiện tại
        model.addAttribute("topLikedPosts", topLikedPosts); // Top bài viết nhiều like
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", posts.getTotalPages());
        model.addAttribute("keyword", keyword);

        return "forum/post-list";
    }


    // Hiển thị chi tiết bài đăng
    @GetMapping("/{id}")
    public String viewPost(@PathVariable Integer id, Model model) {
    	// Lấy top 5 bài viết nhiều like nhất (cho phần Active Topics)
        List<Post> topLikedPosts = postService.getTopLikedPosts();
        Post post = postService.getPostById(id);
        model.addAttribute("post", post); 
        model.addAttribute("topLikedPosts", topLikedPosts);
        return "forum/post-view";
    }

    // Chỉnh sửa bài đăng
    @GetMapping("/{id}/edit")
    public String editPostForm(@PathVariable Integer id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "forum/post-edit";
    }


 // Chỉnh sửa bài đăng
    @PostMapping("/{id}/edit")
    public String editPost(@PathVariable Integer id, 
                           @ModelAttribute Post post, 
                           @RequestParam(value = "file", required = false) MultipartFile file, 
                           HttpSession session, 
                           Model model) {
        // Lấy bài viết từ database
        Post existingPost = postService.getPostById(id);

        // Lấy thông tin người dùng từ session
        User currentUser = (User) session.getAttribute("user");

        // Kiểm tra xem người dùng có phải là admin hoặc người tạo bài đăng
        if (currentUser != null && (currentUser.getId().equals(existingPost.getUser().getId()))) {
            // Giữ lại số lượt like, bình luận, ngày đăng, tác giả
            post.setPostDate(existingPost.getPostDate()); // Giữ lại ngày đăng
            post.setUser(existingPost.getUser()); // Giữ lại tác giả
            post.setLikes(existingPost.getLikes()); // Giữ lại số lượt like
            post.setComments(existingPost.getComments()); // Giữ lại các bình luận

            String oldFilePath = existingPost.getFilePath();
            // Xử lý file ảnh nếu có
            if (file != null && !file.isEmpty()) {
            	System.out.println("File uploaded: " + file.getOriginalFilename());
                try {
                    // Lưu file ảnh mới và cập nhật lại filePath
                    String filePath = fileStorageService.saveFile(file);
                    post.setFilePath(filePath); // Cập nhật đường dẫn file
                } catch (IOException e) {
                    e.printStackTrace();
                    return "redirect:/posts/" + id + "?error=fileUploadError"; // Thông báo lỗi khi upload file
                }
            } else {
            	System.out.println("No file uploaded, keeping old file.");
                // Nếu không có file ảnh mới, giữ lại filePath cũ
                post.setFilePath(oldFilePath);
            }

            post.setId(id); // Đảm bảo sử dụng đúng ID bài viết cần chỉnh sửa
            postService.savePost(post); // Lưu bài viết với thông tin đã được cập nhật

            // Thêm thông báo thành công vào URL
            return "redirect:/posts/" + id + "?success=edit"; // Quay lại chi tiết bài viết với thông báo thành công
        } else {
            // Thêm thông báo lỗi nếu không có quyền chỉnh sửa
            return "redirect:/posts/" + id + "?error=unauthorized"; // Nếu không có quyền, chuyển hướng đến trang lỗi
        }
    }


    // Xóa bài đăng
    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable Integer id, HttpSession session, Model model) {
        // Lấy bài đăng từ database
        Post post = postService.getPostById(id);
        if (post == null) {
            return "redirect:/posts?error=notfound"; // Nếu bài đăng không tồn tại
        }

        // Lấy thông tin người dùng từ session
        User currentUser = (User) session.getAttribute("user");

        // Kiểm tra xem người dùng có phải là admin hoặc người tạo bài đăng
        if (currentUser != null && (currentUser.getId().equals(post.getUser().getId()) || currentUser.isAdmin())) {
            postService.deletePost(id); // Nếu đúng, cho phép xóa
            return "redirect:/posts?success=deleted"; // Quay lại danh sách bài viết với thông báo thành công
        } else {
            // Thêm thông báo lỗi vào URL
            return "redirect:/posts?error=unauthorized"; // Nếu không có quyền, chuyển hướng đến trang danh sách bài viết với thông báo lỗi
        }
    }
    
    
    
    @PostMapping("/{id}/like")
    public String likePost(@PathVariable Integer id, HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";  // Nếu người dùng chưa đăng nhập
        }

        Post post = postService.getPostById(id);  // Lấy bài viết theo id
       

        // Kiểm tra xem người dùng đã like bài viết chưa
        Optional<PostLike> existingLike = postLikeRepository.findByUserAndPost(currentUser, post);
        
      
            if (existingLike.isPresent()) {
                // Nếu đã like, bỏ like
                post.removePostLike(existingLike.get());
                post.setLikes(post.getLikes() - 1); // Giảm số lượng like nếu cần
                postService.savePost(post);  // Cập nhật bài viết
                return "redirect:/posts/" + id;  // Quay lại trang chi tiết bài viết
            } else {
                // Nếu chưa like, thêm like
                PostLike postLike = new PostLike();
                postLike.setUser(currentUser);
                postLike.setPost(post);
                post.addPostLike(postLike);  // Thêm like vào bài viết
                post.setLikes(post.getLikes() + 1); // Tăng số lượng like
                postService.savePost(post);  // Cập nhật bài viết
                
            }
            return "redirect:/posts/" + id ;
    }



    

    @ModelAttribute
    public void addDefaultUserToSession(HttpSession session) {
            User defaultUser = userService.findUserByUsername("user2");
            session.setAttribute("user", defaultUser);
        
    }
    
  
    
}
