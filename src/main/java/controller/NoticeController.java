package controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import command.NoticeCommand;
import dao.NoticeDAO;
import net.sf.json.JSONObject;

@Controller
public class NoticeController {
	
	@Autowired
	NoticeDAO noticeDao;
	public void setNoticeDao(NoticeDAO noticeDao) {
		this.noticeDao = noticeDao;
	}


	@ResponseBody
	@RequestMapping(value="/notice/notice.do", method = RequestMethod.POST)
	public String Comment(HttpServletResponse resp, HttpSession session, String notice ) throws Exception{
		
		//session 에서 id값을 가지고 온다.
		String id = (String)session.getAttribute("id");
		List<NoticeCommand> noticeList = noticeDao.getListById(id);
		JSONObject jso = new JSONObject();

		jso.put("noticeList", noticeList);

		resp.setContentType("text/html;charset=utf-8");
		return jso.toString();
	}
	
}
