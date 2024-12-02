package vn.hoctienganh.models;

import java.util.Date;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.hoctienganh.entity.Post;
import vn.hoctienganh.entity.User;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentModel {
	@Id
	private Integer id;

	private String content;
	private Date commentDate;

	private User author;
	
	private String user;
	
	private Post post;
}
