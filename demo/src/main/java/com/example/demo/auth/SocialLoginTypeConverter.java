package com.example.demo.auth;

import org.springframework.core.convert.converter.Converter;

public class SocialLoginTypeConverter implements Converter<String, SocialLoginType> {

	@Override
	public SocialLoginType convert(String source) {
		// TODO Auto-generated method stub
		return SocialLoginType.valueOf(source.toUpperCase());
	}

}
