package com.example.demo.interfaces;

import com.example.demo.domain.Member;
import com.example.demo.domain.Meta;

public interface FirebaseServiceMetaInterface {

	public String insertMeta(Meta meta, Member member) throws Exception;

	public Meta getMetaDetail(String id, String team) throws Exception;

	public String updateMeta(Meta meta, Member member) throws Exception;

	public String deleteMeta(String id) throws Exception;
}
