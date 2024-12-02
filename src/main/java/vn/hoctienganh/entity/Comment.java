package vn.hoctienganh.entity;

import java.util.Date;

import jakarta.persistence.*;
import lombok.*;
import vn.hoctienganh.models.UserModel;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "comment")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String content;
	private Date commentDate;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User author;
	
	//private String user;
	
	@ManyToOne
	@JoinColumn(name = "post_id")
	private Post post;
	
	@Transient
    private boolean isEditing;
	// Getters and Setters
}
