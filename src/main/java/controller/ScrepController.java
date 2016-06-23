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


import command.ScrepCommand;
import command.BoardCommand;
import command.CategoryCommand;
import command.CommentCommand;
import command.PhotoCommand;
import command.RecommendCommand;
import command.SecretCommand;

import dao.ScrepDAO;
import dao.BoardDAO;
import dao.CategoryDAO;
import dao.PhotoDAO;
import net.sf.json.JSONObject;


@Controller
public class ScrepController {

	@Autowired
	private ScrepDAO ScrepDao;
	@Autowired
	private BoardDAO BoardDao;
	@Autowired
	private CategoryDAO CategoryDao;
	@Autowired
	private PhotoDAO PhotoDao;

	
	
	public void setBoardDao(BoardDAO boardDao) {
		BoardDao = boardDao;
	}
	public void setCategoryDao(CategoryDAO categoryDao) {
		CategoryDao = categoryDao;
	}
	public void setPhotoDao(PhotoDAO photoDao) {
		PhotoDao = photoDao;
	}
    public void setScrepDao(ScrepDAO ScrepDao) {
		this.ScrepDao = ScrepDao;
	}
   
	
    @RequestMapping(value="/screp/screp.do",method=RequestMethod.GET)
	public String ScrepForm(HttpServletRequest request, String id, int board_num, Model model){
		return "profile/myProfile";
	}
	
	
	@RequestMapping(value="/screp/screp.do",method=RequestMethod.POST)
	public String Screp(HttpServletRequest request, String id, int board_num, Model model){
		
		//스크랩 숫자 저장
		int screpCount = ScrepDao.getScrepCountByScrepNum(board_num);
		model.addAttribute("screpCount", screpCount);
		
		//스크랩 리스트 불러오기
		List<ScrepCommand> screpList = null;
		screpList = ScrepDao.getScrepList();
		id = (String) request.getSession().getAttribute("id");
		board_num = (Integer) request.getSession().getAttribute("board_num");
		
		String message =null;
		
		if (screpList!=null) {
			
			model.addAttribute("screpList", screpList);
			}
			else 
			{
				message = "emptyList";
				model.addAttribute("message", message);
			}
	
		return "screp/screpList";
	}
}