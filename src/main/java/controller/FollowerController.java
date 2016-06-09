package controller;

import java.util.List;

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
	@RequestMapping("follower.do")
	public ModelAndView followerForm(HttpServletRequest request, @RequestParam("id") String to_id ){
		ModelAndView mav = new ModelAndView();
		String from_id = (String) request.getSession().getAttribute("id");	/**	로그인 아이디	*/
		 /**	팔로워 정보 DB에서 목록 조회	*/
		List<FollowCommand> from_id_list = followDAO.fromList(to_id);
		mav.addObject("fromList", from_id_list);
		if( from_id != null ) {	/**	로그인 상태일 경우	*/
			List<FollowCommand> to_id_list = followDAO.toList(from_id);	/**	내가 팔로우한 목록 조회, 나의 팔로잉 목록	*/
			boolean followCheck = false;
			if( to_id_list != null ) {	/**	'내'가 팔로우한 목록이 있다면	*/
				for( FollowCommand following : to_id_list ) {	/**	내 팔로우 목록 리스트를 하나씩 꺼내어	*/
					if( following.equals(to_id) ) {	/**	해당 아이디와 비교 같으면	*/
						followCheck = true;	/**	참값 저장 후 탈출	*/
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