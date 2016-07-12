package controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import net.sf.json.JSONObject;
import command.BoardCommand;
import command.CategoryCommand;
import command.MemberCategoryCommand;
import command.MemberCommand;
import command.PhotoCommand;
import command.ProfilePhotoCommand;
import command.RecommendCommand;
import command.ScrepCommand;
@Controller
public class MyProfileContoroller extends BaseController{
	
	//스크렙, 사진, 게시글  데이터   
	@ModelAttribute("screpCommand")
	public MemberCommand getMember(){
		return new MemberCommand();
	}
	
	@ModelAttribute("photoCommand")
	public PhotoCommand getPhoto(){
		return new PhotoCommand();
	}
	
	@ModelAttribute("boardCommand")
	public PhotoCommand getBoard(){
		return new PhotoCommand();
	}
	//My프로필 페이지 요청
    @RequestMapping(value="/profile/myProfile.do",method=RequestMethod.GET)
	public String myProfileform(HttpServletRequest request, Model model){
		
		String id = (String) request.getSession().getAttribute("id"); //로그인 중인 세션 아이디
		String paramId = request.getParameter("id"); // 파라미터 값으로 받아 올 아이디(값으로 받아 올 해당 아이디, 대상 아이디)

		int boardCount = 0;
		
		// Command 데이터 들을 담기위한 list 변수생성
		List<MemberCategoryCommand> membersCategoryList = null;
		List<CategoryCommand> CategoryList = new ArrayList<CategoryCommand>();
		// 해당 id의 카테고리id 가져오기
		membersCategoryList = memberCategoryDao.getlistById(paramId);
		// 카테고리id로 카테고리 가져오기
		for(MemberCategoryCommand Command : membersCategoryList) {
			CategoryCommand Category = categoryDao.getOne(Command.getCategory_id()); // 카테고리 아이디 정보
			CategoryList.add(Category); // 카테고리 추가
		}

		ProfilePhotoCommand myProfilePhoto = profilePhotoDao.getOneById(paramId); // 해당아이디의 프로필 사진

		model.addAttribute("myProfilePhoto", myProfilePhoto); // 프로필 사진 세션 KEY,VALUE 저장
		model.addAttribute("CategoryList", CategoryList); // 카테고리 목록 세션 KEY,VALUE 저장
		model.addAttribute("id", id); // 아이디 세션 KEY,VALUE 저장
		
		

		/*Iterator it = null;
		it = CategoryList.iterator();
		while(it.hasNext()){
			String cate= it.next().toString();
			request.setAttribute("cate", cate);
		} */
			

		//팔로워 숫자 저장
		int followerCount =followDao.countfrom(paramId); // 대상 아이디의 팔로워 수
		model.addAttribute("followerCount", followerCount); // 팔로워 수 세션 KEY,VALUE 저장
		//팔로잉 숫자 저장
		int followingCount = followDao.countto(paramId); // 대상 아이디의 팔로잉 수
		model.addAttribute("followingCount", followingCount); // 팔로잉 수 세션 KEY,VALUE 저장
		int screpCount = screpDao.getScrepCountByScrepNum(paramId); // 대상아이디의 스크렙 수
		model.addAttribute("screpCount", screpCount);  // 대상아이디의 스크렙 수 세션 KEY,VALUE 저장
		int myCount = screpDao.getCountByBoardNum(paramId); // 대상아아디의 게시글 수
		model.addAttribute("myCount", myCount);// 대상아이디의 게시글 수 세션 KEY,VALUE 저장
		// 팔로우 상태 저장
		if(id!=null) { // 세션 아이디가 존재 할 경우(회원 아이디 로그인 상태)
			List<String> folloingList = followDao.toList(id); // 현재 로그인 아이디 팔로잉 수
			boolean followCheck = false; // 팔로잉 유효성 체크
			if(folloingList!=null) {
				for(String following : folloingList) {
					if(following.equals(paramId)) { // 팔로잉 아이디가 내 아이디가 아닌 대상아이디면 유효성 ok
						followCheck = true;
						break;
					}
				}
			}
			model.addAttribute("followCheck", followCheck); // 팔로잉 유효성 세션 KEY,VALUE 저장
		}
		
		//게시글 가져오기
		if(paramId != null) {
			List<BoardCommand> boardList = null;
			List<HashMap<String, Object>> allBoardList = new ArrayList<HashMap<String, Object>>();
			

			boardList = mainDao.getPageListById(paramId); // 해당 페이지에 보여지는 게시글(게시글 페이징)
			
			if(boardList!=null){
					boardCount = boardList.size(); // 게시글의 수 = 게시글 목록의 크기
				for(BoardCommand Command : boardList) {
					HashMap<String, Object> boardMap = new HashMap<String, Object>();
					PhotoCommand photo = photoDao.getOneByBoardNum(Command.getBoard_num()); // 해당 게시글의 사진(이미지)
					ProfilePhotoCommand profilePhoto = profilePhotoDao.getOneById(Command.getId()); // 해당 아이디의 프로필 사진
					CategoryCommand category = categoryDao.getOne(Command.getCategory_id()); // 해당 아이디가 게시한 게시글의 항목
					String commentCount=commentDao.getCountByBoardNum(Command.getBoard_num());//해당 게시글의 댓글 수
					if(commentCount==null)	commentCount="0";
					boolean contentFlag = false;
					String[] contentSub = Command.getContent().split("\n");
					if(contentSub.length > 3) { // 카테고리 분류 항목
						contentFlag = true;
						Command.setContent(contentSub[0] + contentSub[1] + contentSub[2]);
					}
					RecommendCommand recommend = new RecommendCommand(id, Command.getBoard_num());
					if(recommend.getId() != null ){
						List<RecommendCommand> recommends = recommendDao.getRecommend(recommend);
						if(recommends.size() != 0){
							boardMap.put("recommendFlag", "recommend"); // 좋아요 상태
						}else{
							boardMap.put("recommendFlag", "nrecommend"); // 좋아요 취소 상태
						}
					}
					ScrepCommand screp = new ScrepCommand(id, Command.getBoard_num());
					if(screp.getId() != null ){
						ScrepCommand screps = screpDao.getScrep(screp); // 스크랩 정보 
						if(screps != null){
							boardMap.put("screpFlag", "screp"); // 스크랩 상태
						}else{
							boardMap.put("screpFlag", "nscrep"); // 스크랩 취소 상태
						}
					}
					boardMap.put("board", Command);
					boardMap.put("photo", photo);
					boardMap.put("profilePhoto", profilePhoto);
					boardMap.put("category", category);
					boardMap.put("commentCount", commentCount);
					boardMap.put("contentFlag", contentFlag);
					allBoardList.add(boardMap); // 목록에 보여질 게시글의 모든 정보 
				}
			}
		 	model.addAttribute("allBoardList", allBoardList); // 게시글 모든 정보 세션 KEY,VALUE 저장
		}
		model.addAttribute("boardCount", boardCount); // 게시글 수 세션 KEY,VALUE 저장
		model.addAttribute("paramId", paramId); //해당 아이디 세션 KEY,VALUE 저장
			
		return "/profile/myProfile";
	}
    
	@ResponseBody
    @RequestMapping(value="/profile/myProfileAjax.do") // Ajax 요청 페이지
   	public String myProfileAjaxform(HttpServletRequest request, HttpServletResponse resp, String pageInfo, String paramId, int lastBoard_num){
   		
		JSONObject jso = new JSONObject();
		
   		String id = (String) request.getSession().getAttribute("id"); //현재 로그인 세션 아이디

   		//게시글 가져오기
		if(paramId != null) {
			List<BoardCommand> boardList = null;
			List<HashMap<String, Object>> allBoardList = new ArrayList<HashMap<String, Object>>();
			
			if(pageInfo == null) {
				boardList = mainDao.getMorePageListById(paramId, lastBoard_num); // 회원 들이 올려 놓은 게시글 더보기 페이지
			} else {
				List<Integer> boardNumList = screpDao.getScrepListById(paramId); // 해당 아이디가 스크렙한 글 목록
				if(boardNumList.size() != 0){
					boardList = mainDao.getMorePageListByBoardNum(boardNumList, lastBoard_num); //한 페이지 게시글 수가 넘어 갔을 경우, 게시글 더보기
				}
			}
			
			if(boardList!=null){
				for(BoardCommand Command : boardList) {
					HashMap<String, Object> boardMap = new HashMap<String, Object>();
					PhotoCommand photo = photoDao.getOneByBoardNum(Command.getBoard_num());
					ProfilePhotoCommand profilePhoto = profilePhotoDao.getOneById(Command.getId()); // 해당 아이디의 프로필 사진
					CategoryCommand category = categoryDao.getOne(Command.getCategory_id()); // 게시글 분류 할 해당 카테고리 아이디
					String commentCount=commentDao.getCountByBoardNum(Command.getBoard_num()); // 해당 게시글의 댓글 수
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); // 게시글 날짜 형식
					String date = sdf.format(Command.getWrite_date()); // 게시글 날짜 
					
					if(commentCount==null)	commentCount="0";
					boolean contentFlag = false;
					String[] contentSub = Command.getContent().split("\n");
					if(contentSub.length > 3) { // 카테고리 분류 항목
						contentFlag = true;
						Command.setContent(contentSub[0] + contentSub[1] + contentSub[2]);
					}
					RecommendCommand recommend = new RecommendCommand(id, Command.getBoard_num()); // 좋아요 데이터 모델
					if(recommend.getId() != null ){
						List<RecommendCommand> recommends = recommendDao.getRecommend(recommend); //해당 아이디의 해당 게시글 좋아요
						if(recommends.size() != 0){
							boardMap.put("recommendFlag", "recommend"); // 좋아요 상태
						}else{
							boardMap.put("recommendFlag", "nrecommend"); // 좋아요 취소 상태
						}
					}
					ScrepCommand screp = new ScrepCommand(id, Command.getBoard_num()); // 스크렙 데이터 모델
					if(screp.getId() != null ){
						ScrepCommand screps = screpDao.getScrep(screp); // 스크렙 게시글 
						if(screps != null){
							boardMap.put("screpFlag", "screp"); // 스크렙 상태
						}else{
							boardMap.put("screpFlag", "nscrep"); // 스크렙 취소 상태
						}
					}
					boardMap.put("board", Command);
					boardMap.put("photo", photo);
					boardMap.put("profilePhoto", profilePhoto);
					boardMap.put("category", category);
					boardMap.put("commentCount", commentCount);
					boardMap.put("contentFlag", contentFlag);
					boardMap.put("date", date);
					allBoardList.add(boardMap); // 보여질 게시글의 모든 정보 
				}
			}
			jso.put("allBoardList", allBoardList); // 게시글 모든 정보 세션 KEY,VALUE로 저장
		}
			
		resp.setContentType("text/html;charset=utf-8");
		return jso.toString();
   	}
	
	@RequestMapping(value="/profile/profile_photo.do", method=RequestMethod.POST)
	public String insertProfilePhoto(@RequestParam("u_photo") MultipartFile file, HttpSession session, Model model)
			throws IOException {

		String id = (String)session.getAttribute("id");
		String savePath = "/fileSave"; //파일 저장 경로
			
		String genId = UUID.randomUUID().toString(); 
		String o_fileName= file.getOriginalFilename();
		String fileName = genId + "_" + o_fileName;
		String realPath =  savePath + "/" + fileName;
		
		ProfilePhotoCommand profilephoto = new ProfilePhotoCommand(); // 프로필 사진정보
		profilephoto.setFileName(fileName); // 파일 이름
		profilephoto.setId(id); // 프로필 사진을 설정할 아이디
		profilephoto.setRealPath(realPath); // 프로필 사진의 경로
		profilephoto.setO_fileName(o_fileName); // 프로필 사진의 파일 이름
		
		if(!o_fileName.equals("")){
			profilePhotoDao.modify(profilephoto); // 프로필 사진 수정
			File f = new File(session.getServletContext().getRealPath(savePath)); // 파일 저장 경로
			File f2 = new File(f, fileName); // 파일 저장 경로, 파일 이름 저장
			file.transferTo(f2); // 지정된 파일 저장경로에 파일 이름 저장
		}
			
		return "redirect:/profile/myProfile.do?id=" + id;

	}
	
	@RequestMapping(value="/profile/profile_photo_remove.do")
	public String removeProfilePhoto(HttpSession session, Model model) {

		String id = (String)session.getAttribute("id");
		
		ProfilePhotoCommand command = new ProfilePhotoCommand("default_profile.png", "default_profile.png", id, "/image/default_profile.png");
		profilePhotoDao.modify(command); // 프로필 사진 삭제, 프로필 사진 기본이미지 값으로 수정
			
		return "redirect:/profile/myProfile.do?id=" + id;

	}
}
