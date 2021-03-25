package com.example.demo.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.example.demo.auth.GoogleOauthImpl;
import com.example.demo.auth.SocialLoginType;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class OauthService {
	private final GoogleOauthImpl googleOauth;
	
	private final HttpServletResponse response;
	
    public void request(SocialLoginType socialLoginType) {
        String redirectURL;
        switch (socialLoginType) {
            case GOOGLE: {
                redirectURL = googleOauth.getOauthRedirectURL();
            } break;
            default: {
                throw new IllegalArgumentException("Error Occured : OauthService : request -> " + socialLoginType);
            }
        }
        try {
            response.sendRedirect(redirectURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
