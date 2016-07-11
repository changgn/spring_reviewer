package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import command.ProfilePhotoCommand;

@Controller
public class FollowFormController extends BaseController { 

	/**	팔로워 상세 페이지	*/
	@RequestMapping("/follow/follower.do")
	public ModelAndView followerForm(HttpServletRequest request, @RequestParam("id") String to_id){
		ModelAndView mav = new ModelAndView();
		/**	로그인 ID	*/
		String from_id = (String) request.getSession().getAttribute("id");	
		mav.addObject("logId", from_id);
		/**	프로필 페이지 ID	*/
		mav.addObject("profileId", to_id);
		/**	프로필 페이지 프로필 사진	*/
		ProfilePhotoCommand profile_photo = profilePhotoDao.getOneById(to_id);
		mav.addObject("profileIdPhoto", profile_photo);
		/**	프로필페이지 ID의 팔로워 목록	*/
		List<String> from_id_list = null;
		from_id_list = followDao.fromList(to_id);
		mav.addObject("fromList", from_id_list);
		/**	팔로워 리스트 프로필 사진	*/
		ProfilePhotoCommand list_profile_photo = new ProfilePhotoCommand();
		Map list_profile_photo_map = new HashMap();
		for(String id : from_id_list){
			list_profile_photo = profilePhotoDao.getOneById(id);
			list_profile_photo_map.put(id, list_profile_photo);
		}
		mav.addObject("list_profile_photo", list_profile_photo_map);
		if( from_id != null ) {	/**	로그인 아이디가 있다.	*/
			if(from_id.equals(to_id)){ /**	내 프로필 페이지	*/
				if(from_id_list.contains(to_id)){
					from_id_list.remove(from_id);
				}
			}
			List<String> to_id_list = new ArrayList<String>();
			to_id_list = followDao.toList(from_id);	/**	나의 팔로잉 목록	*/
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
		/**	프로필 페이지 프로필 사진	*/
		ProfilePhotoCommand profile_photo = profilePhotoDao.getOneById(to_id);
		mav.addObject("profileIdPhoto", profile_photo);
		/**	Id의 팔로잉 목록 조회	*/
		List<String> to_id_list = followDao.toList(to_id);	
		mav.addObject("toIdList", to_id_list);
		/**	팔로워 리스트 프로필 사진	*/
		ProfilePhotoCommand list_profile_photo = new ProfilePhotoCommand();
		Map list_profile_photo_map = new HashMap();
		for(String id : to_id_list){
			list_profile_photo = profilePhotoDao.getOneById(id);
			list_profile_photo_map.put(id, list_profile_photo);
		}
		mav.addObject("list_profile_photo", list_profile_photo_map);
		
		if(my_to_id!=null){
			if(my_to_id.equals(to_id)){
				if(to_id_list.contains(my_to_id)){
					to_id_list.remove(my_to_id);
				}
			}
			/**	나의 팔로잉 목록	*/
			List<String> my_to_id_list = null;
			my_to_id_list = followDao.toList(my_to_id);	
			/**	팔로우 상태값 저장	*/
			Map map = new HashMap();
			if(my_to_id_list!=null){
				/**	false 값으로 초기화	*/
				for(String following : my_to_id_list){
					for(String tofollowing : to_id_list){	
						map.put(tofollowing, true);
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
		}
		
		mav.setViewName("follow/followingForm");
		return mav;
	}
}