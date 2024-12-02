package vn.hoctienganh.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;


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
    private String audio;
    private String image;
    @Column(name = "memLevel", columnDefinition = "INT DEFAULT 0")
    private int memLevel = 0; // Giá trị mặc định là 0

    @Column(name = "lastLearn", columnDefinition = "DATETIME DEFAULT NULL")
    private LocalDateTime lastLearn = null; // Giá trị mặc định là NULL

    @Column(name = "learnCount", columnDefinition = "INT DEFAULT 0")
    private int learnCount = 0; // Giá trị mặc định là 0

    @ManyToOne
    @JoinColumn(name = "curriculum_id")
    private Curriculum curriculum;

    // Getters and Setters
    
 // Getters and Setters

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public void setPronunciation(String pronunciation) {
        this.pronunciation = pronunciation;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
    
    
    private String match;  // Trường mới dùng để lưu kết quả thay thế

 // Getter và Setter cho match (phải thêm phương thức này)
    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }
}

