package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
	// 관리자 메인
	@RequestMapping("admin.do")
	public String adminForm(){
		return "administrator/admin";
	}
}
