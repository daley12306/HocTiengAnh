package vn.hoctienganh.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private String audio;
    private String image;

    @ManyToOne
    @JoinColumn(name = "curriculum_id")
    @JsonIgnore
    private Curriculum curriculum;
    
    private String match;  // Trường mới dùng để lưu kết quả thay thế
    
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
}
