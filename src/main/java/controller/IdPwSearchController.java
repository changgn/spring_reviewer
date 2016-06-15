package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import command.MemberCommand;
import dao.MemberDAO;

@Controller
public class IdPwSearchController {
 
	private MemberDAO memberDao;
	
	@Autowired
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
	public String pwSearch(HttpServletRequest request){
		
		MemberDAO memberDao = new MemberDAO();	
		String id = (String) request.getParameter("id");
		String phone_num = (String) request.getParameter("phone_num2");
		String email = (String) request.getParameter("email");
		
		MemberCommand member = new MemberCommand();	
		member.setPhone_num(phone_num);
		member.setId(id);
		member.setEmail(email);
		
		MemberCommand mempass = memberDao.pwSearch(member);
		
		String passwd = null;
		String message = null;
		if(mempass!=null){
			passwd = mempass.getPasswd();
		} else {
			message = "incorrect";
		}
		
		request.setAttribute("passwd", passwd);
		request.setAttribute("message", message);
		
		return "idpwSearch/pwSearch";
	}
	
}
