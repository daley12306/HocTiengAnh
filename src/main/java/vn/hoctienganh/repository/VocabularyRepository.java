package vn.hoctienganh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoctienganh.entity.Vocabulary;

@Repository
public interface VocabularyRepository extends JpaRepository<Vocabulary, Integer> {

}
