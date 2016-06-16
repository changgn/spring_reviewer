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
		if(my_to_id!=null){
			List<String> my_to_id_list = followDAO.toList(my_to_id);	/**	로그인한 Id. 즉, '나'의 팔로잉 목록	*/
			boolean followCheck = false;	/**	팔로우 상태 값	*/
			if(my_to_id_list!=null){	/**	'내'가 팔로우한 목록이 있다면	*/
				for(String following : my_to_id_list){	/**	내 팔로우 목록 리스트를 하나씩 꺼내어	*/
					if(following.equals(to_id)){	/**	해당 아이디와 비교 같으면	*/
						followCheck = true;	/**	참값 저장 후 탈출	*/
						break;
					}
				}
			}
			mav.addObject("followCheck", followCheck);
		}
		mav.addObject("id", to_id);
		mav.setViewName("follow/followingForm");
		return mav;
	}
}
