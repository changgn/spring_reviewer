package controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import command.RecommendCommand;
import dao.BoardDAO;
import dao.RecommendDAO;
import net.sf.json.JSONObject;

@Controller
public class RecommendController {

	@Autowired
	private BoardDAO boardDao;
	@Autowired
	private RecommendDAO recommendDao;

	public void setBoardDao(BoardDAO boardDao) { this.boardDao = boardDao; }
	public void setRecommendDao(RecommendDAO recommendDao) { this.recommendDao = recommendDao; }
		
	@ResponseBody
	@RequestMapping("/recommend/recommend.do")
	public String recommend(HttpServletRequest request, HttpServletResponse resp, Integer board_num){

		String login_status = (String)request.getSession().getAttribute("login_status");
		JSONObject jso = new JSONObject();
		
		if(login_status.equals("0") || login_status.equals("1")) {
			String id = (String)request.getSession().getAttribute("id");
			RecommendCommand command = new RecommendCommand(id, board_num);
			
			RecommendCommand recommendselect = recommendDao.getRecommend(command);
		
			HashMap<String, Object> map = new HashMap<String, Object>();
			if(recommendselect != null){
				recommendDao.deleteRecommend(recommendselect);
				jso.put("recommendFlag", "nrecommend");
			} else{
				recommendDao.insertRecommend(command);
				jso.put("recommendFlag", "recommend");
			}
			
			boardDao.updateRecommendNumByBoardNum(map);
			jso.put("board_num", board_num);
			jso.put("recommend_num", boardDao.selectContent(board_num).getRecommend_num());
		} else {
			jso.put("error", "error");
		}
		resp.setContentType("text/html;charset=utf-8");
		return jso.toString();
	}
	
	@ResponseBody
	@RequestMapping("/recommend/member.do")
	public String recommend(HttpServletResponse resp, int board_num){
		
		
		JSONObject jso = new JSONObject();
		List<String> members = recommendDao.getIdByRecommendNum(board_num);
		jso.put("members", members);
		jso.put("board_num", board_num);
		
		resp.setContentType("text/html;charset=utf-8");
		return jso.toString();
	}
}
