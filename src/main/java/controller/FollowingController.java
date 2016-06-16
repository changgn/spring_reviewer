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

import command.FollowCommand;
import dao.FollowDAO;

@Controller
public class FollowingController {
	@Autowired
	private FollowDAO followDAO;
	public void setFollowDAO(FollowDAO followDAO) {
		this.followDAO = followDAO;
	}
	/**	팔로잉, '내'가 다른사람을 팔로우	*/
	@RequestMapping("/follow/follewing.do")
	public ModelAndView followingForm(HttpServletRequest request, @RequestParam("id") String to_id ){
		ModelAndView mav = new ModelAndView();
		String my_to_id = (String)request.getSession().getAttribute("id");	/**	로그인 아이디	*/
		List<String> to_id_list = followDAO.toList(to_id);	/**	목록을 보려는 Id의 팔로잉 목록 조회, 저장	*/
		mav.addObject("toIdList", to_id_list);
		System.out.println(to_id+"의 팔로잉 목록 : "+to_id_list);
		if(my_to_id!=null){
			List<String> my_to_id_list = followDAO.toList(my_to_id);	/**	로그인한 Id. 즉, '나'의 팔로잉 목록	*/
			boolean followCheck = false;	/**	팔로우 상태 값	*/
			Map map = new HashMap();
			List<Map> m = new ArrayList<Map>();
			if(my_to_id_list!=null){	/**	'내'가 팔로우한 목록이 있다면	*/
				for(String following : my_to_id_list){	/**	내 팔로우 목록 리스트를 하나씩 꺼내어	*/
					for(String tofollowing : to_id_list){
						if(tofollowing.equals(following)){	/**	해당 아이디와 비교 같으면	*/
							map.put(tofollowing, true);
						}else{
							if(tofollowing.equals(my_to_id)){
								map.put(tofollowing, true);
							}
							map.put(tofollowing, false);
						}
					}
				}
			}
			mav.addObject("followCheck", map);
			System.out.println("각 리스트의 boolean "+ map);
		}
		mav.addObject("id", to_id);
		mav.setViewName("follow/followingForm");
		return mav;
	}
}
