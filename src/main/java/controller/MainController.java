package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import command.BoardCommand;
import command.CategoryCommand;
import command.PhotoCommand;
import dao.BoardDAO;
import dao.CategoryDAO;
import dao.CommentDAO;
import dao.MemberCategoryDAO;
import dao.PhotoDAO;


@Controller
public class MainController {
	@Autowired
	private BoardDAO boardDao;
	@Autowired
	private PhotoDAO photoDao;
	@Autowired
	private CommentDAO commentDao;
	@Autowired
	private CategoryDAO categoryDao;
	@Autowired
	private MemberCategoryDAO memberCategoryDao;
	
	
	public void setBoardDao(BoardDAO boardDao) {
		this.boardDao = boardDao;
	}

	public void setPhotoDao(PhotoDAO photoDao) {
		this.photoDao = photoDao;
	}

	public void setCommentDao(CommentDAO commentDao) {
		this.commentDao = commentDao;
	}

	public void setCategoryDao(CategoryDAO categoryDao) {
		this.categoryDao = categoryDao;
	}
	public void setMemberCategoryDao(MemberCategoryDAO memberCategoryDao) {
		this.memberCategoryDao = memberCategoryDao;
	}
	@RequestMapping("/main/main.do")
	public String main(HttpServletRequest request, HttpServletResponse response, Model model){
		
		String id = (String)request.getSession().getAttribute("id"); // 濡쒓렇�씤 �븳 id
		String login_status = (String)request.getSession().getAttribute("login_status");	//濡쒓렇�씤 �긽�깭
		
		List<BoardCommand> boardList = null;
		List<HashMap> allBoardList = new ArrayList<HashMap>();
		List<String> categoryIdList = null;
		
		if(login_status==null){
			login_status = "2";	// 濡쒓렇�씤 �븞�맂 �긽�깭
			request.getSession().setAttribute("login_status", login_status);
		}
		if(login_status.equals("2")){ // 濡쒓렇�씤 �븞�맂 寃쎌슦
			
			//紐⑤뱺 寃뚯떆湲��쓣 媛��졇�삩�떎
			boardList = boardDao.getList();
			
		}else { // 濡쒓렇�씤 �맂 寃쎌슦
			// 濡쒓렇�씤 �븳 �븘�씠�뵒�쓽 移댄뀒怨좊━ �젙蹂대�� 媛��졇�삩�떎
			categoryIdList = memberCategoryDao.getCategoryIdById(id);
			
			if(categoryIdList.size() == 0){ // 濡쒓렇�씤 �븳 �븘�씠�뵒�쓽 移댄뀒怨좊━ �젙蹂닿� �뾾�쓣 寃쎌슦
				
				//紐⑤뱺 寃뚯떆湲��쓣 媛��졇�삩�떎
				boardList = boardDao.getList();
			} else { // 濡쒓렇�씤 �븳 �븘�씠�뵒�쓽 移댄뀒怨좊━ �젙蹂닿� �엳�쓣 寃쎌슦
				
				//移댄뀒怨좊━ �젙蹂댁뿉 �뵲瑜� 寃뚯떆湲��쓣 媛��졇�삩�떎
				boardList = boardDao.getListByCategoryId(categoryIdList);
			}
		}
		if(boardList!=null)	{
			for(BoardCommand vo : boardList) {
				HashMap<String, Object> boardMap = new HashMap<String, Object>();
				//PhotoCommand photo = photoDao.getOneByBoardNum(vo.getBoard_num());
				CategoryCommand category = categoryDao.getOne(vo.getCategory_id());
				//String commentCount = commentDao.getCountByBoardNum(vo.getBoard_num());
				//if(commentCount==null)	commentCount="0";
				boolean contentFlag = false;
				String[] contentSub = vo.getContent().split("\n");
				if(contentSub.length > 3) {
					contentFlag = true;
					vo.setContent(contentSub[0] + contentSub[1] + contentSub[2]);
				}
				boardMap.put("board", vo);
				//boardMap.put("photo", photo);
				boardMap.put("category", category);
				//boardMap.put("commentCount", commentCount);
				boardMap.put("contentFlag", contentFlag);
				allBoardList.add(boardMap);
			}
		}
		
		model.addAttribute("allBoardList", allBoardList);
		return "main/main";
	}
}
