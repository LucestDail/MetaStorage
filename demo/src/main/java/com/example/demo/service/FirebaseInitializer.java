package com.example.demo.service;

import java.io.FileInputStream;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Service
public class FirebaseInitializer {
	@PostConstruct
	public void initialize() {
		try {
			FileInputStream serviceAccount = new FileInputStream("./springboottestkey.json");
			FirebaseApp firebaseApp = null;
			List<FirebaseApp> firebaseApps = FirebaseApp.getApps();
			if(firebaseApps != null && !firebaseApps.isEmpty()) {
				for(FirebaseApp app : firebaseApps) {
					if(app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)) {
						firebaseApp = app;
					}
				}
			}else {
				FirebaseOptions options = new FirebaseOptions.Builder()
						.setCredentials(GoogleCredentials.fromStream(serviceAccount))
						.setDatabaseUrl("https://springboottest-4fb38.firebaseio.com")
						.build();
				FirebaseApp.initializeApp(options);
			}
			
			/*
			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setDatabaseUrl("https://springboottest-4fb38.firebaseio.com")
					.build();
			FirebaseApp.initializeApp(options);
			*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}