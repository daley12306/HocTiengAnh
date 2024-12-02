package vn.hoctienganh.entity;

import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false) // Liên kết với Student
    private User student;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = false, length = 100)
    private String subject;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "image", columnDefinition = "TEXT")
    private String image; // URL hoặc đường dẫn tới hình ảnh
    
    @Column(nullable = false)
    private boolean isReply	= false; // Trạng thái phản hồi;
    
    @Column(nullable = false)
    private Date created = new Date(); // Ngày tạo

	@Override
	public String toString() {
		return "Feedback [id=" + id + ", student=" + student + ", email=" + email + ", subject=" + subject
				+ ", description=" + description + ", image=" + image + ", isReply=" + isReply + ", created=" + created
				+ "]";
	}

    
}
