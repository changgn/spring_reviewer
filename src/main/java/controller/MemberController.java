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
import org.springframework.web.bind.annotation.RequestParam;
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
   
	//ȸ�� ������ ������
	@ModelAttribute("memberCommand")
	public MemberCommand get(){
		return new MemberCommand();
	}
	
	
	//�α��� ������ ��û
	@RequestMapping(value="/logon/login.do",method=RequestMethod.GET)
	public String loginform(HttpSession session){
		session.setAttribute("login_status", "2");
		return "logon/loginForm";
	}
	
	@RequestMapping(value="/logon/login.do",method=RequestMethod.POST)
	public String login(HttpServletRequest request,String id, String passwd,Model model){
		
		HttpSession session = request.getSession();
		
		MemberCommand memberInfo = memberDao.loginPro(id);
		String message = null;
		
		if (memberInfo!=null) {
			if ((memberInfo.getPasswd()).equals(passwd)) {
				
				session.setAttribute("id", id);
			}
			else 
			{
				message = "errPwd";
			}
	
		}
		else {
			message = "errID";
		}
		
		session.setAttribute("login_status", "1");
		
		if(id.equals("admin")) {
			session.setAttribute("login_status", "0");
		}
		model.addAttribute("message", message);
		
		return "logon/loginPro";
	}

	@RequestMapping("/logon/logout.do")
	public String logout(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.invalidate();
		return "logon/loginForm";
	}
	
	@RequestMapping(value="/member/modify.do", method=RequestMethod.GET)
	public String form(HttpServletRequest request, Model model) {
		
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		
		MemberCommand memberInfo = memberDao.modifyForm(id);
		model.addAttribute("m", memberInfo);
		
		return "member/modifyForm";
	}
	
	
	@RequestMapping(value="/member/modify.do",method=RequestMethod.POST)
	public String submit(MemberCommand memberInfo) {
		
		int updateSuccess = memberDao.modifyPro(memberInfo);
		
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
	public String delete(HttpServletRequest request, Model model){
		
		HttpSession session = request.getSession();
		
		String errorPasswd = null;
		String id = (String)request.getSession().getAttribute("id");
		String passwd = request.getParameter("passwd");
		String SavePasswd = memberDao.getPasswdById(id);
		
		if (passwd.equals(SavePasswd)) {
			
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("id", id);
			map.put("passwd", passwd);
			memberDao.delete(map);
			session.invalidate();
			
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



	
	
	
	
