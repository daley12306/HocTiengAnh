package vn.hoctienganh.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "answer")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private char selectedAnswer;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;

    // Getters and Setters
}
