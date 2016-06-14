package controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	@RequestMapping(value = "/content/")
	public String city() throws Exception {
		return "ajax/city";
	}
	
	@RequestMapping(value="/contentPro.do", method = RequestMethod.POST)
	public void Comment(HttpServletResponse resp, CommentCommand command, int board_num, HttpSession session,
				@RequestParam("comment_textarea") String content ) throws Exception{
		
		//session 에서 id값을 가지고 온다.
		String id = (String)session.getAttribute("id");
		System.out.println("ddddddd");
		command.setBoard_num(board_num);
		command.setId(id);
		command.setContent(content);
		System.out.println("dddddd2d");

/*		int check = commentdao.insert(command);
*/		JSONObject jso = new JSONObject();
		 // jason은 map구조(키,값), data라는 key(이름)로 command데이터를 주입했다.
		jso.put("data", commentdao.insert(command));
		System.out.println("ddddd3dd");

		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		// out.print 내용을 ajax의 dataType이 jason에게
		// 데이터 쏴줌
		out.print(jso.toString());
/*		
		if(check>0) {
			System.out.println("댓글 저장 완료");
		} else {
			System.out.println("댓글 저장 실패");
		}*/
		

	}
	
	@RequestMapping(value="/contentdel.do")
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
