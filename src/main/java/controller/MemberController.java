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

	//ȸ�� ������ ������
	@ModelAttribute("memberCommand")
	public MemberCommand get(){
		return new MemberCommand();
	}
	
	
	//�α��� ������ ��û
	@RequestMapping(value="/logon/login.do",method=RequestMethod.GET)
	public String loginform(HttpServletRequest request, HttpSession session){
		String login_status = (String) session.getAttribute("login_status");
		if(login_status == null){
			session.setAttribute("login_status", "2");
			login_status = "2";
		}
		Cookie[] cookies = request.getCookies();
		for(int i=0; i<cookies.length; i++) {
			if(cookies[i].getName().equals("autoLogin")) {
				session.setAttribute("id", cookies[i].getValue());
				session.setAttribute("login_status", "1");
				login_status = "1";
				if(cookies[i].getValue().equals("admin")) {
					session.setAttribute("login_status", "0");
					login_status = "0";
				}
			}
		}
		if(login_status.equals("0") || login_status.equals("1")) {
			return "redirect:/main/main.do";
		}
		return "logon/loginForm";
	}
	
	@RequestMapping(value="/logon/login.do",method=RequestMethod.POST)
	public String login(HttpSession session, HttpServletResponse resp, String id, String passwd, Model model, String autologin){
		
		
		MemberCommand memberInfo = memberDao.loginPro(id);
		String message = null;
		if (memberInfo!=null) {
			if ((memberInfo.getPasswd()).equals(passwd)) {
				
				session.setAttribute("id", id);
				session.setAttribute("login_status", "1");
				if(id.equals("admin")) {
					session.setAttribute("login_status", "0");
				}
				if(autologin!=null) {
					Cookie cookie = new Cookie("autoLogin", id);
					cookie.setMaxAge(60*60*24*30);
					cookie.setPath("/");
					resp.addCookie(cookie);
				}
			} else {
				message = "errPwd";
			}
	
		} else {
			message = "errID";
		}
		
		model.addAttribute("message", message);
		
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
		String id = (String) session.getAttribute("id");
		model.addAttribute("id", id);
		
		MemberCommand memberInfo = memberDao.modifyForm(id);
		model.addAttribute("m", memberInfo);
		
		return "member/modifyForm";
	}
	
	
	@RequestMapping(value="/member/modify.do",method=RequestMethod.POST)
	public String submit(MemberCommand memberInfo) {
		
		memberDao.modifyPro(memberInfo);
		
		return "redirect:/main/main.do";
	}
	
	
	@RequestMapping(value="/member/delete.do", method=RequestMethod.GET)
	public String deleteForm(HttpServletRequest request,Model model){
		HttpSession session = request.getSession();
		
		String id = (String) session.getAttribute("id");
		model.addAttribute("id", id);
		
		return "member/deleteForm";
	}       

    	
	@RequestMapping(value="/member/delete.do", method=RequestMethod.POST)
	public String delete(HttpServletRequest request, HttpServletResponse resp, Model model){
		
		HttpSession session = request.getSession();
		
		String errorPasswd = null;
		String id = (String)request.getSession().getAttribute("id");
		String passwd = request.getParameter("passwd");
		String SavePasswd = memberDao.getPasswdById(id);
		
		if (passwd.equals(SavePasswd)) {
			List<BoardCommand> bc = new ArrayList<BoardCommand>();
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



	
	
	
	
