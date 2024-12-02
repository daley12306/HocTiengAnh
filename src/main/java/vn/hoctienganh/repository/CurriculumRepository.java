package vn.hoctienganh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoctienganh.entity.Curriculum;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Integer>{

}
