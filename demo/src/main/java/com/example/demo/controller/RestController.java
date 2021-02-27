package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.domain.Member;
import com.example.demo.domain.Meta;
import com.example.demo.service.FirebaseServiceMemberInterfaceImpl;
import com.example.demo.service.FirebaseServiceMetaInterfaceImpl;

@org.springframework.web.bind.annotation.RestController
public class RestController {
	@Autowired
	FirebaseServiceMemberInterfaceImpl firebaseServiceMember;
	@Autowired
	FirebaseServiceMetaInterfaceImpl firebaseServiceMeta;
	
	@GetMapping("/")
	public ModelAndView index(Model model) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("main/index.html");
		return mav;
	}
	
	@GetMapping("/insertMember")
    public String insertMember(@RequestParam Member member) throws Exception{
        return firebaseServiceMember.insertMember(member);
    }

    @GetMapping("/getMemberDetail")
    public Member getMemberDetail(@RequestParam String id) throws Exception{
        return firebaseServiceMember.getMemberDetail(id);
    }

    @GetMapping("/updateMember")
    public String updateMember(@RequestParam Member member) throws Exception{
        return firebaseServiceMember.updateMember(member);
    }

    @GetMapping("/deleteMember")
    public String deleteMember(@RequestParam String id) throws Exception{
        return firebaseServiceMember.deleteMember(id);
    }
    /*
    @GetMapping("/getMetaDetail")
    public Meta getMetaDetail(@RequestParam String id) throws Exception{
    	return firebaseServiceMeta.getMetaDetail(id);
    }
    */
    
    @GetMapping("/getAllMeta")
    public ModelAndView getAllMeta() throws Exception{
    	ModelAndView mav = new ModelAndView();
    	return mav;
    }
    
    @GetMapping("/getMetaDetail")
    public ModelAndView getMetaDetail(@RequestParam String id) throws Exception{
    	ModelAndView mav = new ModelAndView();
    	Meta meta = firebaseServiceMeta.getMetaDetail(id);
    	System.out.println(meta);
    	mav.addObject("meta",meta);
    	mav.setViewName("meta/result.html");
    	return mav;
    }
    
    @GetMapping("/failurl")
    public String searchfail() {
    	return "your result is empty";
    }
    
}
