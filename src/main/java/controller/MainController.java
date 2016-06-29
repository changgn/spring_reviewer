package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.Cookie;
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
import command.ScrepCommand;
import dao.BoardDAO;
import dao.CategoryDAO;
import dao.CommentDAO;
import dao.MainDAO;
import dao.MemberCategoryDAO;
import dao.PhotoDAO;
import dao.RecommendDAO;
import dao.ScrepDAO;
import dao.SecretDAO;
import net.sf.json.JSONObject;


@Controller
public class MainController {
	@Autowired
	private ScrepDAO screpDao;
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
	@Autowired
	private SecretDAO secretDao;
	@Autowired
	private MainDAO mainDao;
	private int lastBoard_num;
	
	public void setRecommendDao(RecommendDAO recommendDao) { this.recommendDao = recommendDao; }
	public void setBoardDao(BoardDAO boardDao) { this.boardDao = boardDao; }
	public void setPhotoDao(PhotoDAO photoDao) { this.photoDao = photoDao; }
	public void setCommentDao(CommentDAO commentDao) { this.commentDao = commentDao; }
	public void setCategoryDao(CategoryDAO categoryDao) { this.categoryDao = categoryDao; }
	public void setMemberCategoryDao(MemberCategoryDAO memberCategoryDao) { this.memberCategoryDao = memberCategoryDao; }
	public void setSecretDao(SecretDAO secretDao) {this.secretDao = secretDao; }
	public void setMainDao(MainDAO mainDao) { this.mainDao = mainDao; }
	public void setScrepDao(ScrepDAO screpDao) { this.screpDao = screpDao; }

	@RequestMapping("/main/main.do")
	public String main(HttpServletRequest request, HttpServletResponse resp, Model model){
		
		String id = (String)request.getSession().getAttribute("id"); 
		String login_status = (String)request.getSession().getAttribute("login_status");
		
		lastBoard_num = 0;
		List<BoardCommand> boardList = null;
		List<HashMap<String, Object>> allBoardList = new ArrayList<HashMap<String, Object>>();
		List<String> categoryIdList = null;
		
		if(login_status==null){
			login_status = "2";
			request.getSession().setAttribute("login_status", login_status);
		}
		
		Cookie[] cookies = request.getCookies();
		for(int i=0; i<cookies.length; i++) {
			if(cookies[i].getName().equals("autoLogin")) {
				request.getSession().setAttribute("id", cookies[i].getValue());
				request.getSession().setAttribute("login_status", "1");
				id = cookies[i].getValue();
				login_status = "1";
				if(cookies[i].getValue().equals("admin")) {
					request.getSession().setAttribute("login_status", "0");
					login_status = "0";
				}
			}
		}
		
		if(login_status.equals("2")){
			boardList = mainDao.getPageList();
			
		}else {
			
			categoryIdList = memberCategoryDao.getCategoryIdById(id);
			List<Integer> secretBoardNumList = secretDao.getListById(id);
			
			if(categoryIdList.size() == 0){
				if(secretBoardNumList.size() == 0){
					boardList = mainDao.getPageList();
				}else {
					boardList = mainDao.getPageListByExBoardNum(secretBoardNumList);
				}
			} else { 
				if(secretBoardNumList.size() == 0){
					boardList = mainDao.getPageListByCategoryId(categoryIdList);
				}else {
					boardList = mainDao.getPageListByCategoryIdExBoardNum(categoryIdList, secretBoardNumList);
				}
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
				
				ScrepCommand screp = new ScrepCommand(id, vo.getBoard_num());
				if(screp.getId() != null ){
					ScrepCommand screps = screpDao.getScrep(screp);
					if(screps != null){
						boardMap.put("screpFlag", "screp");
					}else{
						boardMap.put("screpFlag", "nscrep");
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
	@RequestMapping("/main/mainAjax.do")
	public String mainAjax(HttpServletRequest request, HttpServletResponse resp, int lastBoard_num){
		
		String id = (String)request.getSession().getAttribute("id"); 
		String login_status = (String)request.getSession().getAttribute("login_status");
		

		JSONObject jso = new JSONObject();

		List<BoardCommand> boardList = null;
		List<HashMap<String, Object>> allBoardList = new ArrayList<HashMap<String, Object>>();
		List<String> categoryIdList = null;
	
		
		if(login_status==null){
			login_status = "2";
			request.getSession().setAttribute("login_status", login_status);
		}
		
		
		if(login_status.equals("2")){
			boardList = mainDao.getMorePageList(lastBoard_num);
		}else {
			categoryIdList = memberCategoryDao.getCategoryIdById(id);
			List<Integer> secretBoardNumList = secretDao.getListById(id);
			
			if(categoryIdList.size() == 0){
				if(secretBoardNumList.size() == 0){
					boardList = mainDao.getMorePageList(lastBoard_num);
				}else {
					boardList = mainDao.getMorePageListByExBoardNum(secretBoardNumList, lastBoard_num);
				}
			} else { 
				if(secretBoardNumList.size() == 0){
					boardList = mainDao.getMorePageListByCategoryId(categoryIdList, lastBoard_num);
				}else {
					boardList = mainDao.getMorePageListByCategoryIdExBoardNum(categoryIdList, secretBoardNumList, lastBoard_num);
				}
			}
		}
		if(boardList!=null)	{
			for(BoardCommand vo : boardList) {
				HashMap<String, Object> boardMap = new HashMap<String, Object>();
				PhotoCommand photo = photoDao.getOneByBoardNum(vo.getBoard_num());
				CategoryCommand category = categoryDao.getOne(vo.getCategory_id());
				String commentCount = commentDao.getCountByBoardNum(vo.getBoard_num());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String date = sdf.format(vo.getWrite_date());
				
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
				
				ScrepCommand screp = new ScrepCommand(id, vo.getBoard_num());
				if(recommend.getId() != null ){
					ScrepCommand screps = screpDao.getScrep(screp);
					if(screps != null){
						boardMap.put("screpFlag", "screp");
					}else{
						boardMap.put("screpFlag", "nscrep");
					}
				}
				
				
				boardMap.put("board", vo);
				boardMap.put("photo", photo);
				boardMap.put("category", category);
				boardMap.put("commentCount", commentCount);
				boardMap.put("contentFlag", contentFlag);
				boardMap.put("date", date);
				allBoardList.add(boardMap);
			}
		}
		jso.put("allBoardList", allBoardList);

		resp.setContentType("text/html;charset=utf-8");
		return jso.toString();
	}

}
