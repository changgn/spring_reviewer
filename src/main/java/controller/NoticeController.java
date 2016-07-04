package controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import command.CommentCommand;
import dao.CommentDAO;
import net.sf.json.JSONObject;

@Controller
public class NoticeController {
	
	@Autowired
	CommentDAO commentdao;

	
	@ResponseBody
	@RequestMapping(value="/notice/notice.do", method = RequestMethod.POST)
	public String Comment(HttpServletResponse resp, HttpSession session, String notice ) throws Exception{
		
		//session 에서 id값을 가지고 온다.
		String id = (String)session.getAttribute("id");

		JSONObject jso = new JSONObject();

		jso.put("id", id);

		resp.setContentType("text/html;charset=utf-8");
		return jso.toString();
	}
	
}
