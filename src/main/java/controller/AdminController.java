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
import org.springframework.web.servlet.ModelAndView;

import command.BoardCommand;
import command.MemberCommand;
import dao.BoardDAO;
import dao.MemberDAO;
import dao.ProfilePhotoDAO;
import dao.RecommendDAO;
import dao.ReportDAO;

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
	@Autowired
	ReportDAO reportDAO;
	public void setReportDAO(ReportDAO reportDAO) {
		this.reportDAO = reportDAO;
	}
	@Autowired
	private ProfilePhotoDAO ProfilePhotoDao;
	public void setProfilePhotoDao(ProfilePhotoDAO profilePhotoDao) {
		ProfilePhotoDao = profilePhotoDao;
	}

	/**	메인화면	*/
	@RequestMapping("/administrator/adminForm.do")
	public String adminForm(){
		return "administrator/admin";
	}
	
	/**	게시글 관리	*/
	@RequestMapping("/administrator/adminBoard.do")
	public String reportForm(Model model){
		// 신고 게시글
		List<BoardCommand> rpboardList = null;
		rpboardList = boardDAO.reportBoardList();
		model.addAttribute("reportBoardList", rpboardList);
		// 추천 게시글
		List<BoardCommand> boardList = null;
		boardList = boardDAO.pupulBoardList();
		model.addAttribute("populBoardList", boardList);
		return "administrator/adminBoard";
	}
	
	/**	회원 관리	*/
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
		//
		List<String> id_list = new ArrayList<String>();
		id_list = memberDAO.getIdList();
		Map abmap = new HashMap();
		Map rbmap = new HashMap();
		Map pbmap = new HashMap();
		
		// 해당ID의 추천 받은 게시글 수
		int recommendCount = 0;
		for(String list : id_list){
			recommendCount = recommendDAO.getRcommendCountById(list);
			abmap.put(list, recommendCount);
		}
		mav.addObject("recommendCount", abmap);
		
		// 해당ID의 게시글 수
		int boardCount = 0;
		for(String mid : id_list){
			boardCount = boardDAO.getBoardCountById(mid);
			rbmap.put(mid, boardCount);
			
		}
		mav.addObject("boardCount", rbmap);
		
		List<BoardCommand> boardList = null;
		boardList = boardDAO.reportBoardList();
		int reporter = 0;
		String rid;
		int board_num;
		
		for(BoardCommand bc : boardList){
			bc.getId();
			bc.getBoard_num();
		}
		
		// 해당ID의 신고받은 게시글 수
		int reportCount = 0;
		for(String list : id_list){
			reportCount = reportDAO.getReportCountById(list);
			pbmap.put(list,reportCount);
		}
		mav.addObject("reportCount", pbmap);

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
