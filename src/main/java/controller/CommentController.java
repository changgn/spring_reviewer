package controller;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import command.BoardCommand;
import command.CommentCommand;
import command.NoticeCommand;
import net.sf.json.JSONObject;

@Controller
public class CommentController extends BaseController{
	
	@ResponseBody
	@RequestMapping(value="/content/comment.do", method = RequestMethod.POST)
	public String Comment(HttpServletResponse resp, CommentCommand command, int board_num, HttpSession session,
				@RequestParam("comment_textarea") String content ) throws Exception{
		
		//session �뿉�꽌 id媛믪쓣 媛�吏�怨� �삩�떎.
		String id = (String)session.getAttribute("id");
		command.setId(id);
		command.setContent(content);
		JSONObject jso = new JSONObject();

		int check = commentDao.insert(command);
		if(check>0) {
			System.out.println("�뙎湲� ���옣 �셿猷�");
			BoardCommand board = boardDao.selectContent(board_num);
			String writer = board.getId();
			NoticeCommand noticeCommand = new NoticeCommand("comment",id,writer,board_num);
			noticeDao.insert(noticeCommand);
		} else {
			System.out.println("�뙎湲� ���옣 �떎�뙣");
		}
		
		int commetNum = commentDao.getRecentCommentNum();
		CommentCommand commentCommand = commentDao.getOne(commetNum);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String date = sdf.format(commentCommand.getWrite_date());

		String commentCount = commentDao.getCountByBoardNum(command.getBoard_num());
		
		jso.put("data", commentCommand);
		jso.put("cnt", commentCount);
		jso.put("date", date);

		resp.setContentType("text/html;charset=utf-8");
		return jso.toString();
	}
	
	@RequestMapping(value="/content/commentdel.do")
	public String Commentdel(HttpServletResponse resp, CommentCommand command, Model model, int board_num, HttpSession session,
				@RequestParam("comment_num") String comment_num_str ){
		
		String id = (String)session.getAttribute("id");
		
		Integer comment_num = null;
		if(comment_num_str!=null) {
			comment_num = Integer.parseInt(comment_num_str);
		}
		
		String commentId = commentDao.getId(comment_num);
		if(comment_num!=null && commentId.equals(id)) {
			commentDao.removeByCommentNum(comment_num);
		} else {
			return "redirect:/content/contentForm.do?board_num=" + board_num + "&comment=true&errorId=error";
		}
		
		return "redirect:/content/contentForm.do?board_num=" + board_num + "&comment=true";
	}
	
	@ResponseBody
	@RequestMapping(value="/content/commentMod.do")
	public String CommentMod(HttpServletResponse resp, Model model, HttpSession session, CommentCommand command ){
		
		JSONObject jso = new JSONObject();
		
		commentDao.updateByCommentNum(command);
		
		jso.put("comment", command);
		resp.setContentType("text/html;charset=utf-8");
		return jso.toString();
	}
	
	
	
	
	
	

}
