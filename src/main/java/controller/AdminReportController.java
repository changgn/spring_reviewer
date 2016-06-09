package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import command.BoardCommand;
import dao.BoardDAO;

@Controller
public class AdminReportController {
	@Autowired
	BoardDAO boardDAO;
	
	public void setBoardDAO(BoardDAO boardDAO) {
		this.boardDAO = boardDAO;
	}
	// 신고글 관리 페이지
	@RequestMapping("adminReport.do")
	public String reportForm(Model model){
		List<BoardCommand> boardList = null;
		boardList = boardDAO.reportBoardList();
		model.addAttribute("reportList", boardList);
		return "administrator/adminReport";
	}
}
