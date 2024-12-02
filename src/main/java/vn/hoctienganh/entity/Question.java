package vn.hoctienganh.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
public class Question {
    private Integer id;
    private String content;
    private List<String> choices;
    private String correctAnswer;
    private Integer curriculumId;
}
