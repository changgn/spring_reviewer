package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import command.MemberCommand;
import command.ProfilePhotoCommand;
import dao.MemberDAO;
import dao.ProfilePhotoDAO;


@Controller
public class JoinController {
	
	@Autowired
	private MemberDAO memberDao;
	@Autowired
	private ProfilePhotoDAO ProfilePhotoDao;
	
	public void setMemberDao(MemberDAO memberDao) {
		this.memberDao = memberDao;
	}
	public void setProfilePhotoDao(ProfilePhotoDAO profilePhotoDao) {
		ProfilePhotoDao = profilePhotoDao;
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
	public String action(MemberCommand memberInfo, Model model){
		int n = memberDao.inputPro(memberInfo);
		if(n != 0 ){
			ProfilePhotoCommand command = new ProfilePhotoCommand("default_profile.png", "default_profile.png", memberInfo.getId(), "/image/default_profile.png");
			ProfilePhotoDao.insert(command);
			String smessage="회원 가입에 성공하셨습니다.";
			model.addAttribute("smessage", smessage);
		}
		else {
			String fmessage="회원 가입에 실패하셨습니다.";
			model.addAttribute("fmessage", fmessage);
		}
		return "member/inputPro";
	}
	
	@RequestMapping("/member/idCheckForm.do")
	public ModelAndView idCheck(String id){
		ModelAndView mav = new ModelAndView("member/idCheckForm");
		String ch = memberDao.idCheck(id);
		int check = 0;
		if (ch != null) {
			check = 1;
		}
		mav.addObject("id", id); // ���̵� ������ �� �ʿ伺!!
		mav.addObject("idch", check);
		
		return mav;      
	}
	
}
