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
public class FollowingController {
	@Autowired
	private FollowDAO followDAO;
	public void setFollowDAO(FollowDAO followDAO) {
		this.followDAO = followDAO;
	}
	/**	팔로잉 목록	*/
	@RequestMapping("/follow/follewing.do")
	public ModelAndView followingForm(HttpServletRequest request, @RequestParam("id") String to_id ){
		ModelAndView mav = new ModelAndView();
		String my_to_id = (String)request.getSession().getAttribute("id");	/**	로그인 Id	*/
		List<String> to_id_list = followDAO.toList(to_id);	/**	Id의 팔로잉 목록 조회	*/
		mav.addObject("toIdList", to_id_list);
		System.out.println(to_id+"의 팔로잉 목록 : "+to_id_list);
		if(my_to_id!=null){
			List<String> my_to_id_list = followDAO.toList(my_to_id);	/**	나의 팔로잉 목록	*/
			System.out.println(my_to_id+"의 팔로잉 목록 : " + to_id_list);
			
			if(to_id_list != null && my_to_id!=null){
				if(to_id_list.contains(my_to_id)){
					to_id_list.remove(my_to_id);
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
				/**	팔로잉목록과 비교하여 있을 경우만 true값으로 저장	*/
				for(String following : my_to_id_list){
					for(String tofollowing : to_id_list){
						if(tofollowing.equals(following)){
							map.put(tofollowing, true);	
						}
					}
				}
			}
			mav.addObject("followCheck", map);
			System.out.println("팔로우 상태값 "+ map);
		}
		mav.addObject("profileId", to_id);
		mav.setViewName("follow/followingForm");
		return mav;
	}
}
