package controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dao.BoardDAO;

@Controller
public class AdminDeleteController {
	@Autowired
	BoardDAO boardDAO = new BoardDAO();
	public void setBoardDAO(BoardDAO boardDAO) {
		this.boardDAO = boardDAO;
	}
	@RequestMapping("adminDelete.do")
	public String adminDelete(HttpServletRequest request, @RequestParam("board_num") String board_num){
		String id = (String) request.getSession().getAttribute("id");
		if(id.equals("admin")){
			boardDAO.deleteContent(Integer.parseInt(board_num));
		}
		return "administrator/adminDelete";
	}
}
