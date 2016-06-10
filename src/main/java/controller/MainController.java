package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dao.BoardDAO;
import dao.CategoryDAO;
import dao.CommentDAO;
import dao.PhotoDAO;


@Controller
public class MainController {
	@Autowired
	private BoardDAO boardDao;
	@Autowired
	private PhotoDAO photoDao;
	@Autowired
	private CommentDAO commentDao;
	@Autowired
	private CategoryDAO categoryDao;
	
	
	public void setBoardDao(BoardDAO boardDao) {
		this.boardDao = boardDao;
	}

	public void setPhotoDao(PhotoDAO photoDao) {
		this.photoDao = photoDao;
	}

	public void setCommentDao(CommentDAO commentDao) {
		this.commentDao = commentDao;
	}

	public void setCategoryDao(CategoryDAO categoryDao) {
		this.categoryDao = categoryDao;
	}

	@RequestMapping("/main/main.do")
	public String main(HttpServletRequest request, HttpServletResponse response){

		return "main/main";
	}
}
