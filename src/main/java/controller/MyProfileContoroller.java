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
	public String myProfileform(HttpServletRequest request, Model model){
		
		List category = null;
		
		String id = (String) request.getSession().getAttribute("id");
		String paramId = request.getParameter("id");
		
		
		List<MemberCategoryCommand> membersCategoryList = null;
		List<CategoryCommand> CategoryList = new ArrayList<CategoryCommand>();
		membersCategoryList = MemberCategoryDao.getlistById(paramId);
		
		for(MemberCategoryCommand Command : membersCategoryList) {
			CategoryCommand Category = categoryDao.getOne(Command.getCategory_id());
			CategoryList.add(Category);
		}
		model.addAttribute("CategoryList", CategoryList);
		category= MemberCategoryDao.getlistById(id);
		

		/*Iterator it = null;
		it = CategoryList.iterator();
		while(it.hasNext()){
			String cate= it.next().toString();
			request.setAttribute("cate", cate);
		} */
			

		
		int followerCount =followDao.countfrom(paramId);
		model.addAttribute("followerCount", followerCount);
		
		int followingCount = followDao.countto(paramId);
		model.addAttribute("followingCount", followingCount);
		
		
		if(id!=null) {
			List<String> folloingList = followDao.toList(paramId);
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
		
		
				if(paramId != null) {
					List<BoardCommand> boardList = null;
					List<HashMap> allBoardList = new ArrayList<HashMap>();
					
					boardList = BoardDao.getList();
					
						if(boardList!=null){
						for(BoardCommand Command : boardList) {
							HashMap<String, Object> boardMap = new HashMap<String, Object>();
							PhotoCommand photo = PhotoDao.getOneByBoardNum(Command.getBoard_num());
							CategoryCommand Category = categoryDao.getOne(Command.getCategory_id());
							String commentCount=commentDao.getCountByBoardNum(Command.getBoard_num());  // 肄붾뱶 異붽�
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
					model.addAttribute("allBoardList", allBoardList);
				}
				model.addAttribute("paramId", paramId); //키값 !!
				
				
				

				int board_num = 0;
				System.out.println(id);
				board_num = BoardDao.getRecentBoardNumById(id);
			
				model.addAttribute("board_num", board_num);
				
			
				
				List<PhotoCommand> photoList = null;
				BoardCommand board = new BoardCommand();
				
				
				board = BoardDao.selectContent(board_num);
				photoList = PhotoDao.getListByBoardNum(board_num);
				
				if(board != null) { 
					request.setAttribute("board", board);
				}
				if(photoList != null) { 
					request.setAttribute("photoList", photoList);
				}
				
				
				ScrepCommand screpCommand = new ScrepCommand();
				screpCommand.setId(id);
				
		
		return "/profile/myProfile";
	}
	
	}
