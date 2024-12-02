package vn.hoctienganh.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "study_record")
public class StudyRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private boolean learningStatus;

	@OneToOne
	@JoinColumn(name = "student_id")
	private User user;

	@ManyToMany
	@JoinTable(name = "study_record_curriculum", // Tên bảng liên kết
			joinColumns = @JoinColumn(name = "study_record_id"), // Cột khóa chính của StudyRecord
			inverseJoinColumns = @JoinColumn(name = "curriculum_id") // Cột khóa chính của Curriculum
	)
	private List<Curriculum> curriculums;

}
