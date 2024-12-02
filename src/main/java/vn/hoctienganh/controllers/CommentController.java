package vn.hoctienganh.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpSession;
import vn.hoctienganh.entity.Comment;
import vn.hoctienganh.entity.Post;
import vn.hoctienganh.entity.User;
import vn.hoctienganh.services.CommentService;
import vn.hoctienganh.services.PostService;

@Controller
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    // Thêm bình luận vào bài đăng
	/*
	 * @PostMapping("/add") public String addComment(@RequestParam Integer
	 * postId, @RequestParam String content, @SessionAttribute("user") User user) {
	 * Post post = postService.getPostById(postId); Comment comment = new Comment();
	 * comment.setContent(content); comment.setPost(post); comment.setUser(user);
	 * commentService.saveComment(comment); return "redirect:/posts/" + postId; }
	 */
    @PostMapping("/add")
    public String addComment(@RequestParam Integer postId, @RequestParam String content, @SessionAttribute("user") User user) {
        Post post = postService.getPostById(postId);
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setPost(post);
        //comment.setUser("Anonymous");
        comment.setAuthor(user);
        comment.setCommentDate(new Date());
        commentService.saveComment(comment);
        return "redirect:/posts/" + postId;
    }
    // Xóa bình luận
    @PostMapping("/{commentId}/delete")
    public String deleteComment(@PathVariable Integer commentId, @RequestParam Integer postId, HttpSession session) {
        // Lấy người dùng từ session
        User currentUser = (User) session.getAttribute("user");

        // Kiểm tra bình luận có tồn tại không
        Comment comment = commentService.getCommentById(commentId);
        if (comment == null) {
            return "redirect:/posts/" + postId + "?error=commentNotFound"; // Nếu không tìm thấy bình luận
        }

        // Kiểm tra quyền xóa
        if (currentUser != null && (currentUser.getId().equals(comment.getAuthor().getId()) || currentUser.isAdmin())) {
            commentService.deleteComment(commentId); // Xóa bình luận
            return "redirect:/posts/" + postId + "?success=commentDeleted"; // Quay lại bài viết với thông báo thành công
        }

        // Nếu không có quyền xóa, quay lại với thông báo lỗi
        return "redirect:/posts/" + postId + "?error=unauthorized";
    }

 



}
