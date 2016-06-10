package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import command.MemberCommand;
import dao.MemberDAO;


@Controller
public class JoinController {
	
	@Autowired
	private MemberDAO memberDao;
	
	public void setMemberDao(MemberDAO memberDao) {
		this.memberDao = memberDao;
	}
	
	@RequestMapping(value="/logon/login.do",method=RequestMethod.POST)
	public String login(HttpServletRequest request,String id, String pass,Model model){
		
		HttpSession session = request.getSession();
		
		MemberCommand memberInfo = memberDao.loginPro(id);
		String message = null;
		if (memberInfo!=null) {
			if ((memberInfo.getPasswd()).equals(pass)) {
				session.setAttribute("memId", id);
			}
			else 
			{
				message = "errPwd";
			}
	
		}
		else {
			message = "errID";
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
	
	@ModelAttribute("memberCommand")
	public MemberCommand memberCommand(){
		return new MemberCommand();
	}
	
	@RequestMapping(value="/member/join.do",method=RequestMethod.GET)
	public String regform(){
		return "member/inputForm";
	}
	@RequestMapping(value="/member/join.do",method=RequestMethod.POST)
	public String action(String id){
		memberDao.loginPro(id);
		return "member/inputPro";
	}
	
	@RequestMapping("/member/idCheckForm.do")
	public ModelAndView idCheck(@RequestParam("id") String id){
		ModelAndView mav = new ModelAndView("member/idCheckForm");
		String memId = memberDao.idCheck(id);
		int check = 0;
		if (memId != null) {
			check = 1;
		}
		mav.addObject("id", memId);
		mav.addObject("check", check);
		return mav;
	}
		
}
