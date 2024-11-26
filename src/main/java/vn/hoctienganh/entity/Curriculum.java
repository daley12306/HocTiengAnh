package vn.hoctienganh.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "curriculum")
public class Curriculum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "curriculum", cascade = CascadeType.ALL)
    private List<Vocabulary> vocabularies;

    // Getters and Setters
}
