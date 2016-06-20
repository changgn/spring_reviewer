package controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.RecommendDAO;
import net.sf.json.JSONObject;

@Controller
public class RecommendController {

	@Autowired
	private RecommendDAO recommendDao;
	
	public void setRecommendDao(RecommendDAO recommendDao) { this.recommendDao = recommendDao; }
	
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
