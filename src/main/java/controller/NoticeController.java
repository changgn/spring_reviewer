package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import command.NoticeCommand;
import net.sf.json.JSONObject;

@Controller
public class NoticeController extends BaseController {
	
	@ResponseBody
	@RequestMapping(value="/notice/notice.do", method = RequestMethod.POST)
	public String notice(HttpServletResponse resp, HttpSession session) throws Exception{
		
		//session 에서 id값을 가지고 온다.
		JSONObject jso = new JSONObject();

		String id = (String)session.getAttribute("id");
		int noReadNoticeCount = noticeDao.getNoReadCountById(id);
		session.setAttribute("noReadNoticeCount", noReadNoticeCount);
		
		List<NoticeCommand> noticeList = noticeDao.getListById(id);
		List<String> dateList = new ArrayList<String>();
		
		for(NoticeCommand notice : noticeList) {
			long currTime = System.currentTimeMillis();
			long notice_date = notice.getNotice_date().getTime();
			long second = (currTime - notice_date) / 1000;
			String date_str = null;
			if(second < 60) {
				date_str = "방금전";
			} else if(second < 60*60) {
				date_str = (second/60) + "분전";
			} else if(second < 60*60*24) {
				date_str = (second/60/60) + "시간전";
			} else {
				date_str = (second/60/60/24) + "일전";
			}
			dateList.add(date_str);
		}
		jso.put("noReadNoticeCount", noReadNoticeCount);
		jso.put("noticeList", noticeList);
		jso.put("dateList", dateList);

		resp.setContentType("text/html;charset=utf-8");
		return jso.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/notice/noticeCount.do", method = RequestMethod.POST)
	public String noticeCount(HttpServletResponse resp, HttpSession session) throws Exception{

		JSONObject jso = new JSONObject();
		
		//session 에서 id값을 가지고 온다.
		String id = (String)session.getAttribute("id");
		
		if(id!=null) {
			int noReadNoticeCount = noticeDao.getNoReadCountById(id);
			session.setAttribute("noReadNoticeCount", noReadNoticeCount);
			jso.put("noReadNoticeCount", noReadNoticeCount);
		}
		
		resp.setContentType("text/html;charset=utf-8");
		return jso.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/notice/noticeRead.do", method = RequestMethod.POST)
	public String noticeRead(HttpServletResponse resp, HttpSession session, int notice_num) throws Exception{

		JSONObject jso = new JSONObject();
		
		//session 에서 id값을 가지고 온다.
		String id = (String)session.getAttribute("id");
		
		noticeDao.updateReadByNoticeNum(notice_num);
		session.setAttribute("noReadNoticeCount", noticeDao.getNoReadCountById(id));
		jso.put("notice_num", notice_num);

		resp.setContentType("text/html;charset=utf-8");
		return jso.toString();
	}
	
}
