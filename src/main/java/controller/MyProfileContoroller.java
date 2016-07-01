package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dao.BoardDAO;
import dao.CategoryDAO;
import dao.CommentDAO;
import dao.FollowDAO;
import dao.MemberCategoryDAO;
import dao.PhotoDAO;
import dao.RecommendDAO;
import dao.ScrepDAO;
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
	private BoardDAO BoardDao;
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
	
	//DAO 초기화
	public void setBoardDao(BoardDAO boardDao) {
		BoardDao = boardDao;
	}
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
					
					boardList = BoardDao.getListById(paramId);
					
						if(boardList!=null){
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
				model.addAttribute("paramId", paramId); //키값 ?
					
				return "/profile/myProfile";
	}
}
