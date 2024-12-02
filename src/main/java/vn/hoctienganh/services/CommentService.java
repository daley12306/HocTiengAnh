package vn.hoctienganh.services;

import vn.hoctienganh.entity.Comment;

public interface CommentService {

	void deleteComment(Integer id);

	Comment saveComment(Comment comment);

	Comment getCommentById(Integer commentId);

}
