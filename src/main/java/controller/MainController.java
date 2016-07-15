package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import command.BoardCommand;
import command.CategoryCommand;
import command.PhotoCommand;
import command.ProfilePhotoCommand;
import command.RecommendCommand;
import command.ScrepCommand;
import net.sf.json.JSONObject;


@Controller
public class MainController extends BaseController {
	
	@RequestMapping("/main/main.do")
	public String main(HttpServletRequest request, HttpServletResponse resp, Model model, String sort){
		
		String id = (String)request.getSession().getAttribute("id"); 
		String login_status = (String)request.getSession().getAttribute("login_status");
		
		int boardCount = 0;
		List<BoardCommand> boardList = null;
		List<HashMap<String, Object>> allBoardList = new ArrayList<HashMap<String, Object>>();
		List<String> categoryIdList = null;
		
		if(login_status==null){
			login_status = "2";
			request.getSession().setAttribute("login_status", login_status);
		}
		if(sort==null)	sort="all";
		
		Cookie[] cookies = request.getCookies();
		if(cookies!=null) {
			for(int i=0; i<cookies.length; i++) {
				if(cookies[i].getName().equals("autoLogin")) {
					request.getSession().setAttribute("id", cookies[i].getValue());
					request.getSession().setAttribute("login_status", "1");
					id = cookies[i].getValue();
					login_status = "1";
					if(cookies[i].getValue().equals("admin")) {
						request.getSession().setAttribute("login_status", "0");
						login_status = "0";
					}
				}
			}
		}
		
		if(login_status.equals("2")){
			boardList = mainDao.getPageList();
			
		} else {
			request.getSession().setAttribute("noReadNoticeCount", noticeDao.getNoReadCountById(id));

			List<Integer> secretBoardNumList = secretDao.getListById(id);
			
			if(sort.equals("all")) {
				if(secretBoardNumList.size() != 0){
					boardList = mainDao.getPageListByExBoardNum(secretBoardNumList);
				}else {
					boardList = mainDao.getPageList();
				}
			} else if(sort.equals("category")) {
				categoryIdList = memberCategoryDao.getCategoryIdById(id);
				if(categoryIdList.size() != 0){
					if(secretBoardNumList.size() != 0){
						boardList = mainDao.getPageListByCategoryIdExBoardNum(categoryIdList, secretBoardNumList);
					}else {
						boardList = mainDao.getPageListByCategoryId(categoryIdList);
					}
				} else { 
					model.addAttribute("sortError", "noCategory");
					if(secretBoardNumList.size() != 0){
						boardList = mainDao.getPageListByExBoardNum(secretBoardNumList);
					} else {
						boardList = mainDao.getPageList();
					}
				}
			} else if(sort.equals("follow")) {
				List<String> idList = followDao.toList(id);
				if(idList.size() != 0){
					if(secretBoardNumList.size() != 0){
						boardList = mainDao.getPageListByIdListExBoardNum(idList, secretBoardNumList);
					}else {
						boardList = mainDao.getPageListByIdList(idList);}
				} else {
					model.addAttribute("sortError", "noFollow");
					if(secretBoardNumList.size() != 0){
						boardList = mainDao.getPageListByExBoardNum(secretBoardNumList);
					} else {
						boardList = mainDao.getPageList();
					}
				}
				
			} else if(sort.equals("category_follow")) {
				categoryIdList = memberCategoryDao.getCategoryIdById(id);
				List<String> idList = followDao.toList(id);
				if(categoryIdList.size() != 0){
					if(idList.size() != 0){
						if(secretBoardNumList.size() != 0){
							boardList = mainDao.getPageListByIdCategoryIdListExBoardNum(idList, categoryIdList, secretBoardNumList);
						}else {
							boardList = mainDao.getPageListByIdCategoryIdList(idList, categoryIdList);
						}
					} else {
						if(secretBoardNumList.size() != 0){
							boardList = mainDao.getPageListByCategoryIdExBoardNum(categoryIdList, secretBoardNumList);
						}else {
							boardList = mainDao.getPageListByCategoryId(categoryIdList);
						}
					}
				} else {
					if(idList.size() != 0){
						if(secretBoardNumList.size() != 0){
							boardList = mainDao.getPageListByIdListExBoardNum(idList, secretBoardNumList);
						}else {
							boardList = mainDao.getPageListByIdList(idList);
						}
					} else {
						model.addAttribute("sortError", "noCategoryFollow");
						if(secretBoardNumList.size() != 0){
							boardList = mainDao.getPageListByExBoardNum(secretBoardNumList);
						} else {
							boardList = mainDao.getPageList();
						}
					}
				}
			}
		}
		if(boardList!=null)	{
			boardCount = boardList.size();
			for(BoardCommand vo : boardList) {
				HashMap<String, Object> boardMap = new HashMap<String, Object>();
				PhotoCommand photo = photoDao.getOneByBoardNum(vo.getBoard_num());
				ProfilePhotoCommand profilePhoto = profilePhotoDao.getOneById(vo.getId());
				CategoryCommand category = categoryDao.getOne(vo.getCategory_id());
				String commentCount = commentDao.getCountByBoardNum(vo.getBoard_num());
		
				if(commentCount==null)	commentCount="0";
				boolean contentFlag = false;
				String[] contentSub = vo.getContent().split("\n");
				if(contentSub.length > 3) {
					contentFlag = true;
					vo.setContent(contentSub[0] + contentSub[1] + contentSub[2]);
				}
				
				RecommendCommand recommend = new RecommendCommand(id, vo.getBoard_num());
				if(recommend.getId() != null ){
					List<RecommendCommand> recommends = recommendDao.getRecommend(recommend);
					if(recommends.size() != 0){
						boardMap.put("recommendFlag", "recommend");
					}else{
						boardMap.put("recommendFlag", "nrecommend");
					}
				}
				
				ScrepCommand screp = new ScrepCommand(id, vo.getBoard_num());
				if(screp.getId() != null ){
					ScrepCommand screps = screpDao.getScrep(screp);
					if(screps != null){
						boardMap.put("screpFlag", "screp");
					}else{
						boardMap.put("screpFlag", "nscrep");
					}
				}
					
				boardMap.put("board", vo);
				boardMap.put("photo", photo);
				boardMap.put("profilePhoto", profilePhoto);
				boardMap.put("category", category);
				boardMap.put("commentCount", commentCount);
				boardMap.put("contentFlag", contentFlag);
				allBoardList.add(boardMap);
			}
		}
		model.addAttribute("boardCount", boardCount);
		model.addAttribute("sort", sort);
		model.addAttribute("allBoardList", allBoardList);
		return "main/main";
	}
	
	@ResponseBody
	@RequestMapping("/main/mainAjax.do")
	public String mainAjax(HttpServletRequest request, HttpServletResponse resp, String sort, int lastBoard_num){
		
		String id = (String)request.getSession().getAttribute("id"); 
		String login_status = (String)request.getSession().getAttribute("login_status");
		

		JSONObject jso = new JSONObject();

		List<BoardCommand> boardList = null;
		List<HashMap<String, Object>> allBoardList = new ArrayList<HashMap<String, Object>>();
		List<String> categoryIdList = null;
	
		
		if(login_status==null){
			login_status = "2";
			request.getSession().setAttribute("login_status", login_status);
		}
		
		
		if(login_status.equals("2")){
			boardList = mainDao.getMorePageList(lastBoard_num);
		}else {
			List<Integer> secretBoardNumList = secretDao.getListById(id);
			
			if(sort.equals("all")) {
				if(secretBoardNumList.size() != 0){
					boardList = mainDao.getMorePageListByExBoardNum(secretBoardNumList, lastBoard_num);
				} else {
					boardList = mainDao.getMorePageList(lastBoard_num);
				}
			} else if(sort.equals("category")) {
				categoryIdList = memberCategoryDao.getCategoryIdById(id);
				if(categoryIdList.size() != 0){
					if(secretBoardNumList.size() != 0){
						boardList = mainDao.getMorePageListByCategoryIdExBoardNum(categoryIdList, secretBoardNumList, lastBoard_num);
					} else {
						boardList = mainDao.getMorePageListByCategoryId(categoryIdList, lastBoard_num);
					}
				} else { 
					if(secretBoardNumList.size() != 0){
						boardList = mainDao.getMorePageListByExBoardNum(secretBoardNumList, lastBoard_num);
					} else {
						boardList = mainDao.getMorePageList(lastBoard_num);
					}
				}
			} else if(sort.equals("follow")) {
				List<String> idList = followDao.toList(id);
				if(idList.size() != 0){
					if(secretBoardNumList.size() != 0){
						boardList = mainDao.getMorePageListByIdListExBoardNum(idList, secretBoardNumList, lastBoard_num);
					} else {
						boardList = mainDao.getMorePageListByIdList(idList, lastBoard_num);
					}
				} else {
					if(secretBoardNumList.size() != 0){
						boardList = mainDao.getMorePageListByExBoardNum(secretBoardNumList, lastBoard_num);
					} else {
						boardList = mainDao.getMorePageList(lastBoard_num);
					}
				}
				
			} else if(sort.equals("category_follow")) {
				categoryIdList = memberCategoryDao.getCategoryIdById(id);
				List<String> idList = followDao.toList(id);
				if(categoryIdList.size() != 0){
					if(idList.size() != 0){
						if(secretBoardNumList.size() != 0){
							boardList = mainDao.getMorePageListByIdCategoryIdListExBoardNum(idList, categoryIdList, secretBoardNumList, lastBoard_num);
						}else {
							boardList = mainDao.getMorePageListByIdCategoryIdList(idList, categoryIdList, lastBoard_num);
						}
					} else {
						if(secretBoardNumList.size() != 0){
							boardList = mainDao.getMorePageListByCategoryIdExBoardNum(categoryIdList, secretBoardNumList, lastBoard_num);
						}else {
							boardList = mainDao.getMorePageListByCategoryId(categoryIdList, lastBoard_num);
						}
					}
				} else {
					if(idList.size() != 0){
						if(secretBoardNumList.size() != 0){
							boardList = mainDao.getMorePageListByIdListExBoardNum(idList, secretBoardNumList, lastBoard_num);
						}else {
							boardList = mainDao.getMorePageListByIdList(idList, lastBoard_num);
						}
					} else {
						if(secretBoardNumList.size() != 0){
							boardList = mainDao.getMorePageListByExBoardNum(secretBoardNumList, lastBoard_num);
						} else {
							boardList = mainDao.getMorePageList(lastBoard_num);
						}
					}
				}
			}
		}
		if(boardList!=null)	{
			for(BoardCommand vo : boardList) {
				HashMap<String, Object> boardMap = new HashMap<String, Object>();
				PhotoCommand photo = photoDao.getOneByBoardNum(vo.getBoard_num());
				CategoryCommand category = categoryDao.getOne(vo.getCategory_id());
				ProfilePhotoCommand profilePhoto = profilePhotoDao.getOneById(vo.getId());
				String commentCount = commentDao.getCountByBoardNum(vo.getBoard_num());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				String date = sdf.format(vo.getWrite_date());
				
				if(commentCount==null)	commentCount="0";
				boolean contentFlag = false;
				String[] contentSub = vo.getContent().split("\n");
				if(contentSub.length > 3) {
					contentFlag = true;
					vo.setContent(contentSub[0] + contentSub[1] + contentSub[2]);
				}
				
				
				RecommendCommand recommend = new RecommendCommand(id, vo.getBoard_num());
				if(recommend.getId() != null ){
					List<RecommendCommand> recommends = recommendDao.getRecommend(recommend);
					if(recommends.size() != 0){
						boardMap.put("recommendFlag", "recommend");
					}else{
						boardMap.put("recommendFlag", "nrecommend");
					}
				}
				
				ScrepCommand screp = new ScrepCommand(id, vo.getBoard_num());
				if(recommend.getId() != null ){
					ScrepCommand screps = screpDao.getScrep(screp);
					if(screps != null){
						boardMap.put("screpFlag", "screp");
					}else{
						boardMap.put("screpFlag", "nscrep");
					}
				}
				
				
				boardMap.put("board", vo);
				boardMap.put("photo", photo);
				boardMap.put("profilePhoto", profilePhoto);
				boardMap.put("category", category);
				boardMap.put("commentCount", commentCount);
				boardMap.put("contentFlag", contentFlag);
				boardMap.put("date", date);
				allBoardList.add(boardMap);
			}
		}
		jso.put("allBoardList", allBoardList);

		resp.setContentType("text/html;charset=utf-8");
		return jso.toString();
	}

}
