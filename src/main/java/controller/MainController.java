package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dao.MemberDAO;
import command.MemberCommand;


@Controller
public class MainController {
	@Autowired
	private MemberDAO memberDao;
	

	public void setMemberDao(MemberDAO memberDao) {
		this.memberDao = memberDao;
	}
	
	
	//로그인 성공 후 메인화면
	@RequestMapping(value="/login.do",method=RequestMethod.POST)
	public String login(HttpServletRequest request,String id, String pass){
		
		HttpSession session = request.getSession();
		
		MemberCommand memberInfo = memberDao.loginPro(id);
		
		if (memberInfo!=null) {
			if ((memberInfo.getPasswd()).equals(pass)) {
				session.setAttribute("memId", id);
			}
		}
		return "logon/loginPro";
	}
	//메인화면 페이지 요청
	@RequestMapping("/main.do")
	public String main(){

		return "main";
	}
	
	//로그아웃 후 로그인 폼으로 이동
	@RequestMapping("/logout.do")
	public String logout(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.invalidate();
		return "logon/loginForm";
	}
}
