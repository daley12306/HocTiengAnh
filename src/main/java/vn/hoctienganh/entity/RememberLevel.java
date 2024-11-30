package vn.hoctienganh.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "remember_level")
public class RememberLevel {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // ID chính cho bảng RememberLevel

    @Column(name = "mem_level", columnDefinition = "INT DEFAULT 0")
    private int memLevel = 0; // Giá trị mặc định là 0

    @Column(name = "last_learn", columnDefinition = "DATETIME DEFAULT NULL")
    private LocalDateTime lastLearn = null; // Giá trị mặc định là NULL

    @Column(name = "learn_count", columnDefinition = "INT DEFAULT 0")
    private int learnCount = 0; // Giá trị mặc định là 0

    // Quan hệ nhiều - 1 với Vocabulary
    @ManyToOne
    @JoinColumn(name = "vocabulary_id", nullable = false)
    private Vocabulary vocabulary;

    // Quan hệ nhiều - 1 với StudyRecord
    @ManyToOne
    @JoinColumn(name = "studyrecord_id", nullable = false)
    private StudyRecord studyRecord;
}
