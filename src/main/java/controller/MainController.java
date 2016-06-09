package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dao.MemberDAO;
import command.MemberCommand;


@Controller
public class MainController {
	@Autowired
	private MemberDAO memberDao;
	

	public void setMemberDao(MemberDAO memberDao) {
		this.memberDao = memberDao;
	}
	
	@RequestMapping("/main.do")
	public String login(HttpServletRequest request,String id, String pass){
		
		HttpSession session = request.getSession();
		
		MemberCommand memberInfo = memberDao.loginPro(id);
		
		if (memberInfo!=null) {
			if ((memberInfo.getPasswd()).equals(pass)) {
				session.setAttribute("memId", id);
			}
		}
		return "main/main";
	}
	@RequestMapping("/logout.do")
	public String logout(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.invalidate();
		return "loginForm";
	}
}
