package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import command.MemberCommand;
import dao.MemberDAO;

@Controller
public class IdPwSearchController {
 
	@Autowired
	private MemberDAO memberDao;
	
	public void setMemberDao(MemberDAO memberDao){
		this.memberDao = memberDao;
	}
	@ModelAttribute("memberCommand")
	public MemberCommand memberCommand(){
		return new MemberCommand();
	}
	
	@RequestMapping(value="/idpwSearch/idpwSearchNew.do", method=RequestMethod.GET)
	public String IdpwSearch(HttpServletRequest request){
	
		String message = request.getParameter("message");
		request.setAttribute("message", message);
	
		return "idpwSearch/idpwSearchNew";
	}
	@RequestMapping(value="/idpwSearch/idSearch.do", method=RequestMethod.POST)
	public String idSearch(HttpServletRequest request, String phone_num){
	
		String message = null;
		List<MemberCommand> idList = memberDao.idSearch(phone_num);
		if(idList.size()==0) {
			message = "errorPhoneNum";
		}
		request.setAttribute("message", message);
		request.setAttribute("idList", idList);
		
		
		return "idpwSearch/idSearch";
	}
	
	@RequestMapping(value="/idpwSearch/pwSearch.do", method=RequestMethod.POST)
	public String pwSearch(MemberCommand memberInfo, String id, @RequestParam("phone_num2") String 

phone_num ,String email,HttpServletRequest request, Model model){
		
		memberInfo.setPhone_num(phone_num);
		memberInfo.setId(id);
		memberInfo.setEmail(email);
		
		MemberCommand passwdVo = memberDao.pwSearch(memberInfo);
		
		String passwd = null;
		String message = null;
		if(passwdVo!=null){
			passwd = passwdVo.getPasswd();
		} else {
			message = "incorrect";
		}
		
		model.addAttribute("passwd", passwd);
		model.addAttribute("message", message);
	
		return "idpwSearch/pwSearch";
	}
	
}
