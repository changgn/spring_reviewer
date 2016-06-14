package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import dao.PhotoDAO;

@Controller
public class SearchController {
	@Autowired
	BoardDAO boardDao; 
	@Autowired
	PhotoDAO photoDao;
	@Autowired
	CommentDAO commentDao;
	@Autowired
	CategoryDAO categoryDao;

	public void setCategorydao(CategoryDAO categoryDao) {
		this.categoryDao = categoryDao;
	}
	public void setPhotodao(PhotoDAO photoDao) {
		this.photoDao = photoDao;
	}
	public void setCommentdao(CommentDAO commentDao) {
		this.commentDao = commentDao;
	}
	public void setBoarddao(BoardDAO boardDao) {
		this.boardDao = boardDao;
	}
	
	@RequestMapping(value="/search/search.do")
	public String search(HttpServletRequest request, Model model, String addcount, String searchContent){

		
		List<HashMap> allBoardList = new ArrayList<HashMap>();
		List<BoardCommand> boardList = null;
		int firstCheck = 0;
		
		// 검색할 카테고리 갯수 가져오기
		if(addcount == null) {addcount = "0";}
		int addcount_int = Integer.parseInt(addcount);
		
		int searchCount = 0;

		if(searchContent != null) {
			// 처음 실행이 아닐 시
			firstCheck = 1;
			if(addcount_int != 0) {	// 카테고리를 선택했을 때
				
				List<String> categoryIdList = new ArrayList<String>();
				
				for(int i=1; i<addcount_int+1; i++) {
					String addname = "add" + i;
					// 검색할 카테고리 ID 가져오기
					String category_id = request.getParameter(addname);
					categoryIdList.add(category_id);
					System.out.println("검색할 카테고리 id : " + category_id);
				}
				
				HashMap<String, Object> categoryIdContentMap = new HashMap<String, Object>();
				categoryIdContentMap.put("categoryIdList", categoryIdList);
				categoryIdContentMap.put("content", searchContent);
				
				boardList = boardDao.getListByCategoryIdContent(categoryIdContentMap);
				System.out.println("검색할 카테고리 수 : " + addcount_int);
				System.out.println("검색할 내용 : " + searchContent);
				
			} else { // 카테고리를 선택하지 않았을 때

				boardList = boardDao.getListByContent(searchContent);
				System.out.println("검색할 카테고리 수 : " + addcount_int);
				System.out.println("검색할 내용 : " + searchContent);
			}
			if(boardList==null) {
				searchCount = 0;
			} else {
				for(BoardCommand board : boardList) {
					HashMap<String, Object> boardMap = new HashMap<String, Object>();
					PhotoCommand photo = photoDao.getOneByBoardNum(board.getBoard_num());
					CategoryCommand category = categoryDao.getOne(board.getCategory_id());
					String commentCount = commentDao.getCountByBoardNum(board.getBoard_num());
					if(commentCount==null)	commentCount="0";
					boolean contentFlag = false;
					String[] contentSub = board.getContent().split("\n");
					if(contentSub.length > 3) {
						contentFlag = true;
						board.setContent(contentSub[0] + contentSub[1] + contentSub[2]);
					}
					boardMap.put("board", board);
					boardMap.put("photo", photo);
					boardMap.put("category", category);
					boardMap.put("commentCount", commentCount);
					boardMap.put("contentFlag", contentFlag);
					allBoardList.add(boardMap);
				}
				model.addAttribute("allBoardList", allBoardList);
				searchCount = boardList.size();
			}
			System.out.println("검색된 게시글 수 : " + searchCount);
		}
		
		
		model.addAttribute("searchCount", searchCount);
		model.addAttribute("firstCheck", firstCheck);
		model.addAttribute("boardList", boardList);
		return "search/searchForm";
	}
}
