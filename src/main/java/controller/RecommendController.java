package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import command.BoardCommand;
import command.NoticeCommand;
import command.RecommendCommand;
import net.sf.json.JSONObject;

@Controller
public class RecommendController extends BaseController {
		
	@ResponseBody
	@RequestMapping("/recommend/recommend.do")
	public String recommend(HttpServletRequest request, HttpServletResponse resp, Integer board_num){

		String login_status = (String)request.getSession().getAttribute("login_status");
		JSONObject jso = new JSONObject();
		
		if(login_status.equals("0") || login_status.equals("1")) {
			String id = (String)request.getSession().getAttribute("id");
			RecommendCommand command = new RecommendCommand(id, board_num);
			BoardCommand board = boardDao.selectContent(board_num);
			String writer = board.getId();

			List<RecommendCommand> recommendselect = recommendDao.getRecommend(command);
			if(recommendselect.size() != 0){
				recommendDao.deleteRecommend(recommendselect.get(0));
				memberDao.updateDecreaseRecommendNum(writer);
				jso.put("recommendFlag", "nrecommend");
				
			} else{
				recommendDao.insertRecommend(command);
				memberDao.updateIncreaseRecommendNum(writer);
				
				NoticeCommand noticeCommand = new NoticeCommand("recommend",id,writer,board_num);
				List<NoticeCommand> noticeList = noticeDao.getListByBoard(noticeCommand);
				if(noticeList.size() != 0) {
					noticeDao.removeByBoard(noticeCommand);
				}
				noticeDao.insert(noticeCommand);
				
				jso.put("recommendFlag", "recommend");
			}
			
			boardDao.updateRecommendNumByBoardNum(board_num, recommendDao.getRecommendCountByRecommendNum(board_num));
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
