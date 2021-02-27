package com.example.demo.interfaces;

import com.example.demo.domain.Meta;

public interface FirebaseServiceMetaInterface {

	public String insertMeta(Meta meta) throws Exception;

	public Meta getMetaDetail(String id) throws Exception;

	public String updateMeta(Meta meta) throws Exception;

	public String deleteMeta(String id) throws Exception;
}
