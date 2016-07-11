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
import command.CategoryCommand;
import command.MemberCategoryCommand;
import command.MemberCommand;
import command.ProfilePhotoCommand;
import dao.BoardDAO;
import dao.CategoryDAO;
import dao.CommentDAO;
import dao.MemberCategoryDAO;
import dao.MemberDAO;
import dao.ProfilePhotoDAO;
import dao.RecommendDAO;
import dao.ReportDAO;
import dao.ScrepDAO;

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
	private ProfilePhotoDAO profilePhotoDAO;
	public void setProfilePhotoDao(ProfilePhotoDAO profilePhotoDAO) {
		this.profilePhotoDAO = profilePhotoDAO;
	}
	@Autowired
	private ScrepDAO screpDAO;
	public void setScrepDAO(ScrepDAO screpDAO) {
		this.screpDAO = screpDAO;
	}
	@Autowired
	private CategoryDAO categoryDAO;
	public void setCategoryDao(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}
	@Autowired
	private CommentDAO commentDAO;
	public void setCommentDAO(CommentDAO commentDAO) {
		this.commentDAO = commentDAO;
	}
	@Autowired
	private MemberCategoryDAO memberCategoryDAO;
	public void setMemberCategoryDAO(MemberCategoryDAO memberCategoryDAO) {
		this.memberCategoryDAO = memberCategoryDAO;
	}

	/**	메인화면	*/
	@RequestMapping("/administrator/adminForm.do")
	public String adminForm(){
		return "administrator/admin";
	}
	
	/**	게시글 관리	*/
	@RequestMapping("/administrator/adminBoard.do")
	public String reportForm(Model model){
		// 전체 게시글 목록
		List<BoardCommand> BoardList = null;
		BoardList = boardDAO.getList();
		model.addAttribute("boardList", BoardList);
		// 게시글 카테고리 정보
		Map cii = new HashMap();
		for(BoardCommand bc : BoardList){
			String category_id = bc.getCategory_id();
			CategoryCommand cc = categoryDAO.getOne(category_id);
			cii.put(category_id, cc);
		}
		model.addAttribute("category_info", cii);
		List<Integer> board_num_list = new ArrayList<Integer>();
		board_num_list = boardDAO.getBoardNumList();
		Map commentCountMap = new HashMap();
		Map screpCountMap = new HashMap();
		// 게시글 코멘트 개수
		String commentCount;
		for(int board_num : board_num_list){
			commentCount = commentDAO.getCountByBoardNum(board_num);
			commentCountMap.put(board_num, commentCount);
		}
		model.addAttribute("commentCount", commentCountMap);
		// 게시글 스크랩 개수
		int screpCount = 0;
		for(int board_num : board_num_list){
			screpCount = screpDAO.getCountByScrepNum(board_num);
			screpCountMap.put(board_num, screpCount);
		}
		model.addAttribute("screpCount", screpCountMap);
		// 신고 게시글 목록
		List<BoardCommand> ReportBoardList = null;
		ReportBoardList = boardDAO.reportBoardList();
		model.addAttribute("reportBoardList", ReportBoardList);
		// 추천 게시글 목록
		List<BoardCommand> PopulBoardList = null;
		PopulBoardList = boardDAO.pupulBoardList();
		model.addAttribute("populBoardList", PopulBoardList);
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
		// 회원 정보 목록
		List<MemberCommand> member_list = null;
		member_list = memberDAO.getList(); 
		mav.addObject("memberList", member_list);	
		// 관리자 로그인 ID
		String id = (String)request.getSession().getAttribute("id");
		if(id.equals("admin")){
			mav.addObject("admin", id);
		}
		List<String> id_list = new ArrayList<String>();
		id_list = memberDAO.getIdList();
		if(id_list.contains(id)){
			id_list.remove(id);
		}
		Map abmap = new HashMap();
		Map rbmap = new HashMap();
		Map pbmap = new HashMap();
		Map ppmap = new HashMap();
		Map sbmap = new HashMap();
		Map cmap = new HashMap();
		// 해당ID의 게시글 수
		int boardCount = 0;
		for(String mid : id_list){
			boardCount = boardDAO.getBoardCountById(mid);
			rbmap.put(mid, boardCount);
			
		}
		mav.addObject("boardCount", rbmap);
		// 해당ID가 추천한 게시글 수
		int recommendCount = 0;
		for(String plist : id_list){
			recommendCount = recommendDAO.getRcommendCountById(plist);
			abmap.put(plist, recommendCount);
		}
		mav.addObject("recommendCount", abmap);
		// 해당ID가 신고한  게시글 수
		int reportCount = 0;
		for(String rlist : id_list){
			reportCount = reportDAO.getReportCountById(rlist);
			pbmap.put(rlist, reportCount);
		}
		mav.addObject("reportCount", pbmap);
		// 해당 ID가 스크랩 한 게시글 수
		int screpCount = 0;
		for(String slist : id_list){
			screpCount = screpDAO.getScrepCountByScrepNum(slist);
			sbmap.put(slist, screpCount);
		}
		mav.addObject("scropCount", sbmap);
		// 해당 ID의 프로필 사진
		ProfilePhotoCommand ppc = new ProfilePhotoCommand();
		for(String pplist : id_list){
			ppc = profilePhotoDAO.getOneById(pplist);
			ppmap.put(pplist, ppc);
		}
		mav.addObject("profilePhoto", ppmap);
		// 해당 ID의 카테고리 정보
		MemberCategoryCommand mcc = new MemberCategoryCommand();
		List<String> mcil = null;
		CategoryCommand cc = new CategoryCommand();
		List<CategoryCommand> mcl = null;
		
		for(String mci : id_list){
			mcil = memberCategoryDAO.getCategoryIdById(mci);
			if(mcil != null){
				for(String ci : mcil){
					cc = categoryDAO.getOne(ci);
					mcl.add(cc);
				}
				cmap.put(mci, mcl);
			}else{
				mav.addObject("MemberCategoryInfo"+mci, "NotInfoCategory");
			}
		}
		mav.addObject("MemberCategory", cmap);
		mav.setViewName("administrator/adminMem");
		return mav;
	}
	
	/**	회원 강제 탈퇴	*/
	@RequestMapping("/administrator/adminOutput.do")
	public String adminOutput(HttpServletRequest request, Model model, HttpSession session, String outId){
		String id = (String)request.getSession().getAttribute("id");
		session = request.getSession();
		if(id.equals("admin")){
			List<BoardCommand> bc = new ArrayList<BoardCommand>();
			bc = boardDAO.getList();
			for(BoardCommand bdcmd : bc){
				if(bdcmd.getId().equals(outId)){
					reportDAO.deleteReport(bdcmd.getBoard_num());
				}
			}
			reportDAO.deleteReportById(outId);
			memberDAO.MemberOut(outId);
		}
		return "administrator/adminOutput";
	}
}
