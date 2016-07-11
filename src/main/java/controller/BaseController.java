package controller;

import org.springframework.beans.factory.annotation.Autowired;

import dao.BoardDAO;
import dao.CategoryDAO;
import dao.CommentDAO;
import dao.FollowDAO;
import dao.MainDAO;
import dao.MemberCategoryDAO;
import dao.MemberDAO;
import dao.NoticeDAO;
import dao.PhotoDAO;
import dao.ProfilePhotoDAO;
import dao.RecommendDAO;
import dao.ReportDAO;
import dao.ScrepDAO;
import dao.SecretDAO;

public class BaseController {
	
	@Autowired
	BoardDAO boardDao; 
	@Autowired
	CategoryDAO categoryDao;
	@Autowired
	CommentDAO commentDao;	
	@Autowired
	FollowDAO followDao;
	@Autowired
	MainDAO mainDao;
	@Autowired
	MemberCategoryDAO memberCategoryDao;
	@Autowired
	MemberDAO memberDao;
	@Autowired
	NoticeDAO noticeDao;
	@Autowired
	PhotoDAO photoDao;
	@Autowired
	ProfilePhotoDAO ProfilePhotoDao;
	@Autowired
	RecommendDAO recommendDao;
	@Autowired
	ReportDAO reportDao;
	@Autowired
	ScrepDAO screpDao;
	@Autowired
	SecretDAO secretDao;
	
	public void setBoarddao(BoardDAO boardDao) { this.boardDao = boardDao; }	
	public void setCategorydao(CategoryDAO categoryDao) { this.categoryDao = categoryDao; }
	public void setCommentdao(CommentDAO commentDao) { this.commentDao = commentDao; }
	public void setFollowDAO(FollowDAO followDao) { this.followDao = followDao; }
	public void setMainDao(MainDAO mainDao) { this.mainDao = mainDao; }
	public void setMemberCategoryDao(MemberCategoryDAO memberCategoryDao) { this.memberCategoryDao = memberCategoryDao; }
	public void setMemberDao(MemberDAO memberDao) { this.memberDao = memberDao; }
	public void setNoticeDao(NoticeDAO noticeDao) { this.noticeDao = noticeDao;	}
	public void setPhotodao(PhotoDAO photoDao) { this.photoDao = photoDao; }
	public void setProfilePhotoDao(ProfilePhotoDAO profilePhotoDao) { this.ProfilePhotoDao = profilePhotoDao; }
	public void setRecommendDao(RecommendDAO recommendDao) { this.recommendDao = recommendDao; }
	public void setReportDAO(ReportDAO reportDao) { this.reportDao = reportDao; }
	public void setScrepDao(ScrepDAO screpDao) { this.screpDao = screpDao; }
	public void setSecretDao(SecretDAO secretDao) { this.secretDao = secretDao; }
	
}
