package vn.hoctienganh.entity;

import java.time.LocalDateTime;

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
    private String audio;
    private String image;

    @ManyToOne
    @JoinColumn(name = "curriculum_id")
    private Curriculum curriculum;

    // Getters and Setters
}