package vn.hoctienganh.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String content;
    private List<String> choices;
    private char correctAnswer;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;

    // Getters and Setters
}
