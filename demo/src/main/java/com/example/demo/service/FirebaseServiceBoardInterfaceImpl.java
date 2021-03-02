package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Board;
import com.example.demo.domain.Member;
import com.example.demo.domain.Meta;
import com.example.demo.interfaces.FirebaseServiceBoardInterface;
import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class FirebaseServiceBoardInterfaceImpl implements FirebaseServiceBoardInterface {

	public static final String COLLECTION_NAME = "board";
	
	@Override
	public String insertBoard(Board board, Member member) throws Exception {
		Firestore firestore = FirestoreClient.getFirestore();
		board.setSave_date(Timestamp.now());
		board.setSave_team(member.getTeam());
		board.setSave_name(member.getName());
		String Randnum = String.valueOf(Math.random());
		board.setId(board.getSave_name()+"_"+board.getSave_team()+"_"+Randnum);
		ApiFuture<com.google.cloud.firestore.WriteResult> apiFuture = firestore.collection(COLLECTION_NAME)
				.document(board.getId()).set(board);
		return apiFuture.get().getUpdateTime().toString();
	}

	@Override
	public Board getBoardDetail(String id, String save_team) throws Exception {
		Firestore firestore = FirestoreClient.getFirestore();
		DocumentReference documentReference = firestore.collection(COLLECTION_NAME).document(id);
		ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();
		DocumentSnapshot documentSnapshot = apiFuture.get();
		Board board = null;
		if (documentSnapshot.exists()) {
			board = documentSnapshot.toObject(Board.class);
			return board;
		} else {
			System.out.println(id + " is not exist");
			return null;
		}
	}

	@Override
	public String updateBoard(Board board, Member member) throws Exception {
		board.setSave_date(Timestamp.now());
		board.setSave_team(member.getTeam());
		board.setSave_name(member.getName());
		Firestore firestore = FirestoreClient.getFirestore();
		ApiFuture<com.google.cloud.firestore.WriteResult> apiFuture = firestore.collection(COLLECTION_NAME)
				.document(board.getId()).set(board);
		return apiFuture.get().getUpdateTime().toString();
	}

	@Override
	public String deleteBoard(String id) throws Exception {
		Firestore firestore = FirestoreClient.getFirestore();
		ApiFuture<WriteResult> apiFuture = firestore.collection(COLLECTION_NAME).document(id).delete();
		return "Document id: " + id + " delete";
	}

	@Override
	public List<Board> getAllBoard(String save_team) throws Exception {
		String teamname = save_team;
		List<Board> boardlist = new ArrayList<>();
		try {
			Firestore firestore = FirestoreClient.getFirestore();
			ApiFuture<QuerySnapshot> future = firestore.collection(COLLECTION_NAME).orderBy("save_date",Query.Direction.DESCENDING).get();
			List<QueryDocumentSnapshot> documents = future.get().getDocuments();
			for(QueryDocumentSnapshot document : documents) {
				Board board = document.toObject(Board.class);
				if(board.getSave_team().equals(teamname))
					boardlist.add(document.toObject(Board.class));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boardlist;
	}

}
