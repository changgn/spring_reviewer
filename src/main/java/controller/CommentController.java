package controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@RequestMapping(value="/content/contentPro.do")
	public void Comment(HttpServletResponse resp, CommentCommand command, int board_num, HttpSession session,
				@RequestParam("comment_textarea") String content ) throws Exception{
		
		String id = (String)session.getAttribute("id");

		command.setBoard_num(board_num);
		command.setId(id);
		command.setContent(content);
		
		
		int check = commentdao.insert(command);
		if(check>0) {
			System.out.println("댓글 저장 완료");
		} else {
			System.out.println("댓글 저장 실패");
		}
		
		JSONObject jso = new JSONObject();
		jso.put("data", command);
		
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(jso.toString());
	}
	
	@RequestMapping(value="/content/contentdel.do")
	public void Commentdel(HttpServletResponse resp, CommentCommand command, Model model, int board_num,HttpSession session,
				@RequestParam("comment_textarea") String content,
				@RequestParam("comment_num") String comment_num_str ){
		

		String id = (String)session.getAttribute("id");
		
		Integer comment_num = null;
		if(comment_num_str!=null) {
			comment_num = Integer.parseInt(comment_num_str);
		}
				
	
		if(comment_num!=null) {
			int check = commentdao.removeByCommentNum(comment_num);
			if(check>0) {
				System.out.println("댓글 저장 완료");
			} else {
				System.out.println("댓글 저장 실패");
			}
		} else {
			command.setBoard_num(board_num);
			command.setId(id);
			command.setContent(content);
			
			
			int check = commentdao.insert(command);
			if(check>0) {
				System.out.println("댓글 저장 완료");
			} else {
				System.out.println("댓글 저장 실패");
			}
		}

/*		JSONObject jso = new JSONObject();
*/		
	}
	
	
	
	

}
