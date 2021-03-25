package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Board;
import com.example.demo.domain.Comment;
import com.example.demo.domain.Member;
import com.example.demo.interfaces.FirebaseServiceCommentInterface;
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
public class FirebaseServiceCommentInterfaceImpl implements FirebaseServiceCommentInterface {

	public static final String COLLECTION_NAME="comment";
	
	@Override
	public String insertComment(Comment comment, Board board, Member member) throws Exception {
		
		Firestore firestore = FirestoreClient.getFirestore();
		comment.setSave_date(Timestamp.now());
		comment.setSave_team(member.getTeam());
		comment.setSave_name(member.getName());
		comment.setBoardId(board.getId());
		String Randnum = String.valueOf(Math.random());
		comment.setId(comment.getSave_name() +"_"+ board.getSave_team() + "_" + Randnum);
		ApiFuture<com.google.cloud.firestore.WriteResult> apiFuture = firestore.collection(COLLECTION_NAME)
				.document(comment.getId()).set(comment);
		return apiFuture.get().getUpdateTime().toString();
	}

	@Override
	public String updateComment(Comment comment, Member member) throws Exception {
		comment.setSave_date(Timestamp.now());
		comment.setSave_team(member.getTeam());
		comment.setSave_name(member.getName());
		Firestore firestore = FirestoreClient.getFirestore();
		ApiFuture<com.google.cloud.firestore.WriteResult> apiFuture = firestore.collection(COLLECTION_NAME)
				.document(comment.getId()).set(comment);
		return apiFuture.get().getUpdateTime().toString();
	}

	@Override
	public String deleteComment(String id) {
		Firestore firestore = FirestoreClient.getFirestore();
		ApiFuture<WriteResult> apiFuture = firestore.collection(COLLECTION_NAME).document(id).delete();
		return "Document id: " + id + " delete";
	}

	@Override
	public List<Comment> getAllComment(Board board, Member member) throws Exception {
		String teamname = member.getTeam();
		List<Comment> commentlist = new ArrayList<>();
		try {
			Firestore firestore = FirestoreClient.getFirestore();
			DocumentReference documentReference = firestore.collection(COLLECTION_NAME).document(teamname);
			ApiFuture<QuerySnapshot> future = firestore.collection(COLLECTION_NAME).whereEqualTo("boardId", board.getId()).orderBy("save_date",Query.Direction.DESCENDING).get();
			List<QueryDocumentSnapshot> documents = future.get().getDocuments();
			for(QueryDocumentSnapshot document : documents) {
				Comment comment = document.toObject(Comment.class);
				if(comment.getSave_team().equals(teamname))
					commentlist.add(document.toObject(Comment.class));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return commentlist;
	}
	
	public Comment getCommentById(String id) throws Exception{
		Firestore firestore = FirestoreClient.getFirestore();
		DocumentReference documentReference = firestore.collection(COLLECTION_NAME).document(id);
		ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();
		DocumentSnapshot documentSnapshot = apiFuture.get();
		Comment comment = null;
		if (documentSnapshot.exists()) {
			comment = documentSnapshot.toObject(Comment.class);
			return comment;
		} else {
			System.out.println(id + " is not exist");
			return null;
		}
	}

}
