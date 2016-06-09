package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	
	@RequestMapping("/idpwSearchNew.do")
	public String IdpwSearch(HttpServletRequest request,HttpServletResponse response){
	
		String message = request.getParameter("message");
		request.setAttribute("message", message);
	
		return "idpwSearchNew";
	}
	@RequestMapping("/idSearch.do")
	public String idSearch(HttpServletRequest request, HttpServletResponse response){
		
		MemberDAO memberDao = new MemberDAO();
		String message = null;
		String phone_num = request.getParameter("phone_num");
		System.out.println("아이디를 찾을 핸드폰 번호: "+phone_num);
		
		List<String> idList = memberDao.idSearch(phone_num);
		if(idList.size()==0){
			message="errorPhoneNum";
			return "idSearch";
		}
		request.setAttribute("message", message);
		request.setAttribute("idList", idList);
		
		return "idSearch";
	}
	@RequestMapping("/pwSearch.do")
	public String pwSearch(HttpServletRequest request, HttpServletResponse response){
		
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
		
		return "pwSearch";
	}
	
}
