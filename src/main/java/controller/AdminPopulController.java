package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import command.BoardCommand;
import dao.BoardDAO;

@Controller
public class AdminPopulController {
	@Autowired
	BoardDAO boardDAO;
	
	public void setBoardDAO(BoardDAO boardDAO) {
		this.boardDAO = boardDAO;
	}

	// 인기글 관리 페이지
	@RequestMapping("adminPopul.do")
	public String populForm(Model model){
		List<BoardCommand> boardList = null;
		boardList = boardDAO.pupulBoardList();
		model.addAttribute("populList", boardList);
		return "administrator/adminPopul";
	}
}
