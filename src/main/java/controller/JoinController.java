package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dao.MemberDAO;
import command.MemberCommand;


@Controller
public class JoinController {
	
	@Autowired
	private MemberDAO memberDao;
	
	
	
	public void setMemberDao(MemberDAO memberDao) {
		this.memberDao = memberDao;
	}
	
	
	@ModelAttribute("memberInfo")
	public MemberCommand memberInfo(){
		return new MemberCommand();
	}
	
	@RequestMapping(value="/inputForm.do",method=RequestMethod.GET)
	public String regform(){
		return "inputForm";
	}
	
	@RequestMapping(value="/InputPro.do",method=RequestMethod.POST)
	public String action(String id){
		memberDao.loginPro(id);
		return "inputPro";
	}
	
	
	//아이디 체크
	@RequestMapping("/idCheckForm.do")
	public ModelAndView idCheck(@RequestParam("id") String id){
		ModelAndView mav = new ModelAndView("idCheckForm");
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
