package vn.hoctienganh.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.hoctienganh.entity.Comment;
import vn.hoctienganh.repository.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {
	 @Autowired
	    private CommentRepository commentRepository;
	 
	 	@Transactional
	    @Override
		public Comment saveComment(Comment comment) {
	        return commentRepository.save(comment);
	    }
	 	
	    @Transactional
	    @Override
		public void deleteComment(Integer id) {
	        commentRepository.deleteById(id);
	    }
	    
		@Override
		public Comment getCommentById(Integer commentId) {
			return commentRepository.findById(commentId)
	                .orElseThrow(() -> new RuntimeException("Comment not found"));
		}

}
