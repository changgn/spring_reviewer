package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
	
	@RequestMapping("/main.do")
	public String form(HttpServletRequest request, HttpServletResponse response) {
		
		Object memId = request.getSession().getAttribute("memId");
		request.getSession().setAttribute("memId", memId);
		
		return "main/main";
	}
	
}
