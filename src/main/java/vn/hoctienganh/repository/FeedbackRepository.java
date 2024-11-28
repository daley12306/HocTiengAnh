package vn.hoctienganh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoctienganh.entity.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long>{

}
