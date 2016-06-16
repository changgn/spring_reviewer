


package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import dao.MemberDAO;
import dao.MemberCategoryDAO;
import dao.PhotoDAO;
import dao.ScrepDAO;
import command.BoardCommand;
import command.CategoryCommand;
import command.FollowCommand;
import command.MemberCategoryCommand;
import command.MemberCommand;
import command.PhotoCommand;
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
	
	@RequestMapping(value="/profile/myProfile.do",method=RequestMethod.GET)
	public String myProfileform(HttpServletRequest request){
		
		List category = null;
		
		String id = (String) request.getSession().getAttribute("id");
		String paramId = request.getParameter("id");
		String from_id = request.getParameter("id");
		
		List<MemberCategoryCommand> membersCategoryList = null;
		List<CategoryCommand> CategoryList = new ArrayList<CategoryCommand>();
		
		// 해당 id의 카테고리id 가져오기
		membersCategoryList = MemberCategoryDao.getlistById(paramId);
		
		// 카테고리id로 카테고리 가져오기
		for(MemberCategoryCommand Command : membersCategoryList) {
			CategoryCommand Category = categoryDao.getOne(Command.getCategory_id());
			CategoryList.add(Category);
		}
		request.setAttribute("CategoryList", CategoryList);
		category= MemberCategoryDao.getlistById(id);//id 값을 통하여 카테고리 리스트를 가져온다
		

		/*Iterator it = null;
		it = CategoryList.iterator();
		while(it.hasNext()){
			String cate= it.next().toString();
			request.setAttribute("cate", cate);
		} */
			

		//팔로워 숫자 저장
		int followerCount =followDao.countfrom(paramId);
		request.setAttribute("followerCount", followerCount);
		//팔로잉 숫자 저장
		int followingCount = followDao.countto(paramId);
		request.setAttribute("followingCount", followingCount);
		
		// 팔로우 상태 저장
		if(id!=null) {
			List<FollowCommand> folloingList = followDao.toList(from_id);
			boolean followCheck = false;
			if(folloingList!=null) {
				for(FollowCommand following : folloingList) {
					if(following.equals(paramId)) {
						followCheck = true;
						break;
					}
				}
			}
			request.setAttribute("followCheck", followCheck);
		}
		
		//게시글 가져오기
				if(paramId != null) {
					List<BoardCommand> boardList = null;
					List<HashMap> allBoardList = new ArrayList<HashMap>();
					
					
					
					boardList = BoardDao.getList();
					
					
					if(boardList!=null){
						for(BoardCommand Command : boardList) {
							HashMap<String, Object> boardMap = new HashMap<String, Object>();
							PhotoCommand photo = PhotoDao.getOneByBoardNum(Command.getBoard_num());
							CategoryCommand Category = categoryDao.getOne(Command.getCategory_id());
							String commentCount=commentDao.getCountByBoardNum(Command.getBoard_num());  // 코드 추가
							if(commentCount==null)	commentCount="0";
							boolean contentFlag = false;
							String[] contentSub = Command.getContent().split("\n");
							if(contentSub.length > 3) {
								contentFlag = true;
								Command.setContent(contentSub[0] + contentSub[1] + contentSub[2]);
							}
							boardMap.put("board", Command);
							boardMap.put("photo", photo);
							boardMap.put("category", category);
							boardMap.put("commentCount", commentCount);
							boardMap.put("contentFlag", contentFlag);
							allBoardList.add(boardMap);
						}
					}
					request.setAttribute("allBoardList", allBoardList);
				}
				request.setAttribute("paramId", paramId);
				
				//게시글

				
				
				int board_num = 0;
				System.out.println(id);
				board_num = BoardDao.getRecentBoardNumById(id);
			
				request.setAttribute("board_num", board_num);
				
			
				// 변수 생성
				List<PhotoCommand> photoList = null;
				BoardCommand board = new BoardCommand();
				
				// 게시물 번호로 정보 가져오기
				/*board = BoardDAO.getByBoardNum(board_num);*/
				photoList = PhotoDao.getListByBoardNum(board_num);
				
				if(board != null) { // 가져온 게시글 정보가 있다면
					request.setAttribute("board", board);
				}
				if(photoList != null) { // 가져온 사진 정보가 있다면
					request.setAttribute("photoList", photoList);
				}
				
				//스크랩
				ScrepCommand screpCommand = new ScrepCommand();
				screpCommand.setId(id);
				
		
		return "/profile/myProfile";
	}
	
	
}
