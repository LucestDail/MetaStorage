package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Meta;
import com.example.demo.interfaces.FirebaseServiceMetaInterface;
import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;


@Service
public class FirebaseServiceMetaInterfaceImpl implements FirebaseServiceMetaInterface {
	
	public static final String COLLECTION_NAME = "meta";
	
	@Override
	public String insertMeta(Meta meta) throws Exception {
		Firestore firestore = FirestoreClient.getFirestore();
		meta.setSave_date(Timestamp.now());
		//testing value, it would be session-login user
		meta.setSave_team("tripamigo");
		meta.setSave_name("admin");
		meta.setId(meta.getId()+"_"+meta.getSave_team());
		ApiFuture<com.google.cloud.firestore.WriteResult> apiFuture = firestore.collection(COLLECTION_NAME)
				.document(meta.getId()).set(meta);
		return apiFuture.get().getUpdateTime().toString();
	}

	@Override
	public Meta getMetaDetail(String id) throws Exception {
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
	public String updateMeta(Meta meta) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteMeta(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
