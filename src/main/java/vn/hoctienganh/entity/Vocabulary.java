package vn.hoctienganh.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "vocabulary")
public class Vocabulary {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String word;

	@Column(name = "pronunciation", columnDefinition = "NVARCHAR(MAX) COLLATE Vietnamese_CI_AS")
	private String pronunciation;

	@Column(name = "definition", columnDefinition = "NVARCHAR(MAX) COLLATE Vietnamese_CI_AS")
	private String definition;

	private String example;

	@Column(columnDefinition = "VARCHAR(MAX)")
	private String audio;

	@Column(columnDefinition = "VARCHAR(MAX)")
	private String image;

	@Column(nullable = false)
	private Integer learnCount = 0; // Thêm giá trị mặc định

	@Column(nullable = false)
	private Integer memLevel = 0; // Thêm giá trị mặc định

	@Column
	private LocalDateTime lastLearn = LocalDateTime.now(); // Thêm giá trị mặc định

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "curriculum_id")
	@JsonIgnore // Thêm annotation này để tránh vòng lặp JSON
	private Curriculum curriculum;

	// Constructor mặc định
	public Vocabulary() {
		this.learnCount = 0;
		this.memLevel = 0;
		this.lastLearn = LocalDateTime.now();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getPronunciation() {
		return pronunciation;
	}

	public void setPronunciation(String pronunciation) {
		this.pronunciation = pronunciation;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}

	public String getAudio() {
		if (audio != null) {
			if (audio.contains("/9/")) {
				String[] parts = audio.split("/9/");
				String fileName = parts[1];
				fileName = fileName.replaceAll("^\\d+_", "");
				return "https://trumtuvung.com/audio/9/" + fileName;
			}
			if (!audio.startsWith("http")) {
				audio = audio.replaceAll("^\\d+_", "");
				return "https://trumtuvung.com/audio/9/" + audio;
			}
		}
		return audio;
	}

	public void setAudio(String audio) {
		if (audio != null) {
			if (audio.contains("/9/")) {
				String[] parts = audio.split("/9/");
				String fileName = parts[1];
				fileName = fileName.replaceAll("^\\d+_", "");
				this.audio = "https://trumtuvung.com/audio/9/" + fileName;
			} else {
				audio = audio.replaceAll("^\\d+_", "");
				this.audio = "https://trumtuvung.com/audio/9/" + audio;
			}
		} else {
			this.audio = null;
		}
	}

	public String getImage() {
		if (image != null) {
			if (image.contains("/9/")) {
				String[] parts = image.split("/9/");
				String fileName = parts[1];
				fileName = fileName.replaceAll("^\\d+_", "");
				return "https://trumtuvung.com/images/9/" + fileName;
			}
			if (!image.startsWith("http")) {
				 image = image.replaceAll("^\\d+_", "");
				return "https://trumtuvung.com/images/9/" + image;
			}
		}
		return image;
	}

	public void setImage(String image) {
		if (image != null) {
			if (image.contains("/9/")) {
				String[] parts = image.split("/9/");
				String fileName = parts[1];
				fileName = fileName.replaceAll("^\\d+_", "");
				this.image = "https://trumtuvung.com/images/9/" + fileName;
			} else {
				image = image.replaceAll("^\\d+_", "");
				this.image = "https://trumtuvung.com/images/9/" + image;
			}
		} else {
			this.image = null;
		}
	}

	public Integer getLearnCount() {
		return learnCount != null ? learnCount : 0;
	}

	public void setLearnCount(Integer learnCount) {
		this.learnCount = learnCount != null ? learnCount : 0;
	}

	public Integer getMemLevel() {
		return memLevel != null ? memLevel : 0;
	}

	public void setMemLevel(Integer memLevel) {
		this.memLevel = memLevel != null ? memLevel : 0;
	}

	public LocalDateTime getLastLearn() {
		return lastLearn != null ? lastLearn : LocalDateTime.now();
	}

	public void setLastLearn(LocalDateTime lastLearn) {
		this.lastLearn = lastLearn != null ? lastLearn : LocalDateTime.now();
	}

	public Curriculum getCurriculum() {
		return curriculum;
	}

	public void setCurriculum(Curriculum curriculum) {
		this.curriculum = curriculum;
	}


}
