package controller;

import java.util.ArrayList;
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
	public String Comment(HttpServletResponse resp, HttpSession session) throws Exception{
		
		//session 에서 id값을 가지고 온다.
		JSONObject jso = new JSONObject();
		
		String id = (String)session.getAttribute("id");
		List<NoticeCommand> noticeList = noticeDao.getListById(id);
		List<String> dateList = new ArrayList<String>();
		
		for(NoticeCommand notice : noticeList) {
			long currTime = System.currentTimeMillis();
			long notice_date = notice.getNotice_date().getTime();
			long second = (currTime - notice_date) / 1000;
			String date_str = null;
			if(second < 60) {
				date_str = second + "초전";
			} else if(second < 60*60) {
				date_str = (second/60) + "분전";
			} else if(second < 60*60*24) {
				date_str = (second/60/60) + "시간전";
			} else {
				date_str = (second/60/60/24) + "일전";
			}
			dateList.add(date_str);
		}
		jso.put("noticeList", noticeList);
		jso.put("dateList", dateList);

		resp.setContentType("text/html;charset=utf-8");
		return jso.toString();
	}
	
}
