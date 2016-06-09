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
	
	
	@ModelAttribute("memberCommand")
	public MemberCommand memberCommand(){
		return new MemberCommand();
	}
	
	//회원가입 페이지 요청
	@RequestMapping(value="/join.do",method=RequestMethod.GET)
	public String regform(){
		return "member/inputForm";
	}
	//회원 가입 성공 후, 로그인 페이지로 redirect
	@RequestMapping(value="/join.do",method=RequestMethod.POST)
	public String action(String id){
		memberDao.loginPro(id);
		return "member/inputPro";
	}
	
	//아이디 체크 페이지 요청 및 아이디 체크
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
