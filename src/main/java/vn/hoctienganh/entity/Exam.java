package vn.hoctienganh.entity;

import java.util.List;

import lombok.*;

@Getter
@Setter
public class Exam {
    private User user;
    private List<Question> questions;

}
