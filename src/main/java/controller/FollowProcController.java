package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.util.SystemOutLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import command.FollowCommand;
import dao.FollowDAO;
import net.sf.json.JSONObject;

@Controller
public class FollowProcController {
	@Autowired
	private FollowDAO followDAO;
	public void setFollowDAO(FollowDAO followDAO) {
		this.followDAO = followDAO;
	}

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
	public String addFollower(HttpServletRequest request, HttpServletResponse reponse, String profileId,  String add_id, String follow, Model model){
		JSONObject jso = new JSONObject();
		/**	나의 팔로잉 목록	*/
		String from_id = (String)request.getSession().getAttribute("id");	/**	로그인 Id	*/
		System.out.println("로그인 ID : " + from_id);
		System.out.println("추기 또는 삭제 ID  : " + add_id);
		
		if(from_id!=null) {	/**	로그인 Id가 있다	*/
			if(follow.equals("follow")){ /**	팔로우와 같다면	*/
				FollowCommand followVo = new FollowCommand(from_id, add_id);	/**	formId - toId	*/
				int n = followDAO.followInsert(followVo); /**	팔로우 등록 처리	*/
				if(n>0) {	/**	성공	*/
					System.out.println(from_id + "가" + add_id + "팔로우"); 
					
				} else {	/**	실패	*/
					System.out.println("팔로우 실패");
				}
			}
			if(follow.equals("unfollow")){	/**	언팔로우와 같으면	*/
				FollowCommand followVo = new FollowCommand(from_id, add_id);
				int n = followDAO.remove(followVo);
				if(n>0) {
					System.out.println(from_id + "가" + add_id + "언팔로우");
				} else {
					System.out.println("언팔로우 실패");
				}
			}
		}
		
		/**	어떤 Id의 팔로워 목록	*/
		List<String> from_id_list = followDAO.fromList(profileId);
		System.out.println(profileId+"의 팔로워 목록 : "+from_id_list);
		if( from_id != null ) {	/**	로그인 아이디가 있다.	*/
			List<String> to_id_list = followDAO.toList(from_id);	/**	나의 팔로잉 목록	*/
			System.out.println(from_id+"의 팔로잉 목록 : " + to_id_list);
			if(from_id_list != null && from_id!=null){
				if(from_id_list.contains(from_id)){
					from_id_list.remove(from_id);
				}
			}
			Map map = new HashMap();
			if( to_id_list != null ) {	/**	팔로잉 목록이 있다	*/
				/**	false 값으로 초기화	*/
				for( String follower : from_id_list ) {	
					for(String following : to_id_list){	
							map.put(follower, false);
					}
				}
				/**	팔로잉목록과 비교하여 있을 경우만 true값으로 저장	*/
				for( String follower : from_id_list ) {	
					for(String following : to_id_list){	
						if(following.equals(follower)){
							map.put(follower, true);	
						}
					}
				}
			}else{
				for(String follower : from_id_list){
					map.put(follower, false);
				}
			}
			model.addAttribute("followCheck", map);
			System.out.println("팔로워 상태값:"+map);
		}
		jso.put("follow", follow);
		reponse.setContentType("text/html;charset=utf-8");
		return jso.toString();
	}
	
	/**	팔로잉 상세에서 팔로우 처리	*/
	@RequestMapping("/follow/followingAdd.do")
	public String addFollowing(HttpServletRequest request, HttpServletResponse reponse,  String profileId, String add_id,String follow, Model model){
		JSONObject jso = new JSONObject();
		/**	로그인 아이디	*/
		String loginId = (String)request.getSession().getAttribute("id");
		
		System.out.println("팔로우 하는, 팔로우 당하는 : " + loginId + add_id);
		
		if(loginId!=null) {
			if(follow.equals("follow")){
				FollowCommand followVo = new FollowCommand(loginId, add_id);	/**	formId - toId	*/
				int n = followDAO.followInsert(followVo);
				if(n>0) {	
					System.out.println(loginId + "가" + add_id + "팔로우"); 
				} else {	
					System.out.println("팔로우 실패");
				}
			}
			if(follow.equals("unfollow")){	
				FollowCommand followVo = new FollowCommand(loginId, add_id);
				int n = followDAO.remove(followVo);
				if(n>0) {
					System.out.println(loginId + "가" + add_id + "를 언팔로우");
				} else {
					System.out.println("언팔로우 실패");
				}
			}
		}
		List<String> to_id_list = followDAO.toList(profileId);	/**	Id의 팔로잉 목록 조회	*/
		System.out.println(profileId+"의 팔로잉 목록 : "+to_id_list);
		if(loginId!=null){
			List<String> my_to_id_list = followDAO.toList(loginId);	/**	나의 팔로잉 목록	*/
			System.out.println(loginId+"의 팔로잉 목록 : " + to_id_list);
			
			if(to_id_list != null && loginId!=null){
				if(to_id_list.contains(loginId)){
					to_id_list.remove(loginId);
				}
			}
			
			Map map = new HashMap();
			if(my_to_id_list!=null){	/**	나의 팔로잉목록이 있다면	*/
				/**	false 값으로 초기화	*/
				for(String following : my_to_id_list){
					for(String tofollowing : to_id_list){	
						map.put(tofollowing, false);
					}
				}
				/**	팔로잉목록과 비교하여 있을 경우만 true값으로 전환	*/
				for(String following : my_to_id_list){
					for(String tofollowing : to_id_list){
						if(tofollowing.equals(following)){
							map.put(tofollowing, true);	
						}
					}
				}
			}else{
				for(String tofollowing : to_id_list){
					map.put(tofollowing, false);
				}
			}
			System.out.println("팔로우 상태값 "+ map);
		}
		jso.put("follow", follow);
		reponse.setContentType("text/html;charset=utf-8");
		return jso.toString();
	}
}
