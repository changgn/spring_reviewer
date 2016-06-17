package controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import command.BoardCommand;
import command.CategoryCommand;
import command.CommentCommand;
import command.PhotoCommand;
import dao.BoardDAO;
import dao.CategoryDAO;
import dao.CommentDAO;
import dao.PhotoDAO;



@Controller
public class BoardController {

	@Autowired
	BoardDAO boarddao; 
	@Autowired
	PhotoDAO photodao;
	@Autowired
	CommentDAO commentdao;
	@Autowired
	CategoryDAO categorydao;

	public void setCategorydao(CategoryDAO categorydao) {
		this.categorydao = categorydao;
	}
	public void setPhotodao(PhotoDAO photodao) {
		this.photodao = photodao;
	}
	public void setCommentdao(CommentDAO commentdao) {
		this.commentdao = commentdao;
	}
	public void setBoarddao(BoardDAO boarddao) {
		this.boarddao = boarddao;
	}

	@RequestMapping(value="write/writeForm.do", method=RequestMethod.GET)
	public String insertboard(){
		return "write/writeForm";
	}
	
	@RequestMapping(value="/write/writeForm.do", method=RequestMethod.POST)
	public String insertboard(MultipartHttpServletRequest mhsq, HttpServletRequest request, 
			String addCategory, String boardContent)
			throws IOException {
	

		String id = (String)request.getSession().getAttribute("id");
		String savePath = "/fileSave";
		
		
		BoardCommand command = new BoardCommand();
		command.setId(id);
		command.setCategory_id(addCategory);
		command.setContent(boardContent);
		boarddao.insertBoard(command);
		
		int board_num = boarddao.getRecentBoardNumById(id).intValue();

	
		Iterator<String> filenames = mhsq.getFileNames();
		while (filenames.hasNext()) {
		
			MultipartFile file = mhsq.getFile(filenames.next());
			
			String genId = UUID.randomUUID().toString(); 
			String o_fileName = file.getOriginalFilename();
			String fileName = genId + "_" + o_fileName;
			String realPath =  savePath + "/" + fileName;
			
			PhotoCommand pcommand = new PhotoCommand(); 
			pcommand.setboard_num(board_num);
			pcommand.setO_fileName(o_fileName);
			pcommand.setFileName(fileName);
			pcommand.setRealPath(realPath);
			
			if(!o_fileName.equals("")){
				photodao.insert(pcommand);
				File f = new File(request.getSession().getServletContext().getRealPath(savePath));
				File f2 = new File(f, fileName);
				file.transferTo(f2);
			}
		}
	/*	boarddao.insertBoard(command);*/
		return "redirect:/content/contentForm.do?board_num=" + board_num;
	}

	
	
	@RequestMapping(value="/content/contentForm.do")
	public String selectcontent(String board_num, String comment, Model model){
	
		List<PhotoCommand> photoList = null;
		List<CommentCommand> commentList = null;
		BoardCommand board = null;
		
		
		board = boarddao.selectContent(Integer.parseInt(board_num));
		photoList = photodao.getListByBoardNum(Integer.parseInt(board_num));
		commentList = commentdao.getListByBoardNum(Integer.parseInt(board_num));
		
		if(board != null) {
			CategoryCommand category = categorydao.getOne(board.getCategory_id());
			model.addAttribute("board", board);
			model.addAttribute("category", category);
			
		} else {
			model.addAttribute("error", "error");
		}
		if(photoList != null) {
			model.addAttribute("photoList", photoList);
		}
		if(commentList != null) { 
			model.addAttribute("commentList", commentList);
			model.addAttribute("commentCount", commentList.size());
		}	
		

		model.addAttribute("board_num", board_num);
		model.addAttribute("comment", comment);
		return "content/contentForm";
		
	}
	
	@RequestMapping(value="/content/deleteContent.do")
	public String delete(HttpSession session, Model model, String board_num, @RequestParam("id") String writer){

		String id = (String)session.getAttribute("id");
		
		if(id.equals(writer)) { 
			boarddao.deleteContent(Integer.parseInt(board_num));
			
		} else {
			model.addAttribute("errorId", "errorId");
		}
		return "content/deleteContent";
	}
	
	@RequestMapping(value="/content/reportPro.do")
	public String report(HttpServletRequest request, Model model){

		String board_num_str = request.getParameter("board_num");
		
		if(board_num_str != null){
			Integer board_num = Integer.parseInt(board_num_str);
			
			int reportupdateok = boarddao.updateRecommendByBoardNum(board_num);
			if(reportupdateok > 0){
				model.addAttribute("reportok", "reportok");
			}else{

				request.setAttribute("reportok", "reportfalse");
			}
		}
		return "content/reportPro";
	}
}



