package com.example.demo.interfaces;

import java.util.List;

import com.example.demo.domain.Board;
import com.example.demo.domain.Comment;
import com.example.demo.domain.Member;

public interface FirebaseServiceCommentInterface {
	
	public String insertComment(Comment comment, Board board, Member member) throws Exception;
	
	public String updateComment(Comment comment, Member member) throws Exception;
	
	public String deleteComment(String id);
	
	public List<Comment> getAllComment(Board board, Member member) throws Exception;
}
