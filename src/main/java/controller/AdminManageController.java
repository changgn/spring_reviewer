package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import command.BoardCommand;
import command.MemberCommand;
import dao.BoardDAO;
import dao.MemberDAO;

@Controller
public class AdminManageController {
	@Autowired
	BoardDAO boardDAO;
	
	public void setBoardDAO(BoardDAO boardDAO) {
		this.boardDAO = boardDAO;
	}

	@Autowired
	private MemberDAO memberDAO;
	
	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}
	
	/**	인기글	*/
	@RequestMapping("/administrator/adminPopul.do")
	public String populForm(Model model){
		List<BoardCommand> boardList = null;
		boardList = boardDAO.pupulBoardList();
		model.addAttribute("populList", boardList);
		return "administrator/adminPopul";
	}
	
	/**	신고글	*/
	@RequestMapping("/administrator/adminReport.do")
	public String reportForm(Model model){
		List<BoardCommand> boardList = null;
		boardList = boardDAO.reportBoardList();
		model.addAttribute("reportList", boardList);
		return "administrator/adminReport";
	}
	
	/**	게시글 삭제	*/
	@RequestMapping("adminDelete.do")
	public String adminDelete(HttpServletRequest request, @RequestParam("board_num") String board_num){
		String id = (String) request.getSession().getAttribute("id");
		if(id.equals("admin")){
			boardDAO.deleteContent(Integer.parseInt(board_num));
		}
		return "administrator/adminDelete";
	}
	
	/**	회원	*/
	@RequestMapping("/administrator/adminMem.do")
	public ModelAndView adminMemForm(){
		ModelAndView mav = new ModelAndView();
		List<MemberCommand> member_list = null;
		member_list = memberDAO.getList();  
		mav.addObject("memberList", member_list);	
		int count = 0;      
		count = memberDAO.count();
		mav.addObject("count", count);
		mav.setViewName("administrator/adminMem");
		return mav;
	}
}
