package com.example.demo.interfaces;

import com.example.demo.domain.Member;

public interface FirebaseServiceMemberInterface {

	public String insertMember(Member member) throws Exception;

	public Member getMemberDetail(String id, String team) throws Exception;

	public String updateMember(Member member) throws Exception;

	public String deleteMember(String id) throws Exception;

}