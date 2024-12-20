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

    @Column( columnDefinition = "NVARCHAR(MAX) COLLATE Vietnamese_CI_AS")
    private String name;

    @ManyToMany(mappedBy = "curriculums", cascade = CascadeType.ALL)
    private List<StudyRecord> studyRecords;
   
    
    @OneToMany(mappedBy = "curriculum", cascade = CascadeType.ALL)
    private List<Vocabulary> vocabularies;
    // Getters and Setters
    
    @Override
	public String toString() {
		return "Curriculum{" + "id = " + id + ", name = '" + name + '\'' + '}';
	}
}
