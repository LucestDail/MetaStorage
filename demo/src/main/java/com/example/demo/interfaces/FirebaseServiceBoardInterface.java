package com.example.demo.interfaces;

import java.util.List;

import com.example.demo.domain.Board;
import com.example.demo.domain.Member;

public interface FirebaseServiceBoardInterface {
	public String insertBoard(Board board, Member member) throws Exception;

	public Board getBoardDetail(String id, String save_team) throws Exception;

	public String updateBoard(Board board, Member member) throws Exception;

	public String deleteBoard(String id) throws Exception;
	
	public List<Board> getAllBoard(String save_team) throws Exception;
}
