package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import command.BoardCommand;
import command.CategoryCommand;
import command.MemberCommand;
import command.ProfilePhotoCommand;

@Controller
public class AdminController extends BaseController {

	/**	메인화면	*/
	@RequestMapping("/administrator/adminForm.do")
	public String adminForm(Model model){
		// 회원 수
		int count = 0;      
		count = memberDao.count();
		count -= 1;
		model.addAttribute("count", count);
		
		int listcount = 0;
		listcount = boardDao.getListCount();
		model.addAttribute("listcount", listcount);
		return "administrator/admin";
	}
	
	/**	게시글 관리	*/
	@RequestMapping("/administrator/adminBoard.do")
	public String reportForm(Model model, String sort, String kind){
		// 분류, 정렬
		if(kind == null ) kind = "noKind";
		if(sort == null ) sort = "noSort";
		// 전체 게시글 목록
		List<BoardCommand> BoardList = new ArrayList<BoardCommand>();
		if(kind.equals("board_num")){
			if(sort.equals("DESC")){
				BoardList = boardDao.getListByBoardNum_ASC();
				kind = "board_num";
				sort = "ASC";
			}else{
				BoardList = boardDao.getListByBoardNum_DESC();
				kind = "board_num";
				sort = "DESC";
			}
		}else if(kind.equals("writer")){
			if(sort.equals("DESC")){
				BoardList = boardDao.getListByWriter_ASC();
				sort = "ASC";
			}else{
				BoardList = boardDao.getListByWriter_DESC();
				sort = "DESC";
			}
		}else if(kind.equals("writeDate")){
			if(sort.equals("DESC")){
				BoardList = boardDao.getListByWriteDate_ASC();
				sort = "ASC";
			}else{
				BoardList = boardDao.getList();
				sort = "DESC";
			}
		}else if(kind.equals("report")){
			if(sort.equals("DESC")){
				BoardList = boardDao.reportBoardList();
				sort = "ASC";
			}else{
				BoardList = boardDao.getReportBoardList();
				sort = "DESC";
			}
		}else if(kind.equals("recommend")){
			if(sort.equals("DESC")){
				BoardList = boardDao.pupulBoardList();
				sort = "ASC";
			}else{
				BoardList = boardDao.populBoardListByASC();
				sort = "DESC";
			}
		}else{
			BoardList = boardDao.getBoardList();
		}
		model.addAttribute("kind", kind);
		model.addAttribute("sort", sort);
		model.addAttribute("boardList", BoardList);
		// 게시글 카테고리 정보
		Map<String, Object> cii = new HashMap<String, Object>();
		for(BoardCommand bc : BoardList){
			String category_id = bc.getCategory_id();
			CategoryCommand cc = categoryDao.getOne(category_id);
			cii.put(category_id, cc);
		}
		model.addAttribute("category_info", cii);
		List<Integer> board_num_list = new ArrayList<Integer>();
		board_num_list = boardDao.getBoardNumList();
		Map<Integer, Object> commentCountMap = new HashMap<Integer, Object>();
		Map<Integer, Object> screpCountMap = new HashMap<Integer, Object>();
		List<String> RecommendListByBoard = new ArrayList<String>();
		Map<Integer, Object> recommendIdListMap = new HashMap<Integer, Object>();
		List<String> ReportListByBoard = new ArrayList<String>();
		Map<Integer, Object> reportIdListMap = new HashMap<Integer, Object>();
		List<String> CommentListByBoard = new ArrayList<String>();
		Map<Integer, Object> commentIdListMap = new HashMap<Integer, Object>();
		List<String> ScrepListByBoard = new ArrayList<String>();
		Map<Integer, Object> screpIdListMap = new HashMap<Integer, Object>();
		// 게시글 코멘트 개수
		String commentCount;
		for(int board_num : board_num_list){
			commentCount = commentDao.getCountByBoardNum(board_num);
			commentCountMap.put(board_num, commentCount);
		}
		model.addAttribute("commentCount", commentCountMap);
		// 게시글 스크랩 개수
		int screpCount = 0;
		for(int board_num : board_num_list){
			screpCount = screpDao.getCountByScrepNum(board_num);
			screpCountMap.put(board_num, screpCount);
		}
		model.addAttribute("screpCount", screpCountMap);
		int listcount = 0;
		listcount = boardDao.getListCount();
		model.addAttribute("listcount", listcount);

		for(int board_num : board_num_list){
			RecommendListByBoard = recommendDao.getIdByRecommendNum(board_num);
			recommendIdListMap.put(board_num, RecommendListByBoard);
		}
		model.addAttribute("recommendListByBoard", recommendIdListMap);
		for(int board_num : board_num_list){
			ReportListByBoard = reportDao.getIdListByBoardNum(board_num);
			reportIdListMap.put(board_num, ReportListByBoard);
		}
		model.addAttribute("reportListByBoard", reportIdListMap);
		for(int board_num : board_num_list){
			CommentListByBoard = commentDao.getIdByBoardNum(board_num);
			commentIdListMap.put(board_num, CommentListByBoard);
		}
		model.addAttribute("commentIdListByBoardnum", commentIdListMap);

		for(int board_num : board_num_list){
			ScrepListByBoard = screpDao.getIdByScrepNum(board_num);
			screpIdListMap.put(board_num, ScrepListByBoard);
		}
		model.addAttribute("screpListByBoardnum", screpIdListMap);
		return "administrator/adminBoard";
	}
	
	/**	회원 관리	*/
	@RequestMapping("/administrator/adminMem.do")
	public ModelAndView adminMemForm(HttpServletRequest request, String sort, String kind){
		ModelAndView mav = new ModelAndView();
		List<String> id_list = new ArrayList<String>();
		List<MemberCommand> member_list = new ArrayList<MemberCommand>();
		Map<String, Object> abmap = new HashMap<String, Object>();
		Map<String, Object> rbmap = new HashMap<String, Object>();
		Map<String, Object> pbmap = new HashMap<String, Object>();
		Map<String, Object> ppmap = new HashMap<String, Object>();
		Map<String, Object> sbmap = new HashMap<String, Object>();
		Map<String, Object> Member_Category_Id_map = new HashMap<String, Object>();
		Map<String, Object> Category_Id_Info_map = new HashMap<String, Object>();
		// 관리자 로그인 ID
		String id = (String)request.getSession().getAttribute("id");
		if(id.equals("admin")){
			mav.addObject("admin", id);
		}
		// 회원 수
		int count = 0;      
		count = memberDao.count();
		count -= 1;
		mav.addObject("count", count);
		// ID 목록
		id_list = memberDao.getIdList();
		if(id_list.contains(id)){
			id_list.remove(id);
		}
		// 해당ID의 게시글 수
		int boardCount = 0;
		for(String mid : id_list){
			boardCount = boardDao.getBoardCountById(mid);
			rbmap.put(mid, boardCount);
		}
		mav.addObject("boardCount", rbmap);
		// 분류, 정렬
		if(kind == null ) kind = "noKind";
		if(sort == null ) sort = "noSort";
		// 회원 정보 목록
		if(kind.equals("recommend")){
			if(sort.equals("DESC")){
				member_list = memberDao.getListByReccomend_ASC();
				kind = "recommend";
				sort = "ASC";
			}else{
				member_list = memberDao.getListByRecommend_DESC();
				kind = "recommend";
				sort = "DESC";
			}
		}else if(kind.equals("regDate")){
			if(sort.equals("DESC")){
				member_list = memberDao.getListByRegDate_ASC();
				kind = "regDate";
				sort = "ASC";
			}else{
				member_list = memberDao.getListByRegDate_DESC();
				kind = "regDate";
				sort = "DESC";
			}
		}else if(kind.equals("id")){
			if(sort.equals("DESC")){
				member_list = memberDao.getListById_ASC();
				kind = "id";
				sort = "ASC";
			}else{
				member_list = memberDao.getListById_DESC();
				kind = "id";
				sort = "DESC";
			}
		}else{	/*	kind - noKink, sort - noSort	*/
			member_list = memberDao.getList();
		}
		mav.addObject("kind", kind);
		mav.addObject("sort", sort);
		mav.addObject("memberList", member_list);
		// 해당ID가 추천한 게시글 수
		int recommendCount = 0;
		for(String plist : id_list){
			recommendCount = recommendDao.getRcommendCountById(plist);
			abmap.put(plist, recommendCount);
		}
		mav.addObject("recommendCount", abmap);
		// 해당ID가 신고한  게시글 수
		int reportCount = 0;
		for(String rlist : id_list){
			reportCount = reportDao.getReportCountById(rlist);
			pbmap.put(rlist, reportCount);
		}
		mav.addObject("reportCount", pbmap);
		// 해당 ID가 스크랩 한 게시글 수
		int screpCount = 0;
		for(String slist : id_list){
			screpCount = screpDao.getScrepCountByScrepNum(slist);
			sbmap.put(slist, screpCount);
		}
		mav.addObject("scropCount", sbmap);
		// 해당 ID의 프로필 사진
		ProfilePhotoCommand ppc = new ProfilePhotoCommand();
		for(String pplist : id_list){
			ppc = profilePhotoDao.getOneById(pplist);
			ppmap.put(pplist, ppc);
		}
		mav.addObject("profilePhoto", ppmap);	
		// ID 별 카테고리 정보
		List<String> member_category_id_list = new ArrayList<String>();
		for(String member_id : id_list){ 
			member_category_id_list= memberCategoryDao.getCategoryIdById(member_id); /*	회원 별 카테고리 ID 목록 */
			if(member_category_id_list.isEmpty()){
				Member_Category_Id_map.put(member_id, "not_category");
			}else{
				for(String category_id : member_category_id_list){
					CategoryCommand catecory_command  = new CategoryCommand();
					catecory_command = categoryDao.getOne(category_id);
					Category_Id_Info_map.put(category_id, catecory_command);
				}
				Member_Category_Id_map.put(member_id, member_category_id_list);
			}
		}
		mav.addObject("CategoryId", Category_Id_Info_map);
		mav.addObject("MemberCategory", Member_Category_Id_map);
		
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
			bc = boardDao.getList();
			for(BoardCommand bdcmd : bc){
				if(bdcmd.getId().equals(outId)){
					reportDao.deleteReport(bdcmd.getBoard_num());
				}
			}
			reportDao.deleteReportById(outId);
			memberDao.MemberOut(outId);
		}
		return "administrator/adminOutput";
	}
}
