package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dao.FollowDAO;

@Controller
public class FollowFormController { 
	@Autowired
	private FollowDAO followDAO;
	public void setFollowDAO(FollowDAO followDAO) {
		this.followDAO = followDAO;
	}
	
	/**	팔로워 상세 페이지	*/
	@RequestMapping("/follow/follower.do")
	public ModelAndView followerForm(HttpServletRequest request, @RequestParam("id") String to_id){
		ModelAndView mav = new ModelAndView();
		/**	로그인 ID	*/
		String from_id = (String) request.getSession().getAttribute("id");	
		mav.addObject("logId", from_id);
		/**	프로필 페이지 ID	*/
		mav.addObject("profileId", to_id);
		/**	프로필페이지 ID의 팔로워 목록	*/
		List<String> from_id_list = null;
		from_id_list = followDAO.fromList(to_id);
		mav.addObject("fromList", from_id_list);
		System.out.println(to_id+"의 팔로워 목록 : "+from_id_list);
		if( from_id != null ) {	/**	로그인 아이디가 있다.	*/
			if(from_id.equals(to_id)){
				if(from_id_list.contains(to_id)){
					from_id_list.remove(from_id);
				}
			}
			List<String> to_id_list = new ArrayList<String>();
			to_id_list = followDAO.toList(from_id);	/**	나의 팔로잉 목록	*/
			System.out.println(from_id+"의 팔로잉 목록 : " + to_id_list);
			Map map = new HashMap();
			if( to_id_list != null ) {
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
			System.out.println("팔로워 상태값:"+map);
			mav.addObject("followCheck", map);
		}
		mav.setViewName("follow/followerForm");
		return mav;
	}
	
	/**	팔로잉 상세 페이지	*/
	@RequestMapping("/follow/follewing.do")
	public ModelAndView followingForm(HttpServletRequest request, @RequestParam("id") String to_id ){
		ModelAndView mav = new ModelAndView();
		/**	로그인 Id	*/
		String my_to_id = (String)request.getSession().getAttribute("id");
		mav.addObject("logId", my_to_id);
		/**	프로필 페이지 ID	*/
		mav.addObject("profileId", to_id);
		/**	Id의 팔로잉 목록 조회	*/
		List<String> to_id_list = followDAO.toList(to_id);	
		mav.addObject("toIdList", to_id_list);
		if(my_to_id!=null){
			if(my_to_id.equals(to_id)){
				if(to_id_list.contains(my_to_id)){
					to_id_list.remove(my_to_id);
				}
			}
			/**	나의 팔로잉 목록	*/
			List<String> my_to_id_list = null;
			my_to_id_list = followDAO.toList(my_to_id);	
			System.out.println(my_to_id+"의 팔로잉 목록 : " + to_id_list);
			/**	팔로우 상태값 저장	*/
			Map map = new HashMap();
			if(my_to_id_list!=null){
				/**	false 값으로 초기화	*/
				for(String following : my_to_id_list){
					for(String tofollowing : to_id_list){	
						map.put(tofollowing, false);
					}
				}
				/**	팔로잉목록과 비교하여 있을 경우만 true값으로 저장	*/
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
			mav.addObject("followCheck", map);
			System.out.println("팔로우 상태값 "+ map);
		}
		
		mav.setViewName("follow/followingForm");
		return mav;
	}
}