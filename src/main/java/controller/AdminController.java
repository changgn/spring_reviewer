package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import dao.RecommendDAO;

@Controller
public class AdminController {
	@Autowired
	BoardDAO boardDAO;
	public void setBoardDAO(BoardDAO boardDAO) {
		this.boardDAO = boardDAO;
	}
	@Autowired
	MemberDAO memberDAO;
	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}
	@Autowired
	RecommendDAO recommendDAO;
	public void setRecommendDAO(RecommendDAO recommendDAO) {
		this.recommendDAO = recommendDAO;
	}

	/**	메인화면	*/
	@RequestMapping("/administrator/adminForm.do")
	public String adminForm(){
		return "administrator/admin";
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
	@RequestMapping("/administrator/adminDelete.do")
	public String adminDelete(HttpServletRequest request, @RequestParam("board_num") String board_num){
		String id = (String) request.getSession().getAttribute("id");
		if(id.equals("admin")){
			boardDAO.deleteContent(Integer.parseInt(board_num));
		}else{
			return "redirect";
		}
		return "administrator/adminDelete";
	}
	
	/**	회원	*/
	@RequestMapping("/administrator/adminMem.do")
	public ModelAndView adminMemForm(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		// 회원 수
		int count = 0;      
		count = memberDAO.count();
		count -= 1;
		mav.addObject("count", count);
		// 회원 정보
		List<MemberCommand> member_list = null;
		member_list = memberDAO.getList(); 
		mav.addObject("memberList", member_list);	
		// 로그인 ID
		String id = (String)request.getSession().getAttribute("id");
		if(id.equals("admin")){
			mav.addObject("admin", id);
		}
		
		List<String> id_list = new ArrayList<String>();
		id_list = memberDAO.getIdList();
		Map map = new HashMap();
		
		// 해당ID의 추천 수
		int recommendCount = 0;
		for(String list : id_list){
			recommendCount = recommendDAO.getRcommendCountById(list);
			map.put(list, recommendCount);
		}
		mav.addObject("recommendCount", map);
		
		// 해당ID의 게시글 수
		int boardCount = 0;
		for(String mid : id_list){
			boardCount = boardDAO.getBoardCoutById(mid);
			map.put(mid, boardCount);
		}
		mav.addObject("boardCount", map);
		
		mav.setViewName("administrator/adminMem");
		return mav;
	}
	
	/**	회원 강제 탈퇴	*/
	@RequestMapping("/administrator/adminOutput.do")
	public String adminOutput(HttpServletRequest request, Model model, HttpSession session, String outId){
		String id = (String)request.getSession().getAttribute("id");
		session = request.getSession();
		if(id.equals("admin")){
			memberDAO.MemberOut(outId);
		}
		return "administrator/adminOutput";
	}
}
