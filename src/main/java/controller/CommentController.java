package controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import command.CommentCommand;
import dao.CommentDAO;

@Controller
public class CommentController {
	
	@Autowired
	CommentDAO commentdao;

	public void setCommentdao(CommentDAO commentdao) {
		this.commentdao = commentdao;
	}
	
	@RequestMapping(value="content/contentPro.do")
	public String Comment(CommentCommand command, Model model, int board_num,HttpSession session,
				@RequestParam("comment_textarea") String content,
				@RequestParam("comment_num") String comment_num_str ){
		
		// �Ķ���� �� �޾ƿ���
		String id = (String)session.getAttribute("id");
		
		Integer comment_num = null;
		if(comment_num_str!=null) {
			comment_num = Integer.parseInt(comment_num_str);
		}
				
		//removeByCommentNum -> comment_num������ comment ����
		if(comment_num!=null) {
			int check = commentdao.removeByCommentNum(comment_num);
			if(check>0) {
				System.out.println("��� ���� �Ϸ�");
			} else {
				System.out.println("��� ���� ����");
			}
		} else {
			command.setBoard_num(board_num);
			command.setId(id);
			command.setContent(content);
			
			
			int check = commentdao.insert(command);
			if(check>0) {
				System.out.println("��� ���� �Ϸ�");
			} else {
				System.out.println("��� ���� ����");
			}
		}
		model.addAttribute("board_num", board_num);
		
		return "redirect:/content/contentForm.do?board_num=" + board_num + "&comment=true";
	}
	
	
	
	

}
