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

	@ModelAttribute("memberinfo")
	public MemberCommand get(){
		return new MemberCommand();
	}
	
	@RequestMapping(value="/loginForm.do",method=RequestMethod.GET)
	public String loginform(){
		return "loginForm";
	}
	
	@RequestMapping("/modifyForm.do")
	public String form(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("memId");
		
		MemberCommand memberInfo = memberDao.modifyForm(id);
		
		request.setAttribute("memberInfo", memberInfo);
		return "modifyForm";
	}
	
	
	@RequestMapping("/modifyPro.do")
	public String submit(@ModelAttribute("memberinfo") MemberCommand memberInfo) {
		
		int updateSuccess = memberDao.modifyPro(memberInfo);
		
		if (updateSuccess>0) {
			return "mainForm";
		}
		return "modifyForm";
	}
	
	@RequestMapping("/deleteForm.do")
	public String deleteForm(HttpServletRequest request){
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		String passwd = (String) session.getAttribute("passwd");
		
		
		request.setAttribute("id",id);
		request.setAttribute("passwd", passwd);
		
		return "deleteForm";
	}

	
	@RequestMapping("/deletePro.do")
	public String delete(HttpServletRequest request,String id,String passwd){
		HttpSession session = request.getSession();
		MemberCommand memInfo = memberDao.deleteCf(id);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("passwd", passwd);
		
		if ((memInfo.getPasswd()).equals(passwd)) {

			session.invalidate();
			memberDao.deleteCf(id);
			return "loginForm";
		}
		
		return "deleteForm";
	}
	
	}



	
	
	
	
