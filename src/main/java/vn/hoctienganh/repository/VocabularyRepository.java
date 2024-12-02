package vn.hoctienganh.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import vn.hoctienganh.entity.Vocabulary;

public interface VocabularyRepository extends JpaRepository<Vocabulary, Integer> { 
	
}
