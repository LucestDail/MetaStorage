package com.example.demo.domain;

import java.util.Date;

import com.google.cloud.Timestamp;

import lombok.Data;

@Data
public class Meta {
	private String id;
	private String name_kor;
	private String name_eng;
	private String explanation;
	private String save_team;
	private Timestamp save_date;
	private String save_name;
}
