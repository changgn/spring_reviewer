package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import command.ScrepCommand;
import command.BoardCommand;
import command.CategoryCommand;
import command.MemberCategoryCommand;
import command.PhotoCommand;
import command.ProfilePhotoCommand;
import command.RecommendCommand;

import net.sf.json.JSONObject;

//스크렙 삽입 & 갯수
@Controller
public class ScrepController extends BaseController {
	
	@ResponseBody
	@RequestMapping(value="/screp/screpInsert.do")
	public String Screp(HttpServletRequest request, HttpServletResponse resp, int board_num){
		
		String id = (String)request.getSession().getAttribute("id"); //로그인 id(해당 id)
		
		JSONObject jso = new JSONObject();
		
		ScrepCommand command = new ScrepCommand(id, board_num); //command 객체에 스크랩정보를 저장 시킨다.(id,board_num)
		screpDao.insertScrep(command);
		
		jso.put("screpCount", command); //serepCount 를 삽입된 글의 갯수로 저장
		resp.setContentType("text/html;charset=utf-8");
		return jso.toString();
		
	}
	
   //스크렙 게시글 목록
	@RequestMapping(value="/profile/screpList.do")
	public String ScrepList(HttpServletRequest request, String comment, Model model){

		String id = (String) request.getSession().getAttribute("id"); //id라는 변수에 현재 로그인 id의 세션 값 저장
		String paramId = request.getParameter("id"); //paramId 라는 변수에 id 속성의 파라미터 값을 저장
		int boardCount = 0;
		int screpCount = screpDao.getScrepCountByScrepNum(paramId); // 해당회원이 스크렙한 글 갯수
		model.addAttribute("screpCount", screpCount); //KEY: screpCount, VALUE : screpCount 속성으로 세션 저장(JSP 페이지에서 EL 태그값으로 불러와 사용하기 위함 )
		
		int myCount = screpDao.getCountByBoardNum(paramId); // 해당 회원이 작성한 게시글 갯수
		model.addAttribute("myCount", myCount);// 게시글 갯수, 세션으로 저장
		
		int followerCount =followDao.countfrom(paramId); // 해당 회원이(id) 팔로워한 횟수
		model.addAttribute("followerCount", followerCount);// 팔로워 횟수, 세션으로 저장
		//팔로잉 숫자 저장
		int followingCount = followDao.countto(paramId);// 해당 회원(id)이 팔로워한 횟수 
		model.addAttribute("followingCount", followingCount); // 팔로잉 횟수, 세션으로 저장

		// Command들을 담기위한 list 변수생성
		List<MemberCategoryCommand> membersCategoryList = null;
		List<CategoryCommand> CategoryList = new ArrayList<CategoryCommand>();
		// 해당 id의 카테고리id 가져오기
		membersCategoryList = memberCategoryDao.getlistById(paramId); //해당회원이 작성한 분류항목에 따른 글 정보
		// 카테고리id로 카테고리 가져오기
		for(MemberCategoryCommand Command : membersCategoryList) {
			CategoryCommand Category = categoryDao.getOne(Command.getCategory_id());
			CategoryList.add(Category); // 해당회원이 직접 카테고리 추가
		}
		model.addAttribute("CategoryList", CategoryList); // 해당회원이 추가한 분류별 글 정보
		
		ProfilePhotoCommand myProfilePhoto = profilePhotoDao.getOneById(paramId); // 해당회원에 추가시킬 프로필 사진
		model.addAttribute("myProfilePhoto", myProfilePhoto); // 추가시킬 프로필 사진의 세션 값 저장
		
		List<BoardCommand> boardList = null;
		List<Integer> boardNumList = screpDao.getScrepListById(paramId); // 해당회원이 스크렙 한 글 번호 (삭제 할시 파라미터 값으로 스크렙 한 글번호 정보를 받아오기 위함)
		List<HashMap<String,Object>> allBoardList = new ArrayList<HashMap<String,Object>>();
		
		if(boardNumList.size() == 0){
			boardList = null;
		}else {
			boardList = mainDao.getPageListByBoardNum(boardNumList); //게시글 목록 페이지 설정
		}
		
		
		if(boardList!=null)	{
			boardCount = boardList.size(); //게시글 갯수 = 게시글 목록의 크기
			for(BoardCommand vo : boardList) {
				HashMap<String, Object> boardMap = new HashMap<String, Object>();
				PhotoCommand photo = photoDao.getOneByBoardNum(vo.getBoard_num()); // 해당 글 번호의 사진(이미지)
				ProfilePhotoCommand profilePhoto = profilePhotoDao.getOneById(vo.getId()); // 해당 아이디의 프로필 사진(이미지) 
				CategoryCommand category = categoryDao.getOne(vo.getCategory_id()); // 카테고리 분류 정보 
				String commentCount = commentDao.getCountByBoardNum(vo.getBoard_num()); // 해당 게시글의 코멘트 수
		
				if(commentCount==null)	commentCount="0";
				boolean contentFlag = false;
				String[] contentSub = vo.getContent().split("\n");
				if(contentSub.length > 3) {
					contentFlag = true;
					vo.setContent(contentSub[0] + contentSub[1] + contentSub[2]);
				}
				
				RecommendCommand recommend = new RecommendCommand(id, vo.getBoard_num()); // 게시글 추천 정보
				if(recommend.getId() != null ){
					List<RecommendCommand> recommends = recommendDao.getRecommend(recommend); // 해당 회원이 해당 게시글에 대한 추천
					if(recommends.size() != 0){
						boardMap.put("recommendFlag", "recommend"); // 추천
					}else{
						boardMap.put("recommendFlag", "nrecommend"); // 추천 취소
					}
				}
				
				ScrepCommand screp = new ScrepCommand(id, vo.getBoard_num());
				if(screp.getId() != null ){
					ScrepCommand screps = screpDao.getScrep(screp); // 해당 회원이 해당 게시글에 대한 스크렙
					if(screps != null){
						boardMap.put("screpFlag", "screp"); // 스크렙 
					}else{
						boardMap.put("screpFlag", "nscrep"); // 스크랩 취소
					}
				}
				

					
				boardMap.put("board", vo); 
				boardMap.put("photo", photo);
				boardMap.put("profilePhoto", profilePhoto);
				boardMap.put("category", category);
				boardMap.put("commentCount", commentCount);
				boardMap.put("contentFlag", contentFlag);
				allBoardList.add(boardMap); // 해당 게시글 정보 추가
			}
		}
		
		// 팔로우 상태 저장
		if(id!=null) {
			List<String> folloingList = followDao.toList(id); // 해당회원(아이디)의 팔로윙
			boolean followCheck = false; //팔로윙 유효성 체크
			if(folloingList!=null) {
				for(String following : folloingList) {
					if(following.equals(paramId)) { // 특정 파라미터 값을 가진 회원(아이디) 팔로우 신청
						followCheck = true;
						break;
					}
				}
			}
			model.addAttribute("followCheck", followCheck);
		}

		model.addAttribute("pageInfo", "screp"); // 스크렙 글에 대한 페이지
		model.addAttribute("paramId", paramId); // 특정 파라미터 값을 가진 회원(아이디)
		model.addAttribute("boardCount", boardCount); // 게시글 수
		model.addAttribute("allBoardList", allBoardList); // 게시글, 사진, 스크렙 등이 모두 들어간 게시글 목록
		
		return "profile/myProfile";
	}
	
	
	//스크랩 갯수 증가
	@ResponseBody
	@RequestMapping("/screp/screp.do")
	public String Screp(HttpServletRequest request, HttpServletResponse resp, Integer board_num, String page, String paramId){

		String login_status = (String)request.getSession().getAttribute("login_status"); //로그인 상태 정보
		JSONObject jso = new JSONObject();
		
		if(login_status.equals("0") || login_status.equals("1")) {
			String id = (String)request.getSession().getAttribute("id"); // 로그인 되어진 아이디
			ScrepCommand screp = new ScrepCommand(id, board_num); // 스크랩 정보

			ScrepCommand Screpselect = null;
			Screpselect = screpDao.getScrep(screp); // 해당 회원이 해당 게시글에 대한 스크렙
			HashMap<String, Object> map = new HashMap<String, Object>();
			if(Screpselect != null){
				screpDao.deleteScrep(Screpselect); // 스크렙 게시글 삭제
				map.put("board_num", board_num); // 게시 글에 대한 KEY, VALUE 정보
				map.put("screp", screpDao.getCountByScrepNum(board_num)); // 스크렙 글에 대한 KEY, VALUE(해당 게시물을 스크렙한 회원 수, 즉 스크렙 수)
				screpDao.updateScrepByBoardNum(map); // 스크렙 정보(상태)수정
				jso.put("screpFlag", "nscrep"); // 스크렙 취소 상태
			} else{
				screpDao.insertScrep(screp); // 스크렙 게시물 추가(삽입)
				map.put("board_num", board_num); // 게시 글에 대한 KEY, VALUE 정보
				map.put("screp", screpDao.getCountByScrepNum(board_num)); // 스크렙 글에 대한 KEY, VALUE(해당 게시물을 스크렙한 회원 수, 즉 스크렙 수)
				screpDao.updateScrepByBoardNum(map); // // 스크렙 정보(상태)수정
				jso.put("screpFlag", "screp"); // 스크렙 상태
			}
			int screpCount = 0;
			if(page != null){
				screpCount = screpDao.getScrepCountByScrepNum(paramId); // 스크렙 게시 글 수
			}
			jso.put("screpCount", screpCount); // 스크렙 게시 글 수 (KEY,VALUE)저장
			jso.put("screp_num", boardDao.selectContent(board_num).getScrep()); // 해당 게시글 모든 정보를 스크렙
			jso.put("board_num", board_num); //게시 글 번호 (KEY,VALUE)저장
		} else {
			jso.put("error", "error");
		}
		resp.setContentType("text/html;charset=utf-8");
		return jso.toString();
	}
	
	//멤버 스크랩 갯수
	@ResponseBody
	@RequestMapping("/screp/member.do")
	public String screpMember(HttpServletResponse resp, int board_num){
		JSONObject jso = new JSONObject();
		List<String> members = screpDao.getIdByScrepNum(board_num); //해당 글번호의 게시물을 스크렙 한 회원
		jso.put("members", members); // 게시물을 스크렙 한 회원 KEY,VALUE 값으로 저장
		jso.put("board_num", board_num); // 게시글 번호를  KEY,VALUE 값으로 저장
		
		resp.setContentType("text/html;charset=utf-8");
		return jso.toString();
	}


}