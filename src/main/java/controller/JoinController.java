package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
	
	@RequestMapping("/logout.do")
	public String logout(HttpServletRequest request){
		HttpSession session = request.getSession();
		session.invalidate();
		return "logon/loginForm";
	}
	
	@ModelAttribute("memberCommand")
	public MemberCommand memberCommand(){
		return new MemberCommand();
	}
	
	@RequestMapping(value="/join.do",method=RequestMethod.GET)
	public String regform(){
		return "member/inputForm";
	}
	@RequestMapping(value="/join.do",method=RequestMethod.POST)
	public String action(String id){
		memberDao.loginPro(id);
		return "member/inputPro";
	}
	
	@RequestMapping("/idCheckForm.do")
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
