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

import command.FollowCommand;
import dao.FollowDAO;

@Controller
public class FollowerController { 
	@Autowired
	private FollowDAO followDAO;
	public void setFollowDAO(FollowDAO followDAO) {
		this.followDAO = followDAO;
	}
	/** 팔로워, 다른사람이 '나'를 팔로우 */
	@RequestMapping("/follow/follower.do")
	public ModelAndView followerForm(HttpServletRequest request, @RequestParam("id") String to_id ){
		ModelAndView mav = new ModelAndView();
//		String from_id = (String) request.getSession().getAttribute("id");	/**	로그인 아이디	*/
		String from_id = "val1";
		 /**	팔로워 정보 DB에서 목록 조회	*/
		List<FollowCommand> from_id_list = followDAO.fromList(to_id);
		mav.addObject("fromList", from_id_list);
		if( from_id != null ) {	/**	로그인 상태일 경우	*/
			List<FollowCommand> to_id_list = followDAO.toList(from_id);	/**	내가 팔로우한 목록 조회, 나의 팔로잉 목록	*/
			boolean followCheck = false;
			Map map = new HashMap();
			if( to_id_list != null ) {	/**	'내'가 팔로우한 목록이 있다면	*/
				for( FollowCommand following : to_id_list ) {	/**	내 팔로우 목록 리스트를 하나씩 꺼내어	*/
					for(FollowCommand follower : from_id_list){
						if(following.equals(follower)){
							followCheck = true;
							break;
						}
						break;
					}
				}
			}
			mav.addObject("followCheck", followCheck);
		}
		mav.addObject("id", to_id);
		mav.setViewName("follow/followerForm");
		return mav;
	}
}