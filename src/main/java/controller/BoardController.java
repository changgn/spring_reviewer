package controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import command.BoardCommand;
import command.CategoryCommand;
import command.CommentCommand;
import command.MemberRecommendDeleteCommand;
import command.NoticeCommand;
import command.PhotoCommand;
import command.ProfilePhotoCommand;
import command.RecommendCommand;
import command.ReportCommand;
import command.ScrepCommand;
import command.SecretCommand;
import dao.BoardDAO;
import dao.CategoryDAO;
import dao.CommentDAO;
import dao.FollowDAO;
import dao.MemberDAO;
import dao.NoticeDAO;
import dao.PhotoDAO;
import dao.ProfilePhotoDAO;
import dao.RecommendDAO;
import dao.ReportDAO;
import dao.ScrepDAO;
import dao.SecretDAO;
import net.sf.json.JSONObject;



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
	@Autowired
	private RecommendDAO recommendDao;
	@Autowired
	private SecretDAO secretDao;
	@Autowired
	private ScrepDAO ScrepDao;
	@Autowired
	private ReportDAO reportDAO;
	@Autowired
	private MemberDAO memberDAO;
	@Autowired
	private NoticeDAO noticeDao;
	@Autowired
	private FollowDAO followDao;
	@Autowired
	private ProfilePhotoDAO ProfilePhotoDao;

	public void setMemberDAO(MemberDAO memberDAO) { this.memberDAO = memberDAO; }
	public void setReportDAO(ReportDAO reportDAO) { this.reportDAO = reportDAO; }
	public void setCategorydao(CategoryDAO categorydao) { this.categorydao = categorydao; }
	public void setPhotodao(PhotoDAO photodao) { this.photodao = photodao; }
	public void setCommentdao(CommentDAO commentdao) { this.commentdao = commentdao; }
	public void setBoarddao(BoardDAO boarddao) { this.boarddao = boarddao; }
	public void setRecommendDao(RecommendDAO recommendDao) { this.recommendDao = recommendDao; }
	public void setSecretDao(SecretDAO secretDao) { this.secretDao = secretDao; }
	public void setScrepDao(ScrepDAO screpDao) { ScrepDao = screpDao; }
	public void setNoticeDao(NoticeDAO noticeDao) { this.noticeDao = noticeDao; }
	public void setFollowDAO(FollowDAO followDAO) { this.followDao = followDAO; }
	public void setProfilePhotoDao(ProfilePhotoDAO profilePhotoDao) { ProfilePhotoDao = profilePhotoDao; }

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
		int n = boarddao.insertBoard(command);
		int board_num = boarddao.getRecentBoardNumById(id).intValue();
		if(n>0) {
			List<String> followerList = followDao.fromList(id);
			for(String follower : followerList) {
				NoticeCommand noticeCommand = new NoticeCommand("content",id,follower,board_num);
				noticeDao.insert(noticeCommand);
			}
		}
	
		Iterator<String> filenames = mhsq.getFileNames();
		while (filenames.hasNext()) {
		
			MultipartFile file = mhsq.getFile(filenames.next());
			
			String genId = UUID.randomUUID().toString(); 
			String o_fileName = file.getOriginalFilename();
			String fileName = genId + "_" + o_fileName;
			String realPath =  savePath + "/" + fileName;
			
			PhotoCommand pcommand = new PhotoCommand(); 
			pcommand.setBoard_num(board_num);
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
		
		return "redirect:/content/contentForm.do?board_num=" + board_num;
	}

	
	
	@RequestMapping(value="/content/contentForm.do")
	public String selectcontent(HttpServletRequest request, String board_num, String comment, Model model){
		ProfilePhotoCommand profilePhoto = null;
		List<PhotoCommand> photoList = null;
		List<CommentCommand> commentList = null;
		BoardCommand board = null;

		String id = (String)request.getSession().getAttribute("id");
		String commentCount = "0";
		CategoryCommand category = null;
		
		board = boarddao.selectContent(Integer.parseInt(board_num));
		if(board != null) {
			profilePhoto = ProfilePhotoDao.getOneById(board.getId());
			photoList = photodao.getListByBoardNum(Integer.parseInt(board_num));
			commentList = commentdao.getListByBoardNum(Integer.parseInt(board_num));
			category = categorydao.getOne(board.getCategory_id());
			commentCount = commentdao.getCountByBoardNum(board.getBoard_num());
			if(commentCount==null)	commentCount="0";
			if(id != null ){
				RecommendCommand recommend = new RecommendCommand(id, Integer.parseInt(board_num));
				List<RecommendCommand> recommends = recommendDao.getRecommend(recommend);
				if(recommends.size() != 0){
					model.addAttribute("recommendFlag", "recommend");
				}else{
					model.addAttribute("recommendFlag", "nrecommend");
				}
				ScrepCommand screp = new ScrepCommand(id, Integer.parseInt(board_num));
				if(screp.getId() != null ){
					ScrepCommand screps = ScrepDao.getScrep(screp);
					if(screps != null){
						model.addAttribute("screpFlag", "screp");
					}else{
						model.addAttribute("screpFlag", "nscrep");
					}
				}
			}
		} else {
			model.addAttribute("error", "error");
		}

		model.addAttribute("board_num", board_num);
		model.addAttribute("board", board);
		model.addAttribute("photoList", photoList);
		model.addAttribute("profilePhoto", profilePhoto);
		model.addAttribute("category", category);
		model.addAttribute("comment", comment);
		model.addAttribute("commentList", commentList);
		model.addAttribute("commentCount", commentCount);
		return "content/contentForm";
		
	}
	
	@RequestMapping(value="/content/deleteContent.do")
	public String delete(HttpSession session, Model model, String board_num, @RequestParam("id") String writer){

		String id = (String)session.getAttribute("id");
		String login_status = (String)session.getAttribute("login_status");

		if(id.equals(writer) || login_status.equals("0")) { 
			BoardCommand bc = new BoardCommand();
			bc = boarddao.selectContent(Integer.parseInt(board_num));
			int recommend_num = 0 ;
			recommend_num = bc.getRecommend_num();

			MemberRecommendDeleteCommand mrdc = new MemberRecommendDeleteCommand();
			mrdc.setBoard_recommend_num(recommend_num);
			mrdc.setId(writer);
			memberDAO.updateDecreaseRecommendNumByDeleteBoard(mrdc);
			
			reportDAO.deleteReport(Integer.parseInt(board_num));
			boarddao.deleteContent(Integer.parseInt(board_num));
		} else {
			model.addAttribute("errorId", "errorId");
		}
		return "content/deleteContent";
	}
	
	@RequestMapping(value="/content/reportPro.do")
	public String report(HttpServletRequest request, Model model){

		String board_num_str = request.getParameter("board_num");
		String id = (String) request.getSession().getAttribute("id");
		
		if(board_num_str != null){
			Integer board_num = Integer.parseInt(board_num_str);
			
			int reportupdateok = boarddao.updateReportNumByBoardNum(board_num);
			ReportCommand rc = new ReportCommand(id, board_num);
			reportDAO.insertReport(rc);
			if(reportupdateok > 0){
				BoardCommand board = boarddao.selectContent(board_num);
				NoticeCommand noticeCommand = new NoticeCommand("report", id, board.getId(), board_num);
				List<NoticeCommand> noticeList = noticeDao.getListByBoard(noticeCommand);
				if(noticeList.size() != 0) {
					noticeDao.removeByBoard(noticeCommand);
				}
				noticeDao.insert(noticeCommand);
				
				model.addAttribute("reportok", "reportok");
			}else{

				request.setAttribute("reportok", "reportfalse");
			}
		}
		return "content/reportPro";
	}
	
	@ResponseBody
	@RequestMapping("/content/secret.do")
	public String secret(HttpServletRequest request, HttpServletResponse resp, int board_num){
		
		String id = (String)request.getSession().getAttribute("id");
		
		JSONObject jso = new JSONObject();
		
		SecretCommand command = new SecretCommand(id, board_num);
		secretDao.insert(command);
		
		jso.put("secret", command);
		resp.setContentType("text/html;charset=utf-8");
		return jso.toString();
		
	}
	
	
	
}



