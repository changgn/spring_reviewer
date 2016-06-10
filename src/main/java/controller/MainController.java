package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dao.MemberDAO;


@Controller
public class MainController {
	@Autowired
	private MemberDAO memberDao;
	

	public void setMemberDao(MemberDAO memberDao) {
		this.memberDao = memberDao;
	}
	
	@RequestMapping("/main.do")
	public String main(){

		return "main/main";
	}
	
	
}
