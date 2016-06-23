package controller;

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
public class FollowerController { 
	@Autowired
	private FollowDAO followDAO;
	public void setFollowDAO(FollowDAO followDAO) {
		this.followDAO = followDAO;
	}
	/**	팔로워 목록	*/
	@RequestMapping("/follow/follower.do")
	public ModelAndView followerForm(HttpServletRequest request, @RequestParam("id") String to_id ){
		ModelAndView mav = new ModelAndView();
		String from_id = (String) request.getSession().getAttribute("id");	/**	로그인 ID	*/
		/**	어떤 Id의 팔로워 목록	*/
		List<String> from_id_list = followDAO.fromList(to_id);
		if(from_id_list != null && from_id!=null){
			if(from_id_list.contains(from_id)){
				from_id_list.remove(from_id);
			}
		}
		mav.addObject("fromList", from_id_list);
		System.out.println(to_id+"의 팔로워 목록 : "+from_id_list);
		if( from_id != null ) {	/**	로그인 아이디가 있다.	*/
			List<String> to_id_list = followDAO.toList(from_id);	/**	나의 팔로잉 목록	*/
			System.out.println(from_id+"의 팔로잉 목록 : " + to_id_list);
			
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
			}
			System.out.println("팔로워 상태값:"+map);
			mav.addObject("followCheck", map);
		}
		mav.addObject("profileId", to_id);
		mav.setViewName("/follow/followerForm");
		return mav;
	}
}