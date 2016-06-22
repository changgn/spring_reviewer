package controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import net.sf.json.JSONObject;
import command.ScrepCommand;
import command.SecretCommand;


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
	public String ScrepForm(HttpServletRequest request, String id, int board_num, Model model){
		return "profile/myProfile";
	}
	
	
	@RequestMapping(value="/screp/screp.do",method=RequestMethod.POST)
	public String Screp(HttpServletRequest request, String id, int board_num, Model model){
		
		List<ScrepCommand> screpList = null;
		screpList = ScrepDao.getList();
		id = (String) request.getSession().getAttribute("id");
		board_num = (Integer) request.getSession().getAttribute("board_num");
		
		ScrepCommand insertScrep = new ScrepCommand(id, board_num);
		ScrepDao.insertScrep(insertScrep);
		String message =null;
		
		if (screpList!=null) {
			
			model.addAttribute("screpList", screpList);
			}
			else 
			{
				message = "emptyList";
			}
	
		model.addAttribute("message", message);
		model.addAttribute("insertScrep", insertScrep);
		
		return "redirect:/main/main.do";
	}
}