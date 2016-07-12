package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import command.BoardCommand;
import command.MemberCommand;


@Controller
public class MemberController extends BaseController {

	//member 데이터를 모델로 사용
	@ModelAttribute("memberCommand")
	public MemberCommand get(){
		return new MemberCommand();
	}
	
	
	// 로그인 페이지 요청
	@RequestMapping(value="/logon/login.do",method=RequestMethod.GET)
	public String loginform(HttpServletRequest request, HttpSession session){
		String login_status = (String) session.getAttribute("login_status");
		if(login_status == null){
			session.setAttribute("login_status", "2"); // 비로그인 상태 세션 KEY,VALUE 저장
			login_status = "2";
		}
		Cookie[] cookies = request.getCookies(); // 쿠키요청
		for(int i=0; i<cookies.length; i++) {
			if(cookies[i].getName().equals("autoLogin")) {
				session.setAttribute("id", cookies[i].getValue()); // 로그인 id 쿠키 값 저장
				session.setAttribute("login_status", "1"); // 일반회원 로그인 상태 세션 KEY,VALUE 저장
				login_status = "1";
				if(cookies[i].getValue().equals("admin")) {
					session.setAttribute("login_status", "0"); // 관리자회원 로그인 상태 세션 KEY,VALUE 저장
					login_status = "0";
				}
			}
		}
		if(login_status.equals("0") || login_status.equals("1")) {
			return "redirect:/main/main.do"; // 로그인 상태일 경우 메인페이지 요청
		}
		return "logon/loginForm"; // 아니면 로그인페이지로 이동
	}
	
	@RequestMapping(value="/logon/login.do",method=RequestMethod.POST)
	public String login(HttpSession session, HttpServletResponse resp, String id, String passwd, Model model, String autologin){
		
		
		MemberCommand memberInfo = memberDao.loginPro(id); // 해당아이디의 비밀번호 정보/ select passwd from members where id=#{id}
		String message = null;
		if (memberInfo!=null) { //
			if ((memberInfo.getPasswd()).equals(passwd)) { //회원 비밀번호가 일치한하면 로그인 성공!
				
				session.setAttribute("id", id); // 현재 로그인 상태
				session.setAttribute("login_status", "1"); // 개인회원 로그인 상태 세션 KEY,VALUE 저장
				if(id.equals("admin")) {
					session.setAttribute("login_status", "0"); // 관리자 회원 로그인 상태 세션 KEY,VALUE 저장
				}
				if(autologin!=null) {
					Cookie cookie = new Cookie("autoLogin", id); //자동로그인 상태 세션 KEY, 회원 아이디세션 VALUE 저장
					cookie.setMaxAge(60*60*24*30);
					cookie.setPath("/");
					resp.addCookie(cookie); // 자동로그인 상태 쿠키값으로 추가
				}
			} else {
				message = "errPwd"; // 비밀번호 불일치 할 경우 오류 발생
			}
	
		} else {
			message = "errID"; // 회원ID 불일치 할 경우 오류 발생
		}
		
		model.addAttribute("message", message); // 오류메시지 세션 KEY,VALUE 저장 
		
		return "logon/loginPro";
	}

	@RequestMapping("/logon/logout.do")
	public String logout(HttpServletRequest request, HttpServletResponse resp, HttpSession session){
		session.invalidate();
		Cookie[] cookies = request.getCookies();
		for(int i=0; i<cookies.length; i++) {
			if(cookies[i].getName().equals("autoLogin")) {
				cookies[i].setMaxAge(0);
				cookies[i].setPath("/");
				resp.addCookie(cookies[i]);
			}
		}
		return "redirect:/logon/login.do";
	}
	
	@RequestMapping(value="/member/modify.do", method=RequestMethod.GET)
	public String form(HttpServletRequest request, Model model) {
		
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id"); //현재 로그인 아이디 세션 값
		model.addAttribute("id", id); // 현재 로그인 아이디 세션 KEY,VALUE 저장
		
		MemberCommand memberInfo = memberDao.modifyForm(id); // 해당회원의 정보 /  select * from members where id=#{id}
		model.addAttribute("m", memberInfo); // 해당 회원의 정보 세션 KEY,VALUE 저장
		
		return "member/modifyForm";
	}
	
	
	@RequestMapping(value="/member/modify.do",method=RequestMethod.POST)
	public String submit(MemberCommand memberInfo) {
		
		memberDao.modifyPro(memberInfo); // 해당회원의 수정 할 정보 / update members set passwd=#{passwd},name=#{name},birth=#{birth},gender=#{gender},email=#{email},phone_num=#{phone_num} where id=#{id}
		
		return "redirect:/main/main.do";
	}
	
	
	@RequestMapping(value="/member/delete.do", method=RequestMethod.GET)
	public String deleteForm(HttpServletRequest request,Model model){
		HttpSession session = request.getSession();
		
		String id = (String) session.getAttribute("id"); // 로그인 상태에 있는 해당 아이디 세션 값
		model.addAttribute("id", id); // 로그인 상태 해당 id KEY,VALUE 값 세션으로 저장
		
		return "member/deleteForm";
	}       

    	
	@RequestMapping(value="/member/delete.do", method=RequestMethod.POST)
	public String delete(HttpServletRequest request, HttpServletResponse resp, Model model){
		
		HttpSession session = request.getSession();
		
		String errorPasswd = null;
		String id = (String)request.getSession().getAttribute("id"); // 로그인 상태에 있는 해당 아이디 세션 값
		String passwd = request.getParameter("passwd"); // 파라미터 값으로 받아올 passwd 값 저장
		String SavePasswd = memberDao.getPasswdById(id); // 해당회원의 passwd 정보 / select passwd from members where id=#{id}
		
		if (passwd.equals(SavePasswd)) {
			List<BoardCommand> bc = new ArrayList<BoardCommand>(); //
			bc = boardDao.getList();
			for(BoardCommand bdcmd : bc){
				if(bdcmd.getId().equals(id)){
					reportDao.deleteReport(bdcmd.getBoard_num());
				}
			}
			reportDao.deleteReportById(id);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("id", id);
			map.put("passwd", passwd);
			memberDao.delete(map);
			session.invalidate();
			
			Cookie[] cookies = request.getCookies();
			for(int i=0; i<cookies.length; i++) {
				if(cookies[i].getName().equals("autoLogin")) {
					cookies[i].setMaxAge(0);
					cookies[i].setPath("/");
					resp.addCookie(cookies[i]);
				}
			}
			return "logon/loginForm";
		}
		else 
		{
			errorPasswd = "errorPasswd";
		}
		model.addAttribute("errorPasswd", errorPasswd);
		return "member/deleteForm";
	}
	
}



	
	
	
	
