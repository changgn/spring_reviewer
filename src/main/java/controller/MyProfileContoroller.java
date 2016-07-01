package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.CategoryDAO;
import dao.CommentDAO;
import dao.FollowDAO;
import dao.MainDAO;
import dao.MemberCategoryDAO;
import dao.PhotoDAO;
import dao.RecommendDAO;
import dao.ScrepDAO;
import net.sf.json.JSONObject;
import command.BoardCommand;
import command.CategoryCommand;
import command.MemberCategoryCommand;
import command.MemberCommand;
import command.PhotoCommand;
import command.RecommendCommand;
import command.ScrepCommand;
@Controller
public class MyProfileContoroller {
	
	@Autowired
	private CategoryDAO categoryDao;
	@Autowired
	private CommentDAO commentDao;
	@Autowired
	private FollowDAO followDao;
	@Autowired
	private MemberCategoryDAO MemberCategoryDao;
	@Autowired
	private PhotoDAO PhotoDao;
	@Autowired
	private ScrepDAO ScrepDao;
	@Autowired
	private RecommendDAO recommendDao;
	@Autowired
	private MainDAO mainDao;
	
	//DAO 초기화
	public void setCategoryDao(CategoryDAO categoryDao) {
		this.categoryDao = categoryDao;
	}
	public void setCommentDao(CommentDAO commentDao) {
		this.commentDao = commentDao;
	}
	public void setFollowDao(FollowDAO followDao) {
		this.followDao = followDao;
	}
	public void setMemberCategoryDao(MemberCategoryDAO memberCategoryDao) {
		MemberCategoryDao = memberCategoryDao;
	}
	public void setPhotoDao(PhotoDAO photoDao) {
		PhotoDao = photoDao;
	}
	public void setScrepDao(ScrepDAO screpDao) {
		ScrepDao = screpDao;
	}
	public void setMainDao(MainDAO mainDao) { this.mainDao = mainDao; }
	public void setRecommendDao(RecommendDAO recommendDao) { this.recommendDao = recommendDao; }
	
	//Command Model view 설정
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
    @RequestMapping(value="/profile/myProfile.do",method=RequestMethod.GET)
	public String myProfileform(HttpServletRequest request, Model model){
		
		String id = (String) request.getSession().getAttribute("id");
		String paramId = request.getParameter("id");

		int boardCount = 0;
		
		// Command들을 담기위한 list 변수생성
		List<MemberCategoryCommand> membersCategoryList = null;
		List<CategoryCommand> CategoryList = new ArrayList<CategoryCommand>();
		// 해당 id의 카테고리id 가져오기
		membersCategoryList = MemberCategoryDao.getlistById(paramId);
		// 카테고리id로 카테고리 가져오기
		for(MemberCategoryCommand Command : membersCategoryList) {
			CategoryCommand Category = categoryDao.getOne(Command.getCategory_id());
			CategoryList.add(Category);
		}
		model.addAttribute("CategoryList", CategoryList);
		model.addAttribute("id", id);
		
		

		/*Iterator it = null;
		it = CategoryList.iterator();
		while(it.hasNext()){
			String cate= it.next().toString();
			request.setAttribute("cate", cate);
		} */
			

		//팔로워 숫자 저장
		int followerCount =followDao.countfrom(paramId);
		model.addAttribute("followerCount", followerCount);
		//팔로잉 숫자 저장
		int followingCount = followDao.countto(paramId);
		model.addAttribute("followingCount", followingCount);
		int screpCount = ScrepDao.getScrepCountByScrepNum(paramId);
		model.addAttribute("screpCount", screpCount);
		int myCount = ScrepDao.getCountByBoardNum(paramId);
		model.addAttribute("myCount", myCount);
		// 팔로우 상태 저장
		if(id!=null) {
			List<String> folloingList = followDao.toList(id);
			boolean followCheck = false;
			if(folloingList!=null) {
				for(String following : folloingList) {
					if(following.equals(paramId)) {
						followCheck = true;
						break;
					}
				}
			}
			model.addAttribute("followCheck", followCheck);
			
			
		
		}
		
		//게시글 가져오기
		if(paramId != null) {
			List<BoardCommand> boardList = null;
			List<HashMap<String, Object>> allBoardList = new ArrayList<HashMap<String, Object>>();
			

			boardList = mainDao.getPageListById(paramId);
			
			if(boardList!=null){
					boardCount = boardList.size();
				for(BoardCommand Command : boardList) {
					HashMap<String, Object> boardMap = new HashMap<String, Object>();
					PhotoCommand photo = PhotoDao.getOneByBoardNum(Command.getBoard_num());
					CategoryCommand category = categoryDao.getOne(Command.getCategory_id());
					String commentCount=commentDao.getCountByBoardNum(Command.getBoard_num());
					if(commentCount==null)	commentCount="0";
					boolean contentFlag = false;
					String[] contentSub = Command.getContent().split("\n");
					if(contentSub.length > 3) {
						contentFlag = true;
						Command.setContent(contentSub[0] + contentSub[1] + contentSub[2]);
					}
					RecommendCommand recommend = new RecommendCommand(id, Command.getBoard_num());
					if(recommend.getId() != null ){
						List<RecommendCommand> recommends = recommendDao.getRecommend(recommend);
						if(recommends.size() != 0){
							boardMap.put("recommendFlag", "recommend");
						}else{
							boardMap.put("recommendFlag", "nrecommend");
						}
					}
					ScrepCommand screp = new ScrepCommand(id, Command.getBoard_num());
					if(screp.getId() != null ){
						ScrepCommand screps = ScrepDao.getScrep(screp);
						if(screps != null){
							boardMap.put("screpFlag", "screp");
						}else{
							boardMap.put("screpFlag", "nscrep");
						}
					}
					boardMap.put("board", Command);
					boardMap.put("photo", photo);
					boardMap.put("category", category);
					boardMap.put("commentCount", commentCount);
					boardMap.put("contentFlag", contentFlag);
					allBoardList.add(boardMap);
				}
			}
		 	model.addAttribute("allBoardList", allBoardList);
		}
		model.addAttribute("boardCount", boardCount);
		model.addAttribute("paramId", paramId); //키값 ?
			
		return "/profile/myProfile";
	}
    
	@ResponseBody
    @RequestMapping(value="/profile/myProfileAjax.do")
   	public String myProfileAjaxform(HttpServletRequest request, HttpServletResponse resp, String pageInfo, String paramId, int lastBoard_num){
   		
		JSONObject jso = new JSONObject();
		
   		String id = (String) request.getSession().getAttribute("id");

   		//게시글 가져오기
		if(paramId != null) {
			List<BoardCommand> boardList = null;
			List<HashMap<String, Object>> allBoardList = new ArrayList<HashMap<String, Object>>();
			
			if(pageInfo == null) {
				boardList = mainDao.getMorePageListById(paramId, lastBoard_num);
			} else {
				List<Integer> boardNumList = ScrepDao.getScrepListById(paramId);
				if(boardNumList.size() != 0){
					boardList = mainDao.getMorePageListByBoardNum(boardNumList, lastBoard_num);
				}
			}
			
			if(boardList!=null){
				for(BoardCommand Command : boardList) {
					HashMap<String, Object> boardMap = new HashMap<String, Object>();
					PhotoCommand photo = PhotoDao.getOneByBoardNum(Command.getBoard_num());
					CategoryCommand category = categoryDao.getOne(Command.getCategory_id());
					String commentCount=commentDao.getCountByBoardNum(Command.getBoard_num());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String date = sdf.format(Command.getWrite_date());
					
					if(commentCount==null)	commentCount="0";
					boolean contentFlag = false;
					String[] contentSub = Command.getContent().split("\n");
					if(contentSub.length > 3) {
						contentFlag = true;
						Command.setContent(contentSub[0] + contentSub[1] + contentSub[2]);
					}
					RecommendCommand recommend = new RecommendCommand(id, Command.getBoard_num());
					if(recommend.getId() != null ){
						List<RecommendCommand> recommends = recommendDao.getRecommend(recommend);
						if(recommends.size() != 0){
							boardMap.put("recommendFlag", "recommend");
						}else{
							boardMap.put("recommendFlag", "nrecommend");
						}
					}
					ScrepCommand screp = new ScrepCommand(id, Command.getBoard_num());
					if(screp.getId() != null ){
						ScrepCommand screps = ScrepDao.getScrep(screp);
						if(screps != null){
							boardMap.put("screpFlag", "screp");
						}else{
							boardMap.put("screpFlag", "nscrep");
						}
					}
					boardMap.put("board", Command);
					boardMap.put("photo", photo);
					boardMap.put("category", category);
					boardMap.put("commentCount", commentCount);
					boardMap.put("contentFlag", contentFlag);
					boardMap.put("date", date);
					allBoardList.add(boardMap);
				}
			}
			jso.put("allBoardList", allBoardList);
		}
			
		resp.setContentType("text/html;charset=utf-8");
		return jso.toString();
   	}
}
