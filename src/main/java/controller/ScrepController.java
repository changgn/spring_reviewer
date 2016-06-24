package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import command.ScrepCommand;
import command.BoardCommand;
import command.CategoryCommand;
import command.CommentCommand;
import command.PhotoCommand;
import command.RecommendCommand;
import command.SecretCommand;

import dao.ScrepDAO;
import dao.BoardDAO;
import dao.CategoryDAO;
import dao.CommentDAO;
import dao.PhotoDAO;
import dao.RecommendDAO;
import net.sf.json.JSONObject;


@Controller
public class ScrepController {

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
	
	
	@RequestMapping(value="/screp/screpList.do")
	public String Screp(HttpServletRequest request, String id, int board_num, String comment, Model model){
/*		//스크랩 버튼 insert,    id 값으로 board_num을 뽑아오고 -> myprofile
		//스크랩 숫자 저장
		int screpCount = ScrepDao.getScrepCountByScrepNum(board_num);
		model.addAttribute("screpCount", screpCount);
		
		//스크랩 리스트 불러오기
		List<ScrepCommand> screpList = null;
		screpList = ScrepDao.getScrepList();
		id = (String) request.getSession().getAttribute("id");
		board_num = (Integer)request.getSession().getAttribute("board_num");
		screpList = ScrepDao.getListBoardNum(board_num);
		
		if(id != null ){
			ScrepCommand Screp = new ScrepCommand(id,board_num);
			ScrepCommand Screps = ScrepDao.getScrep(Screp);
			if(Screps != null){
				model.addAttribute("screpFlag", "scrap");
			}else{
				model.addAttribute("screpFlag", "nscrap");
			}
		}
		
		if(screpList != null) { 
			model.addAttribute("screpList", screpList);
			model.addAttribute("screpCount", screpList.size());
		}	
		
		model.addAttribute("board_num", board_num);
		*/
		
		int screpCount = ScrepDao.getScrepCountByScrepNum(id);
		String paramId = request.getParameter("id");
		
		List<BoardCommand> boardList = null;
		List<Integer> boardNumList = ScrepDao.getScrepListById(id);
		List<HashMap<String,Object>> allBoardList = new ArrayList<HashMap<String,Object>>();
		
		if(boardNumList.size() == 0){
			boardList = null;
		}else {
			boardList = BoardDao.getListByBoardNum(boardNumList);
		}
		
		
		if(boardList!=null)	{
			for(BoardCommand vo : boardList) {
				HashMap<String, Object> boardMap = new HashMap<String, Object>();
				PhotoCommand photo = PhotoDao.getOneByBoardNum(vo.getBoard_num());
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
					RecommendCommand recommends = RecommendDao.getRecommend(recommend);
					if(recommends != null){
						boardMap.put("recommendFlag", "recommend");
					}else{
						boardMap.put("recommendFlag", "nrecommend");
					}
				}
				
				ScrepCommand Screp = new ScrepCommand(id, vo.getBoard_num());
				if(Screp.getId() != null ){
					ScrepCommand Screps = ScrepDao.getScrep(Screp);
					if(Screps != null){
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
		model.addAttribute("profileId", id);
		model.addAttribute("allBoardList", allBoardList);
		model.addAttribute("screpCount", screpCount);
		return "screp/screpList";
	}
}