package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.domain.Board;
import com.example.demo.domain.Member;
import com.example.demo.domain.Meta;
import com.example.demo.service.FirebaseServiceBoardInterfaceImpl;
import com.example.demo.service.FirebaseServiceMemberInterfaceImpl;
import com.example.demo.service.FirebaseServiceMetaInterfaceImpl;
import com.google.api.client.http.HttpRequest;

@org.springframework.web.bind.annotation.RestController
public class RestController {
	@Autowired
	FirebaseServiceMemberInterfaceImpl firebaseServiceMember;
	@Autowired
	FirebaseServiceMetaInterfaceImpl firebaseServiceMeta;
	@Autowired
	FirebaseServiceBoardInterfaceImpl firebaseServiceBoard;
	
	@GetMapping("/")
	public ModelAndView login(Model model) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("member/login.html");
		return mav;
	}
	
	@GetMapping("/main")
	public ModelAndView loginMain(Model model) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("main/main.html");
		return mav;
	}
	
	@GetMapping("/metasearch")
	public ModelAndView loginIndex(Model model) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("main/index.html");
		return mav;
	}
	
	@GetMapping("/register")
	public ModelAndView getRegister() {
		return new ModelAndView("member/register.html");
	}
	
	@GetMapping("/login")
	public ModelAndView getLoginform() {
		return new ModelAndView("main/login.html");
	}
	
	@PostMapping("/loginMember")
	public String postMemberLogin(@RequestBody Member member, HttpServletRequest request) throws Exception{
		try {
			if(firebaseServiceMember.memberLogin(member) != null) {
				Member logonMember = firebaseServiceMember.memberLogin(member);
				HttpSession session = request.getSession();
		    	session.setAttribute("member", firebaseServiceMember.getMemberDetail(member.getId(),logonMember.getTeam()));
				return "success";
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "fail";
	}
	
	@PostMapping("/insertMember")
    public String insertMember(@RequestBody Member member) throws Exception{
        return firebaseServiceMember.insertMember(member);
    }

    @GetMapping("/getMemberDetail")
    public ModelAndView getMemberDetail(@RequestParam String id, HttpServletRequest request) throws Exception{
    	ModelAndView mav = new ModelAndView();
    	Member sessionMember = (Member) request.getSession().getAttribute("member");
    	String team = sessionMember.getTeam();
    	Member member = firebaseServiceMember.getMemberDetail(id,team);
        mav.addObject(member);
        mav.setViewName("member/mypage.html");
        return mav;
    }

    @GetMapping("/mypage")
    public ModelAndView loginMypage(HttpSession session) {
    	return new ModelAndView("member/mypage.html");
    }
    
    @GetMapping("/getTeamDetail")
    public ModelAndView loginTeampage(@RequestParam String team) {
    	System.out.println(team);
    	ModelAndView mav = new ModelAndView();
    	List<Member> teamlist = new ArrayList<>();
    	teamlist = firebaseServiceMember.getAllMember(team);
    	mav.addObject("teamlist",teamlist);
    	mav.setViewName("member/teampage.html");
        return mav;
    }
    
    @GetMapping("/updateMember")
	public ModelAndView logingetUpdate() {
    	return new ModelAndView("member/update.html");
	}
    
    @PostMapping("/updateMember")
    public String loginUpdateMember(@RequestBody Member member, HttpServletRequest request) throws Exception{
    	System.out.println(member);
    	firebaseServiceMember.updateMember(member);
		HttpSession session = request.getSession();
    	Member sessionMember = (Member) request.getSession().getAttribute("member");
    	String team = sessionMember.getTeam();
    	session.setAttribute("member", firebaseServiceMember.getMemberDetail(member.getId(),team));
        return "success";
    }

    @GetMapping("/deleteMember")
    public ModelAndView loginDeleteMember(@RequestParam String id, HttpServletRequest request) throws Exception{
    	firebaseServiceMember.deleteMember(id);
    	HttpSession session = request.getSession();
    	session.invalidate();
        return new ModelAndView("member/login.html");
    }
    /*
    @GetMapping("/getMetaDetail")
    public Meta getMetaDetail(@RequestParam String id) throws Exception{
    	return firebaseServiceMeta.getMetaDetail(id);
    }
    */
    
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
    	HttpSession session = request.getSession();    	
    	session.invalidate();
    	return new ModelAndView("member/login.html");
    }
    
    @GetMapping("/getAllMeta")
    public ModelAndView loginGetAllMeta(HttpServletRequest request) throws Exception{ 
    	ModelAndView mav = new ModelAndView();
    	List<Meta> metalist = new ArrayList<>();
    	Member sessionMember = (Member) request.getSession().getAttribute("member");
    	String team = sessionMember.getTeam();
    	metalist = firebaseServiceMeta.getAllMeta(team);
    	for(Meta meta : metalist) {
    		meta.setId(meta.getId().substring(0,meta.getId().length() - meta.getSave_team().length()-1));
    	}
    	mav.addObject("metalist",metalist);
    	mav.setViewName("meta/result.html");
    	return mav;
    }
    
    @GetMapping("/getMetaDetail")
    public ModelAndView loginGetMetaDetail(@RequestParam String id, HttpServletRequest request) throws Exception{
    	System.out.println("metadetail run");
    	ModelAndView mav = new ModelAndView();
    	Member sessionMember = (Member) request.getSession().getAttribute("member");
    	String team = sessionMember.getTeam();
    	String searchid = id+"_"+team;
    	Meta meta = firebaseServiceMeta.getMetaDetail(searchid,team);
    	meta.setId(meta.getId().substring(0,meta.getId().length() - meta.getSave_team().length()-1));
    	mav.addObject("metaid",meta.getId());
    	List<Meta> metalist = new ArrayList<>();
    	metalist.add(meta);
    	mav.addObject("meta",meta);
    	mav.addObject("metalist",metalist);
    	mav.setViewName("meta/result.html");
    	return mav;
    }
    
    @GetMapping("/getMetaInfo")
    public ModelAndView loginGetMetaInfo(@RequestParam String id, HttpServletRequest request) throws Exception{
    	System.out.println("metadetail run");
    	ModelAndView mav = new ModelAndView();
    	Member sessionMember = (Member) request.getSession().getAttribute("member");
    	String team = sessionMember.getTeam();
    	String searchid = id+"_"+team;
    	Meta meta = firebaseServiceMeta.getMetaDetail(searchid,team);
    	meta.setId(meta.getId().substring(0,meta.getId().length() - meta.getSave_team().length()-1));
    	mav.addObject("metaid",meta.getId());
    	List<Meta> metalist = new ArrayList<>();
    	metalist.add(meta);
    	mav.addObject("meta",meta);
    	mav.addObject("metalist",metalist);
    	mav.setViewName("meta/metainfo.html");
    	return mav;
    }
    
    @GetMapping("/failurl")
    public ModelAndView loginGetFailUrl(@RequestParam String id) {
    	ModelAndView mav = new ModelAndView();
    	mav.addObject("id", id);
    	mav.setViewName("meta/failurl.html");
    	return mav;
    }
    
    @GetMapping("/insertMeta")
    public ModelAndView loginInsertMetaForm() {
    	return new ModelAndView("meta/insertmeta.html");
    }
    
    @PostMapping("/insertMeta")
    public String loginInsertMeta(@RequestBody Meta meta, HttpServletRequest request) throws Exception{
    	Member sessionMember = (Member) request.getSession().getAttribute("member");
        return firebaseServiceMeta.insertMeta(meta,sessionMember);
    }
    
    @GetMapping("/metaUpdate")
    public ModelAndView loginUpdateMetaForm(@RequestParam String id, HttpServletRequest request) {
    	Member sessionMember = (Member) request.getSession().getAttribute("member");
    	String team = sessionMember.getTeam();
    	String searchid = id+"_"+team;
    	Meta meta = null;
		try {
			meta = firebaseServiceMeta.getMetaDetail(searchid,team);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	meta.setId(meta.getId().substring(0,meta.getId().length() - meta.getSave_team().length()-1));
    	ModelAndView mav = new ModelAndView();
    	mav.addObject("meta",meta);
    	mav.setViewName("meta/metaupdate.html");
    	return mav;
    }
    
    @PostMapping("/metaUpdate")
    public String loginUpdateMeta(@RequestBody Meta inputMeta, HttpServletRequest request) {
    	Member sessionMember = (Member) request.getSession().getAttribute("member");
    	String team = sessionMember.getTeam();
    	String searchid = inputMeta.getId()+"_"+team;
    	Meta meta = null;
		try {
			meta = firebaseServiceMeta.getMetaDetail(searchid,team);
			if(firebaseServiceMeta.updateMeta(inputMeta, sessionMember) != null) {
				return "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return "fail";
    }
    
    @GetMapping("/metaDelete")
    public ModelAndView loginDeleteMeta(@RequestParam String id) {
    	try {
			firebaseServiceMeta.deleteMeta(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return new ModelAndView("main/index.html");
    }
    
    
    
    @GetMapping("/insertBoard")
    public ModelAndView insertBoard() {
    	return new ModelAndView("board/insertboard.html");
    }
    
    @PostMapping("/insertBoard")
    public String insertBoard(@RequestBody Board board, HttpServletRequest request) throws Exception{
    	Member sessionMember = (Member) request.getSession().getAttribute("member");
        return firebaseServiceBoard.insertBoard(board,sessionMember);
    }
    
    @GetMapping("/updateBoard")
    public ModelAndView updateBoard(@RequestParam String id, HttpServletRequest request) {
    	Member sessionMember = (Member) request.getSession().getAttribute("member");
    	String team = sessionMember.getTeam();
    	String searchid = id;
    	Board board = null;
		try {
			board = firebaseServiceBoard.getBoardDetail(searchid,team);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	ModelAndView mav = new ModelAndView();
    	mav.addObject("board",board);
    	mav.setViewName("board/updateboard.html");
    	return mav;
    }
    
    @PostMapping("/updateBoard")
    public String updateBoard(@RequestBody Board inputBoard, HttpServletRequest request) {
    	System.out.println("update : " + inputBoard);
    	Member sessionMember = (Member) request.getSession().getAttribute("member");
    	String team = sessionMember.getTeam();
    	String searchid = inputBoard.getId();
    	Board board = null;
		try {
			board = firebaseServiceBoard.getBoardDetail(searchid,team);
			if(firebaseServiceBoard.updateBoard(inputBoard, sessionMember) != null) {
				return "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return "fail";
    }
    
    @GetMapping("/boardlist")
    public ModelAndView boardlist(HttpServletRequest request) throws Exception{ 
    	ModelAndView mav = new ModelAndView();
    	List<Board> boardlist = new ArrayList<>();
    	Member sessionMember = (Member) request.getSession().getAttribute("member");
    	String team = sessionMember.getTeam();
    	boardlist = firebaseServiceBoard.getAllBoard(team);
    	mav.addObject("boardlist",boardlist);
    	mav.setViewName("board/boardlist.html");
    	return mav;
    }
    
    @GetMapping("/infoBoard")
    public ModelAndView infoBoard(@RequestParam String id, HttpServletRequest request) throws Exception{
    	ModelAndView mav = new ModelAndView();
    	Member sessionMember = (Member) request.getSession().getAttribute("member");
    	String team = sessionMember.getTeam();
    	String searchid = id;
    	Board board = firebaseServiceBoard.getBoardDetail(searchid,team);
    	System.out.println(board);
    	mav.addObject("board",board);
    	mav.setViewName("board/infoboard.html");
    	return mav;
    }
    
    @GetMapping("/deleteBoard")
    public ModelAndView deleteBoard(@RequestParam String id) {
    	try {
			firebaseServiceBoard.deleteBoard(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return new ModelAndView("main/main.html");
    }
}
