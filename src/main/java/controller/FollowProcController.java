package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import command.FollowCommand;
import command.NoticeCommand;
import dao.FollowDAO;
import dao.NoticeDAO;
import net.sf.json.JSONObject;

@Controller
public class FollowProcController {
	@Autowired
	private FollowDAO followDAO;
	@Autowired
	private NoticeDAO noticeDao;
	
	public void setFollowDAO(FollowDAO followDAO) { this.followDAO = followDAO; }
	public void setNoticeDao(NoticeDAO noticeDao) { this.noticeDao = noticeDao; }

	@ResponseBody
	@RequestMapping("/follow/follow.do")
	public String Follow(HttpServletRequest request, HttpServletResponse resp, String to_id, String follow, Model model){
		String from_id = (String)request.getSession().getAttribute("id");

		JSONObject jso = new JSONObject();
		
		FollowCommand followAdd = new FollowCommand();
		followAdd.setFrom_id(from_id);
		followAdd.setTo_id(to_id);
		
		if(from_id!=null) {
			if(follow.equals("follow")){
				FollowCommand followVo = new FollowCommand(from_id, to_id);
				int n = followDAO.followInsert(followVo);
				if(n>0) {
					NoticeCommand noticeCommand = new NoticeCommand();
					noticeCommand.setKind("follow");
					noticeCommand.setId(from_id);
					noticeCommand.setTargetid(to_id);
					List<NoticeCommand> noticeList = noticeDao.getListByMember(noticeCommand);
					if(noticeList.size() != 0) {
						noticeDao.removeByMember(noticeCommand);
					}
					noticeDao.insert2(noticeCommand);
					
					model.addAttribute("followCheck", false);
					System.out.println(from_id + "가" + to_id + "팔로우"); 
				} else {
					System.out.println("팔로우 실패");
				}
			}
			if(follow.equals("unfollow")){
				FollowCommand followVo = new FollowCommand(from_id, to_id);
				int n = followDAO.remove(followVo);
				if(n>0) {
					NoticeCommand noticeCommand = new NoticeCommand();
					noticeCommand.setKind("unfollow");
					noticeCommand.setId(from_id);
					noticeCommand.setTargetid(to_id);
					List<NoticeCommand> noticeList = noticeDao.getListByMember(noticeCommand);
					if(noticeList.size() != 0) {
						noticeDao.removeByMember(noticeCommand);
					}
					noticeDao.insert2(noticeCommand);

					model.addAttribute("followCheck", true);
					System.out.println(from_id + "가" + to_id + "언팔로우");
				} else {
					System.out.println("언팔로우 실패");
				}
			}

			//팔로워 숫자 저장
			int followerCount =followDAO.countfrom(to_id);
			jso.put("followerCount", followerCount);
		}
		jso.put("follow", follow);
		resp.setContentType("text/html;charset=utf-8");
		return jso.toString();
	}
	
	/**	팔로워 상세에서 팔로우 처리	*/
	@ResponseBody
	@RequestMapping("/follow/followerAdd.do")
	public String addFollower(HttpServletRequest request, HttpServletResponse reponse, String profileId, String add_id, String follow, Model model){
		JSONObject jso = new JSONObject();
		/**	나의 팔로잉 목록	*/
		String from_id = (String)request.getSession().getAttribute("id");	/**	로그인 Id	*/
		System.out.println("로그인 ID : " + from_id);
		System.out.println("추기 또는 삭제 ID  : " + add_id);
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(from_id!=null) {	/**	로그인 Id가 있다	*/
			if(follow.equals("follow")){ /**	팔로우와 같다면	*/
				FollowCommand followVo = new FollowCommand(from_id, add_id);	/**	formId - toId	*/
				int n = followDAO.followInsert(followVo); /**	팔로우 등록 처리	*/
				if(n>0) {	/**	성공	*/
					NoticeCommand noticeCommand = new NoticeCommand();
					noticeCommand.setKind("follow");
					noticeCommand.setId(from_id);
					noticeCommand.setTargetid(add_id);
					List<NoticeCommand> noticeList = noticeDao.getListByMember(noticeCommand);
					if(noticeList.size() != 0) {
						noticeDao.removeByMember(noticeCommand);
					}
					noticeDao.insert2(noticeCommand);
					map.put(add_id, true);
					model.addAttribute("followCheck", map);
					System.out.println(from_id + "가" + add_id + "팔로우"); 
				} else {	/**	실패	*/
					System.out.println("팔로우 실패");
				}
			}
			if(follow.equals("unfollow")){	/**	언팔로우와 같으면	*/
				FollowCommand followVo = new FollowCommand(from_id, add_id);
				int n = followDAO.remove(followVo);
				if(n>0) {
					NoticeCommand noticeCommand = new NoticeCommand();
					noticeCommand.setKind("unfollow");
					noticeCommand.setId(from_id);
					noticeCommand.setTargetid(add_id);
					List<NoticeCommand> noticeList = noticeDao.getListByMember(noticeCommand);
					if(noticeList.size() != 0) {
						noticeDao.removeByMember(noticeCommand);
					}
					noticeDao.insert2(noticeCommand);
					map.put(add_id, false);
					model.addAttribute("followCheck", map);
					System.out.println(from_id + "가" + add_id + "언팔로우");
				} else {
					System.out.println("언팔로우 실패");
				}
			}
		}
//		model.addAttribute("followCheck", map);
		System.out.println("팔로워 상태값:"+map);
		jso.put("id", add_id);
		jso.put("follow", follow);
		reponse.setContentType("text/html;charset=utf-8");
		return jso.toString();
	}
	
	/**	팔로잉 상세에서 팔로우 처리	*/
	@ResponseBody
	@RequestMapping("/follow/followingAdd.do")
	public String addFollowing(HttpServletRequest request, HttpServletResponse reponse,  String profileId, String add_id,String follow, Model model){
		JSONObject jso = new JSONObject();
		Map<String, Object> map = new HashMap<String, Object>();
		/**	로그인 아이디	*/
		String loginId = (String)request.getSession().getAttribute("id");
		System.out.println("로그인 ID : " + loginId);
		System.out.println("추기 또는 삭제 ID  : " + add_id);
		
		if(loginId!=null) {
			if(follow.equals("follow")){
				FollowCommand followVo = new FollowCommand(loginId, add_id);	/**	formId - toId	*/
				int n = followDAO.followInsert(followVo);
				if(n>0) {	
					NoticeCommand noticeCommand = new NoticeCommand();
					noticeCommand.setKind("follow");
					noticeCommand.setId(loginId);
					noticeCommand.setTargetid(add_id);
					List<NoticeCommand> noticeList = noticeDao.getListByMember(noticeCommand);
					if(noticeList.size() != 0) {
						noticeDao.removeByMember(noticeCommand);
					}
					noticeDao.insert2(noticeCommand);
					
					map.put(add_id, true);
					model.addAttribute("followCheck", map);
					System.out.println(loginId + "가" + add_id + "팔로우"); 
				} else {	
					System.out.println("팔로우 실패");
				}
			}
			if(follow.equals("unfollow")){	
				FollowCommand followVo = new FollowCommand(loginId, add_id);
				int n = followDAO.remove(followVo);
				if(n>0) {
					NoticeCommand noticeCommand = new NoticeCommand();
					noticeCommand.setKind("unfollow");
					noticeCommand.setId(loginId);
					noticeCommand.setTargetid(add_id);
					List<NoticeCommand> noticeList = noticeDao.getListByMember(noticeCommand);
					if(noticeList.size() != 0) {
						noticeDao.removeByMember(noticeCommand);
					}
					noticeDao.insert2(noticeCommand);
					
					map.put(add_id, false);
					model.addAttribute("followCheck", map);
					System.out.println(loginId + "가" + add_id + "언팔로우");
					if(loginId.equals(profileId)){
						List<String> to_id_list = followDAO.toList(profileId);
						to_id_list.remove(add_id);
						jso.put("toIdList", to_id_list);
						jso.put("myPofile", true);
					}
				} else {
					System.out.println("언팔로우 실패");
				}
			}
		}
		System.out.println("팔로워 상태값:"+map);
		jso.put("id", add_id);
		jso.put("follow", follow);
		reponse.setContentType("text/html;charset=utf-8");
		return jso.toString();
	}
}
