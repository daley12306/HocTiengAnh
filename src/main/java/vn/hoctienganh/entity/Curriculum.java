package vn.hoctienganh.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "curriculum")
public class Curriculum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany(mappedBy = "curriculums", cascade = CascadeType.ALL)
    private List<StudyRecord> studyRecords;
    
    @Column(name = "completion_percentage")
    private int completionPercentage;
    
    @OneToMany(mappedBy = "curriculum", cascade = CascadeType.ALL)
    private List<Vocabulary> vocabularies;
    // Getters and Setters
}
