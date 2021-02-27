package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Member;
import com.example.demo.interfaces.FirebaseServiceMemberInterface;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
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
	public Member getMemberDetail(String id) throws Exception {
		Firestore firestore = FirestoreClient.getFirestore();
		DocumentReference documentReference = firestore.collection(COLLECTION_NAME).document(id);
		ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();
		DocumentSnapshot documentSnapshot = apiFuture.get();
		Member member = null;
		String teamKeyword = "tripamigo";
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
	

}
