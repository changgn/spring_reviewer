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
import command.MemberCategoryCommand;
import command.MemberCommand;
import command.ProfilePhotoCommand;

@Controller
public class AdminController extends BaseController {

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
		BoardList = boardDao.getList();
		model.addAttribute("boardList", BoardList);
		// 게시글 카테고리 정보
		Map cii = new HashMap();
		for(BoardCommand bc : BoardList){
			String category_id = bc.getCategory_id();
			CategoryCommand cc = categoryDao.getOne(category_id);
			cii.put(category_id, cc);
		}
		model.addAttribute("category_info", cii);
		List<Integer> board_num_list = new ArrayList<Integer>();
		board_num_list = boardDao.getBoardNumList();
		Map commentCountMap = new HashMap();
		Map screpCountMap = new HashMap();
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
		// 신고 게시글 목록
		List<BoardCommand> ReportBoardList = null;
		ReportBoardList = boardDao.reportBoardList();
		model.addAttribute("reportBoardList", ReportBoardList);
		// 추천 게시글 목록
		List<BoardCommand> PopulBoardList = null;
		PopulBoardList = boardDao.pupulBoardList();
		model.addAttribute("populBoardList", PopulBoardList);
		return "administrator/adminBoard";
	}
	
	/**	회원 관리	*/
	@RequestMapping("/administrator/adminMem.do")
	public ModelAndView adminMemForm(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		// 회원 수
		int count = 0;      
		count = memberDao.count();
		count -= 1;
		mav.addObject("count", count);
		// 회원 정보 목록
		List<MemberCommand> member_list = null;
		member_list = memberDao.getList(); 
		mav.addObject("memberList", member_list);	
		// 관리자 로그인 ID
		String id = (String)request.getSession().getAttribute("id");
		if(id.equals("admin")){
			mav.addObject("admin", id);
		}
		List<String> id_list = new ArrayList<String>();
		id_list = memberDao.getIdList();
		if(id_list.contains(id)){
			id_list.remove(id);
		}
		Map abmap = new HashMap();
		Map rbmap = new HashMap();
		Map pbmap = new HashMap();
		Map ppmap = new HashMap();
		Map sbmap = new HashMap();
		Map Member_Category_Id_map = new HashMap();
		Map Category_Id_Info_map = new HashMap();
		// 해당ID의 게시글 수
		int boardCount = 0;
		for(String mid : id_list){
			boardCount = boardDao.getBoardCountById(mid);
			rbmap.put(mid, boardCount);
			
		}
		mav.addObject("boardCount", rbmap);
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
				Member_Category_Id_map.put(member_id, Category_Id_Info_map);
			}
		}
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
