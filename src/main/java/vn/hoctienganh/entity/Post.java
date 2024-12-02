package vn.hoctienganh.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "post")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(columnDefinition = "TEXT", nullable = false)
	private String title;
	
	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;
	
	private Date postDate;
	
	private Integer likes = 0; // Default value for likes

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	//private String author;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments;

	private String filePath;
	
	
	 @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	 private List<PostLike> postLikes = new ArrayList<>();

	    public void addPostLike(PostLike postLike) {
	        postLikes.add(postLike);
	        postLike.setPost(this);
	    }

	    public void removePostLike(PostLike postLike) {
	        if (postLikes != null) {
	            postLikes.remove(postLike);
	        }
	    }



	// Convenience methods for managing comments
	public void addComment(Comment comment) {
		comments.add(comment);
		comment.setPost(this);
	}

	public void removeComment(Comment comment) {
		comments.remove(comment);
		comment.setPost(null);
	}
}
