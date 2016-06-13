package controller;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import dao.MemberDAO;
import command.MemberCommand;


@Controller
public class MemberController {

	@Autowired
	private MemberDAO memberDao;

	
	public void setMemberDao(MemberDAO memberDao) {
		this.memberDao = memberDao;
	}
   
	//회원 정보를 가져옴
	@ModelAttribute("memberCommand")
	public MemberCommand get(){
		return new MemberCommand();
	}
	
	//로그인 페이지 요청
	@RequestMapping(value="/logon/login.do",method=RequestMethod.GET)
	public String loginform(HttpSession session){
		session.setAttribute("login_status", "2");
		return "logon/loginForm";
	}

	@RequestMapping(value="/main/modify.do", method=RequestMethod.GET)
	public String form(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("memId");
		
		MemberCommand memberCommand = memberDao.modifyForm(id);
		
		request.setAttribute("member", memberCommand);
		return "member/modifyForm";
	}
	
	//회원수정 후, 메인페이지로의 이동 요청
	@RequestMapping(value="/main/modify.do", method=RequestMethod.POST)
	public String submit(@ModelAttribute("memberCommand") MemberCommand memberCommand) {
		
		int updateSuccess = memberDao.modifyPro(memberCommand);
		
		if (updateSuccess>0) {
			return "main/main";
		}
		return "member/modifyForm";
	}
	
	//회원삭제 페이지 요청
	@RequestMapping(value="/main/delete.do", method=RequestMethod.GET)
	public String deleteForm(HttpServletRequest request){
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		String passwd = (String) session.getAttribute("passwd");
		
		
		request.setAttribute("id",id);
		request.setAttribute("passwd", passwd);
		
		return "member/deleteForm";
	}       

    //회원 페이지 삭제 후 로그인 페이지로 이동요청	
	@RequestMapping(value="/main/delete.do", method=RequestMethod.POST)
	public String delete(HttpServletRequest request,String id,String passwd){
		HttpSession session = request.getSession();
		MemberCommand memberCommand = memberDao.deleteCf(id);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("passwd", passwd);
		
		if ((memberCommand.getPasswd()).equals(passwd)) {

			session.invalidate();
			memberDao.deleteCf(id);
			return "logon/loginForm";
		}
		
		return "member/deleteForm";
	}
	
	}



	
	
	
	
