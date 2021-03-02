package com.example.demo.domain;

import com.google.cloud.Timestamp;

import lombok.Data;

@Data
public class Board {
	private String id;
	private String title;
	private String content;
	private String save_team;
	private Timestamp save_date;
	private String save_name;
}
