


package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dao.BoardDAO;
import dao.CategoryDAO;
import dao.CommentDAO;
import dao.FollowDAO;
import dao.MemberDAO;
import dao.MemberCategoryDAO;
import dao.PhotoDAO;
import dao.ScrepDAO;
import command.BoardCommand;
import command.CategoryCommand;
import command.FollowCommand;
import command.MemberCategoryCommand;
import command.MemberCommand;
import command.PhotoCommand;
import command.ScrepCommand;
@Controller
public class MyProfileContoroller {
	
	@Autowired
	private BoardDAO BoardDao;
	@Autowired
	private CategoryDAO categoryDao;
	@Autowired
	private CommentDAO commentDao;
	@Autowired
	private FollowDAO followDao;
	@Autowired
	private MemberCategoryDAO MemberCategoryDao;
	@Autowired
	private PhotoDAO PhotoDao;
	@Autowired
	private ScrepDAO ScrepDao;
	public void setBoardDao(BoardDAO boardDao) {
		BoardDao = boardDao;
	}
	public void setCategoryDao(CategoryDAO categoryDao) {
		this.categoryDao = categoryDao;
	}
	public void setCommentDao(CommentDAO commentDao) {
		this.commentDao = commentDao;
	}
	public void setFollowDao(FollowDAO followDao) {
		this.followDao = followDao;
	}
	public void setMemberCategoryDao(MemberCategoryDAO memberCategoryDao) {
		MemberCategoryDao = memberCategoryDao;
	}
	public void setPhotoDao(PhotoDAO photoDao) {
		PhotoDao = photoDao;
	}
	public void setScrepDao(ScrepDAO screpDao) {
		ScrepDao = screpDao;
	}
	
	@RequestMapping(value="/profile/myProfile.do",method=RequestMethod.GET)
	public String myProfileform(){
		
		return "/profile/myProfile";
	}
}
