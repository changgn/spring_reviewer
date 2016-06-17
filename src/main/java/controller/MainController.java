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
import org.springframework.web.bind.annotation.ResponseBody;

import command.BoardCommand;
import command.CategoryCommand;
import command.PhotoCommand;
import command.RecommendCommand;
import dao.BoardDAO;
import dao.CategoryDAO;
import dao.CommentDAO;
import dao.MemberCategoryDAO;
import dao.PhotoDAO;
import dao.RecommendDAO;
import net.sf.json.JSONObject;


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
	@Autowired
	private RecommendDAO recommendDao;
	
	public void setRecommendDao(RecommendDAO recommendDao) { this.recommendDao = recommendDao; }
	public void setBoardDao(BoardDAO boardDao) { this.boardDao = boardDao; }
	public void setPhotoDao(PhotoDAO photoDao) { this.photoDao = photoDao; }
	public void setCommentDao(CommentDAO commentDao) { this.commentDao = commentDao; }
	public void setCategoryDao(CategoryDAO categoryDao) { this.categoryDao = categoryDao; }
	public void setMemberCategoryDao(MemberCategoryDAO memberCategoryDao) { this.memberCategoryDao = memberCategoryDao; }
	
	@RequestMapping("/main/main.do")
	public String main(HttpServletRequest request, HttpServletResponse response, Model model){
		
		String id = (String)request.getSession().getAttribute("id"); 
		String login_status = (String)request.getSession().getAttribute("login_status");
		
		List<BoardCommand> boardList = null;
		List<HashMap> allBoardList = new ArrayList<HashMap>();
		List<String> categoryIdList = null;
		
		if(login_status==null){
			login_status = "2";
			request.getSession().setAttribute("login_status", login_status);
		}
		if(login_status.equals("2")){
			
			boardList = boardDao.getList();
			
		}else {
			
			categoryIdList = memberCategoryDao.getCategoryIdById(id);
			
			if(categoryIdList.size() == 0){
				
				boardList = boardDao.getList();
			} else { 
				
				boardList = boardDao.getListByCategoryId(categoryIdList);
			}
		}
		if(boardList!=null)	{
			for(BoardCommand vo : boardList) {
				HashMap<String, Object> boardMap = new HashMap<String, Object>();
				PhotoCommand photo = photoDao.getOneByBoardNum(vo.getBoard_num());
				CategoryCommand category = categoryDao.getOne(vo.getCategory_id());
				String commentCount = commentDao.getCountByBoardNum(vo.getBoard_num());
		
				if(commentCount==null)	commentCount="0";
				boolean contentFlag = false;
				String[] contentSub = vo.getContent().split("\n");
				if(contentSub.length > 3) {
					contentFlag = true;
					vo.setContent(contentSub[0] + contentSub[1] + contentSub[2]);
				}
				
				RecommendCommand recommend = new RecommendCommand(id, vo.getBoard_num());
				if(recommend.getId() != null ){
					RecommendCommand recommends = recommendDao.getRecommend(recommend);
					if(recommends != null){
						boardMap.put("recommendFlag", "recommend");
					}else{
						boardMap.put("recommendFlag", "nrecommend");
					}
				}
					
				boardMap.put("board", vo);
				boardMap.put("photo", photo);
				boardMap.put("category", category);
				boardMap.put("commentCount", commentCount);
				boardMap.put("contentFlag", contentFlag);
				allBoardList.add(boardMap);
			}
		}
		
		model.addAttribute("allBoardList", allBoardList);
		return "main/main";
	}
	
	@ResponseBody
	@RequestMapping("/recommend/recommend.do")
	public String recommend(HttpServletRequest request, HttpServletResponse resp, Integer board_num){
		
		String id = (String)request.getSession().getAttribute("id");
		
		JSONObject jso = new JSONObject();
		RecommendCommand command = new RecommendCommand(id, board_num);
		
		RecommendCommand recommendselect = recommendDao.getRecommend(command);
	
		if(recommendselect != null){
			boardDao.RecommendByBoardNumDecrease(board_num);
			recommendDao.deleteRecommend(recommendselect);
			jso.put("recommendFlog", "nrecommend");
			
			
		} else{
			boardDao.updateRecommendByBoardNum(board_num);
			recommendDao.insertBoard(command);
			jso.put("recommendFlog", "recommend");
		}
		
		jso.put("recommend", boardDao.selectContent(board_num).getRecommend_num());
		
		resp.setContentType("text/html;charset=utf-8");
		return jso.toString();
	}
	
	
	
}
