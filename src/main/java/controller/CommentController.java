package controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import command.CommentCommand;
import dao.CommentDAO;
import net.sf.json.JSONObject;

@Controller
public class CommentController {
	
	@Autowired
	CommentDAO commentdao;

	public void setCommentdao(CommentDAO commentdao) {
		this.commentdao = commentdao;
	}
	
	@ResponseBody
	@RequestMapping(value="/content/comment.do", method = RequestMethod.POST)
	public String Comment(HttpServletResponse resp, CommentCommand command, int board_num, HttpSession session,
				@RequestParam("comment_textarea") String content ) throws Exception{
		
		//session 에서 id값을 가지고 온다.
		String id = (String)session.getAttribute("id");
		command.setId(id);
		command.setContent(content);
		JSONObject jso = new JSONObject();
		 // jason은 map구조(키,값), data라는 key(이름)로 command데이터를 주입했다.
		int check = commentdao.insert(command);
		if(check>0) {
			System.out.println("댓글 저장 완료");
		} else {
			System.out.println("댓글 저장 실패");
		}
		
		int commetNum = commentdao.getRecentCommentNum();
		CommentCommand commentCommand = commentdao.getOne(commetNum);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String date = sdf.format(commentCommand.getWrite_date());

		String commentCount = commentdao.getCountByBoardNum(command.getBoard_num());
		
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
		
		String commentId = commentdao.getId(comment_num);
		if(comment_num!=null && commentId.equals(id)) {
			commentdao.removeByCommentNum(comment_num);
		} else {
			return "redirect:/content/contentForm.do?board_num=" + board_num + "&comment=true&errorId=error";
		}
		
		return "redirect:/content/contentForm.do?board_num=" + board_num + "&comment=true";
	}
	
	
	
	

}
