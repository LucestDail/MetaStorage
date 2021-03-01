package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Member;
import com.example.demo.domain.Meta;
import com.example.demo.interfaces.FirebaseServiceMemberInterface;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class FirebaseServiceMemberInterfaceImpl implements FirebaseServiceMemberInterface {
	
	public static final String COLLECTION_NAME = "member";

	@Override
	public String insertMember(Member member) throws Exception {
		Firestore firestore = FirestoreClient.getFirestore();
		ApiFuture<com.google.cloud.firestore.WriteResult> apiFuture = firestore.collection(COLLECTION_NAME)
				.document(member.getId()).set(member);
		return apiFuture.get().getUpdateTime().toString();
	}

	@Override
	public Member getMemberDetail(String id, String team) throws Exception {
		Firestore firestore = FirestoreClient.getFirestore();
		DocumentReference documentReference = firestore.collection(COLLECTION_NAME).document(id);
		ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();
		DocumentSnapshot documentSnapshot = apiFuture.get();
		Member member = null;
		String teamKeyword = team;
		if (documentSnapshot.exists()) {
			member = documentSnapshot.toObject(Member.class);
			if(member.getTeam().equals(teamKeyword)) {
				return member;
			}else {
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public String updateMember(Member member) throws Exception {
		Firestore firestore = FirestoreClient.getFirestore();
		ApiFuture<com.google.cloud.firestore.WriteResult> apiFuture = firestore.collection(COLLECTION_NAME)
				.document(member.getId()).set(member);
		return apiFuture.get().getUpdateTime().toString();
	}

	@Override
	public String deleteMember(String id) throws Exception {
		Firestore firestore = FirestoreClient.getFirestore();
		ApiFuture<WriteResult> apiFuture = firestore.collection(COLLECTION_NAME).document(id).delete();
		return "Document id: " + id + " delete";
	}

	public Member memberLogin(Member member) {
		try {
			Firestore firestore = FirestoreClient.getFirestore();
			DocumentReference documentReference = firestore.collection(COLLECTION_NAME).document(member.getId());
			ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();
			DocumentSnapshot documentSnapshot = apiFuture.get();
			if (documentSnapshot.exists()) {
				Member curMember = documentSnapshot.toObject(Member.class);
				if(curMember.getPassword().equals(member.getPassword())) {
					return curMember;
				}else {
					return null;
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Member> getAllMember(String team) {
		String teamname = team;
		List<Member> teamlist = new ArrayList<>();
		try {
			Firestore firestore = FirestoreClient.getFirestore();
			ApiFuture<QuerySnapshot> future = firestore.collection(COLLECTION_NAME).get();
			List<QueryDocumentSnapshot> documents = future.get().getDocuments();
			for(QueryDocumentSnapshot document : documents) {
				Member member = document.toObject(Member.class);
				if(member.getTeam().equals(teamname))
					teamlist.add(document.toObject(Member.class));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(teamlist);
		return teamlist;
	}
}
