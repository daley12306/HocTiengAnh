package vn.hoctienganh.models;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.hoctienganh.entity.Comment;
import vn.hoctienganh.entity.PostLike;
import vn.hoctienganh.entity.User;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostModel {
	@Id
	private Integer id;

	private String title;
	private String content;
	private Date postDate;
	private Integer likes; 
	private User user;
	
	private List<Comment> comments;
	private List<PostLike> postLikes;
	
	private String filePath;
	
	private MultipartFile file;
}
