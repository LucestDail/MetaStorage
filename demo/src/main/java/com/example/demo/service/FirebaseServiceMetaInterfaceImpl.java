package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Member;
import com.example.demo.domain.Meta;
import com.example.demo.interfaces.FirebaseServiceMetaInterface;
import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;


@Service
public class FirebaseServiceMetaInterfaceImpl implements FirebaseServiceMetaInterface {
	
	public static final String COLLECTION_NAME = "meta";
	
	@Override
	public String insertMeta(Meta meta, Member member) throws Exception {
		Firestore firestore = FirestoreClient.getFirestore();
		meta.setSave_date(Timestamp.now());
		meta.setSave_team(member.getTeam());
		meta.setSave_name(member.getName());
		meta.setId(meta.getId()+"_"+meta.getSave_team());
		ApiFuture<com.google.cloud.firestore.WriteResult> apiFuture = firestore.collection(COLLECTION_NAME)
				.document(meta.getId()).set(meta);
		return apiFuture.get().getUpdateTime().toString();
	}

	@Override
	public Meta getMetaDetail(String id, String team) throws Exception {
		Firestore firestore = FirestoreClient.getFirestore();
		DocumentReference documentReference = firestore.collection(COLLECTION_NAME).document(id);
		ApiFuture<DocumentSnapshot> apiFuture = documentReference.get();
		DocumentSnapshot documentSnapshot = apiFuture.get();
		Meta meta = null;
		if (documentSnapshot.exists()) {
			meta = documentSnapshot.toObject(Meta.class);
			return meta;
		} else {
			System.out.println(id + " is not exist");
			return null;
		}
	}

	@Override
	public String updateMeta(Meta meta, Member member) throws Exception {
		meta.setSave_date(Timestamp.now());
		meta.setSave_team(member.getTeam());
		meta.setSave_name(member.getName());
		meta.setId(meta.getId()+"_"+meta.getSave_team());
		System.out.println(meta);
		Firestore firestore = FirestoreClient.getFirestore();
		ApiFuture<com.google.cloud.firestore.WriteResult> apiFuture = firestore.collection(COLLECTION_NAME)
				.document(meta.getId()).set(meta);
		return apiFuture.get().getUpdateTime().toString();
	}

	@Override
	public String deleteMeta(String id) throws Exception {
		Firestore firestore = FirestoreClient.getFirestore();
		ApiFuture<WriteResult> apiFuture = firestore.collection(COLLECTION_NAME).document(id).delete();
		return "Document id: " + id + " delete";
	}

	public List<Meta> getAllMeta(String team) {
		String teamname = team;
		List<Meta> metalist = new ArrayList<>();
		try {
			Firestore firestore = FirestoreClient.getFirestore();
			ApiFuture<QuerySnapshot> future = firestore.collection(COLLECTION_NAME).get();
			List<QueryDocumentSnapshot> documents = future.get().getDocuments();
			for(QueryDocumentSnapshot document : documents) {
				Meta meta = document.toObject(Meta.class);
				if(meta.getSave_team().equals(teamname))
					metalist.add(document.toObject(Meta.class));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return metalist;
	}

}
