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


import command.ScrepCommand;
import command.BoardCommand;
import command.CategoryCommand;
import command.PhotoCommand;
import command.ProfilePhotoCommand;
import command.RecommendCommand;

import dao.ScrepDAO;
import dao.BoardDAO;
import dao.CategoryDAO;
import dao.CommentDAO;
import dao.FollowDAO;
import dao.MainDAO;
import dao.PhotoDAO;
import dao.ProfilePhotoDAO;
import dao.RecommendDAO;
import net.sf.json.JSONObject;


@Controller
public class ScrepController {
	@Autowired
	private MainDAO MainDao;
	@Autowired
	private ScrepDAO ScrepDao;
	@Autowired
	private BoardDAO BoardDao;
	@Autowired
	private CategoryDAO CategoryDao;
	@Autowired
	private PhotoDAO PhotoDao;
	@Autowired
	private CommentDAO CommentDao;
	@Autowired
	private RecommendDAO RecommendDao;
	@Autowired
	private FollowDAO followDao;
	@Autowired
	private ProfilePhotoDAO ProfilePhotoDao;
	
	

	public void setBoardDao(BoardDAO boardDao) {
		BoardDao = boardDao;
	}
	public void setCategoryDao(CategoryDAO categoryDao) {
		CategoryDao = categoryDao;
	}
	public CommentDAO getCommentdao() {
		return CommentDao;
	}
	public void setCommentdao(CommentDAO commentDao) {
		this.CommentDao = commentDao;
	}
	public void setPhotoDao(PhotoDAO photoDao) {
		PhotoDao = photoDao;
	}
    public void setScrepDao(ScrepDAO ScrepDao) {
		this.ScrepDao = ScrepDao;
	}
	public void setRecommendDao(RecommendDAO recommendDao) { this.RecommendDao = recommendDao; }
	public void setFollowDao(FollowDAO followDao) {
		this.followDao = followDao;
	}
	public void setMainDao(MainDAO MainDao) {
		this.MainDao = MainDao;
	}
	public void setProfilePhotoDao(ProfilePhotoDAO profilePhotoDao) { 
		ProfilePhotoDao = profilePhotoDao; 
	}
	
	@ResponseBody
	@RequestMapping(value="/screp/screpInsert.do")
	public String Screp(HttpServletRequest request, HttpServletResponse resp, int board_num){
		
		String id = (String)request.getSession().getAttribute("id");
		
		JSONObject jso = new JSONObject();
		
		ScrepCommand command = new ScrepCommand(id, board_num);
		ScrepDao.insertScrep(command);
		
		jso.put("screpCount", command);
		resp.setContentType("text/html;charset=utf-8");
		return jso.toString();
		
	}
	

	@RequestMapping(value="/profile/screpList.do")
	public String ScrepList(HttpServletRequest request, String comment, Model model){

		String id = (String) request.getSession().getAttribute("id");
		String paramId = request.getParameter("id");
		int boardCount = 0;
		int screpCount = ScrepDao.getScrepCountByScrepNum(paramId);
		model.addAttribute("screpCount", screpCount);
		
		int myCount = ScrepDao.getCountByBoardNum(paramId);
		model.addAttribute("myCount", myCount);
		
		int followerCount =followDao.countfrom(paramId);
		model.addAttribute("followerCount", followerCount);
		//팔로잉 숫자 저장
		int followingCount = followDao.countto(paramId);
		model.addAttribute("followingCount", followingCount);
		
		ProfilePhotoCommand myProfilePhoto = ProfilePhotoDao.getOneById(paramId);
		model.addAttribute("myProfilePhoto", myProfilePhoto);
		
		List<BoardCommand> boardList = null;
		List<Integer> boardNumList = ScrepDao.getScrepListById(paramId);
		List<HashMap<String,Object>> allBoardList = new ArrayList<HashMap<String,Object>>();
		
		if(boardNumList.size() == 0){
			boardList = null;
		}else {
			boardList = MainDao.getPageListByBoardNum(boardNumList);
		}
		
		
		if(boardList!=null)	{
			boardCount = boardList.size();
			for(BoardCommand vo : boardList) {
				HashMap<String, Object> boardMap = new HashMap<String, Object>();
				PhotoCommand photo = PhotoDao.getOneByBoardNum(vo.getBoard_num());
				ProfilePhotoCommand profilePhoto = ProfilePhotoDao.getOneById(vo.getId());
				CategoryCommand category = CategoryDao.getOne(vo.getCategory_id());
				String commentCount = CommentDao.getCountByBoardNum(vo.getBoard_num());
		
				if(commentCount==null)	commentCount="0";
				boolean contentFlag = false;
				String[] contentSub = vo.getContent().split("\n");
				if(contentSub.length > 3) {
					contentFlag = true;
					vo.setContent(contentSub[0] + contentSub[1] + contentSub[2]);
				}
				
				RecommendCommand recommend = new RecommendCommand(id, vo.getBoard_num());
				if(recommend.getId() != null ){
					List<RecommendCommand> recommends = RecommendDao.getRecommend(recommend);
					if(recommends.size() != 0){
						boardMap.put("recommendFlag", "recommend");
					}else{
						boardMap.put("recommendFlag", "nrecommend");
					}
				}
				
				ScrepCommand screp = new ScrepCommand(id, vo.getBoard_num());
				if(screp.getId() != null ){
					ScrepCommand screps = ScrepDao.getScrep(screp);
					if(screps != null){
						boardMap.put("screpFlag", "screp");
					}else{
						boardMap.put("screpFlag", "nscrep");
					}
				}
				

					
				boardMap.put("board", vo);
				boardMap.put("photo", photo);
				boardMap.put("profilePhoto", profilePhoto);
				boardMap.put("category", category);
				boardMap.put("commentCount", commentCount);
				boardMap.put("contentFlag", contentFlag);
				allBoardList.add(boardMap);
			}
		}
		
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

		model.addAttribute("pageInfo", "screp");
		model.addAttribute("paramId", paramId);
		model.addAttribute("boardCount", boardCount);
		model.addAttribute("allBoardList", allBoardList);
		
		return "profile/myProfile";
	}
	
	
	//스크랩 갯수 증가
	@ResponseBody
	@RequestMapping("/screp/screp.do")
	public String Screp(HttpServletRequest request, HttpServletResponse resp, Integer board_num, String page, String paramId){

		String login_status = (String)request.getSession().getAttribute("login_status");
		JSONObject jso = new JSONObject();
		
		if(login_status.equals("0") || login_status.equals("1")) {
			String id = (String)request.getSession().getAttribute("id");
			ScrepCommand screp = new ScrepCommand(id, board_num);

			ScrepCommand Screpselect = null;
			Screpselect = ScrepDao.getScrep(screp);
			HashMap<String, Object> map = new HashMap<String, Object>();
			if(Screpselect != null){
				ScrepDao.deleteScrep(Screpselect);
				map.put("board_num", board_num);
				map.put("screp", ScrepDao.getCountByScrepNum(board_num));
				ScrepDao.updateScrepByBoardNum(map);
				jso.put("screpFlag", "nscrep");
			} else{
				ScrepDao.insertScrep(screp);
				map.put("board_num", board_num);
				map.put("screp", ScrepDao.getCountByScrepNum(board_num));
				ScrepDao.updateScrepByBoardNum(map);
				jso.put("screpFlag", "screp");
			}
			int screpCount = 0;
			if(page != null){
				screpCount = ScrepDao.getScrepCountByScrepNum(paramId);
			}
			jso.put("screpCount", screpCount);
			jso.put("screp_num", BoardDao.selectContent(board_num).getScrep());
			jso.put("board_num", board_num);
		} else {
			jso.put("error", "error");
		}
		resp.setContentType("text/html;charset=utf-8");
		return jso.toString();
	}
	
	//멤버 스크랩 갯수
	@ResponseBody
	@RequestMapping("/screp/member.do")
	public String screpMember(HttpServletResponse resp, int board_num){
		JSONObject jso = new JSONObject();
		List<String> members = ScrepDao.getIdByScrepNum(board_num);
		jso.put("members", members);
		jso.put("board_num", board_num);
		
		resp.setContentType("text/html;charset=utf-8");
		return jso.toString();
	}


}