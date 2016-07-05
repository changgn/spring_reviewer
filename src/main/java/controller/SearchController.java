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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import command.BoardCommand;
import command.CategoryCommand;
import command.PhotoCommand;
import command.ProfilePhotoCommand;
import command.RecommendCommand;
import command.ScrepCommand;
import dao.BoardDAO;
import dao.CategoryDAO;
import dao.CommentDAO;
import dao.MainDAO;
import dao.PhotoDAO;
import dao.ProfilePhotoDAO;
import dao.RecommendDAO;
import dao.ScrepDAO;
import net.sf.json.JSONObject;

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
	@Autowired
	private RecommendDAO recommendDao;
	@Autowired
	private ScrepDAO screpDao;
	@Autowired
	private MainDAO mainDao;
	@Autowired
	private ProfilePhotoDAO ProfilePhotoDao;
	private String addcount;
	private String searchContent;
	private List<String> categoryIdList;
	
	public void setCategorydao(CategoryDAO categoryDao) { this.categoryDao = categoryDao; }
	public void setPhotodao(PhotoDAO photoDao) { this.photoDao = photoDao; }
	public void setCommentdao(CommentDAO commentDao) { this.commentDao = commentDao; }
	public void setBoarddao(BoardDAO boardDao) { this.boardDao = boardDao; }
	public void setMainDao(MainDAO mainDao) { this.mainDao = mainDao; }
	public void setScrepDao(ScrepDAO screpDao) { this.screpDao = screpDao; }
	
	public void setRecommendDao(RecommendDAO recommendDao) { this.recommendDao = recommendDao; }
	public void setProfilePhotoDao(ProfilePhotoDAO profilePhotoDao) { ProfilePhotoDao = profilePhotoDao; }
	@RequestMapping(value="/search/searchForm.do")
	public String searchForm(Model model){
		model.addAttribute("firstCheck", 0);
		return "search/searchForm";
	}
	
	@RequestMapping(value="/search/search.do")
	public String search(HttpServletRequest request, Model model, String addcount, String searchContent){
		this.addcount = addcount;
		this.searchContent = searchContent;
		String id = (String)request.getSession().getAttribute("id"); 
		List<HashMap<String, Object>> allBoardList = new ArrayList<HashMap<String, Object>>();
		List<BoardCommand> boardList = null;
		int firstCheck = 1;
		
		// 검색할 카테고리 갯수 가져오기
		if(addcount == null) {addcount = "0";}
		int addcount_int = Integer.parseInt(addcount);
		
		int searchCount = 0;

		if(searchContent != null) {
			
			if(addcount_int != 0) {	// 카테고리를 선택했을 때
				
				List<String> categoryIdList = new ArrayList<String>();
				for(int i=1; i<addcount_int+1; i++) {
					String addname = "add" + i;
					// 검색할 카테고리 ID 가져오기
					String category_id = request.getParameter(addname);
					categoryIdList.add(category_id);
					System.out.println("검색할 카테고리 id : " + category_id);
				}
				this.categoryIdList = categoryIdList;
				
				boardList = mainDao.getPageListByCategoryIdContent(categoryIdList, searchContent);
				System.out.println("검색할 카테고리 수 : " + addcount_int);
				System.out.println("검색할 내용 : " + searchContent);
				
			} else { // 카테고리를 선택하지 않았을 때

				boardList = mainDao.getPageListByContent(searchContent);
				System.out.println("검색할 카테고리 수 : " + addcount_int);
				System.out.println("검색할 내용 : " + searchContent);
			}
			if(boardList==null) {
				searchCount = 0;
			} else {
				for(BoardCommand board : boardList) {
					HashMap<String, Object> boardMap = new HashMap<String, Object>();
					PhotoCommand photo = photoDao.getOneByBoardNum(board.getBoard_num());
					ProfilePhotoCommand profilePhoto = ProfilePhotoDao.getOneById(board.getId());
					CategoryCommand category = categoryDao.getOne(board.getCategory_id());
					String commentCount = commentDao.getCountByBoardNum(board.getBoard_num());
					if(commentCount==null)	commentCount="0";
					boolean contentFlag = false;
					String[] contentSub = board.getContent().split("\n");
					if(contentSub.length > 3) {
						contentFlag = true;
						board.setContent(contentSub[0] + contentSub[1] + contentSub[2]);
					}
					if(id != null ){
						RecommendCommand recommend = new RecommendCommand(id, board.getBoard_num());
						List<RecommendCommand> recommends = recommendDao.getRecommend(recommend);
						if(recommends.size() != 0){
							boardMap.put("recommendFlag", "recommend");
						}else{
							boardMap.put("recommendFlag", "nrecommend");
						}
						ScrepCommand screp = new ScrepCommand(id, board.getBoard_num());
						if(screp.getId() != null ){
							ScrepCommand screps = screpDao.getScrep(screp);
							if(screps != null){
								boardMap.put("screpFlag", "screp");
							}else{
								boardMap.put("screpFlag", "nscrep");
							}
						}
					}
					boardMap.put("board", board);
					boardMap.put("photo", photo);
					boardMap.put("profilePhoto", profilePhoto);
					boardMap.put("category", category);
					boardMap.put("commentCount", commentCount);
					boardMap.put("contentFlag", contentFlag);
					allBoardList.add(boardMap);
				}
				model.addAttribute("allBoardList", allBoardList);
				searchCount = boardList.size();
			}
		}
		
		
		model.addAttribute("searchCount", searchCount);
		model.addAttribute("firstCheck", firstCheck);
		model.addAttribute("boardList", boardList);
		model.addAttribute("searchContent", searchContent);
		return "search/searchForm";
	}
	
	@ResponseBody
	@RequestMapping(value="/search/searchmore.do")
	public String searchmore(HttpServletRequest request, HttpServletResponse resp, int lastBoard_num){
		
		String id = (String)request.getSession().getAttribute("id"); 
		String login_status = (String)request.getSession().getAttribute("login_status");
	
		JSONObject jso = new JSONObject();
		List<HashMap<String, Object>> allBoardList = new ArrayList<HashMap<String, Object>>();
		List<BoardCommand> boardList = null;

		
		// 검색할 카테고리 갯수 가져오기
		if(addcount == null) {addcount = "0";}
		int addcount_int = Integer.parseInt(addcount);
		

		if(searchContent != null) {
			
			if(addcount_int != 0) {	// 카테고리를 선택했을 때

				boardList = mainDao.getMorePageListByCategoryIdContent(categoryIdList, searchContent, lastBoard_num);
				System.out.println("검색할 카테고리 수 : " + addcount_int);
				System.out.println("검색할 내용 : " + searchContent);
				
			} else { // 카테고리를 선택하지 않았을 때

				boardList = mainDao.getMorePageListByContent(searchContent, lastBoard_num);
				System.out.println("검색할 카테고리 수 : " + addcount_int);
				System.out.println("검색할 내용 : " + searchContent);
			}
			if(boardList!=null) {
				for(BoardCommand board : boardList) {
					HashMap<String, Object> boardMap = new HashMap<String, Object>();
					PhotoCommand photo = photoDao.getOneByBoardNum(board.getBoard_num());
					ProfilePhotoCommand profilePhoto = ProfilePhotoDao.getOneById(board.getId());
					CategoryCommand category = categoryDao.getOne(board.getCategory_id());
					String commentCount = commentDao.getCountByBoardNum(board.getBoard_num());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					String date = sdf.format(board.getWrite_date());
					if(commentCount==null)	commentCount="0";
					boolean contentFlag = false;
					String[] contentSub = board.getContent().split("\n");
					if(contentSub.length > 3) {
						contentFlag = true;
						board.setContent(contentSub[0] + contentSub[1] + contentSub[2]);
					}
					if(id != null ){
						RecommendCommand recommend = new RecommendCommand(id, board.getBoard_num());
						List<RecommendCommand> recommends = recommendDao.getRecommend(recommend);
						if(recommends.size() != 0){
							boardMap.put("recommendFlag", "recommend");
						}else{
							boardMap.put("recommendFlag", "nrecommend");
						}
						ScrepCommand screp = new ScrepCommand(id, board.getBoard_num());
						if(screp.getId() != null ){
							ScrepCommand screps = screpDao.getScrep(screp);
							if(screps != null){
								boardMap.put("screpFlag", "screp");
							}else{
								boardMap.put("screpFlag", "nscrep");
							}
						}
					}
					boardMap.put("board", board);
					boardMap.put("photo", photo);
					boardMap.put("profilePhoto", profilePhoto);
					boardMap.put("category", category);
					boardMap.put("commentCount", commentCount);
					boardMap.put("contentFlag", contentFlag);
					boardMap.put("date", date);
					allBoardList.add(boardMap);
				}
				jso.put("allBoardList", allBoardList);
				System.out.println(allBoardList.size());
			}
		}
		jso.put("id", id);
		jso.put("login_status", login_status);
		
		resp.setContentType("text/html;charset=utf-8");
		return jso.toString();
	}
	
}
