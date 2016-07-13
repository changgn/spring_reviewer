package controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;

@Controller
public class ReportController extends BaseController{
	@ResponseBody
	@RequestMapping("/report/member.do")
	public String recommend(HttpServletResponse resp, int board_num){
		
		JSONObject jso = new JSONObject();
		List<String> members = reportDao.getIdListByBoardNum(board_num);
		jso.put("members", members);
		jso.put("board_num", board_num);
		
		resp.setContentType("text/html;charset=utf-8");
		return jso.toString();
	}
}
