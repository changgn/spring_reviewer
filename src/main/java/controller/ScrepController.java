package controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dao.ScrepDAO;
import command.ScrepCommand;


@Controller
public class ScrepController {

	@Autowired
	private ScrepDAO ScrepDao;

	
	public void setScrepDao(ScrepDAO ScrepDao) {
		this.ScrepDao = ScrepDao;
	}
   
	
	@ModelAttribute("ScrepCommand")
	public ScrepCommand get(){
		return new ScrepCommand();
	}
	
	
	
	@RequestMapping(value="/screp/screp.do",method=RequestMethod.GET)
	public String loginform(Model model){
		model.addAttribute("login_status", "2");
		return "screp/screpForm";
	}
	
	@RequestMapping(value="/screp/screp.do",method=RequestMethod.POST)
	
	public String Screp(HttpServletRequest request, String id, String board_num, Model model){
		
		List<ScrepCommand> screpList = null;
		screpList = ScrepDao.getList();
		id = (String) request.getSession().getAttribute("id");
		board_num = (String) request.getSession().getAttribute("board_num");
		String message =null;
		
		if (screpList!=null) {
			
			model.addAttribute("screpList", screpList);
			}
			else 
			{
				message = "emptyList";
			}
	
		model.addAttribute("message", message);
		
		
		return "redirect:/main/main.do";
	}
}