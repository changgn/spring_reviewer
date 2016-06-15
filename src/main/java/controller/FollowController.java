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
public class FollowController {
	@Autowired
	private FollowDAO followDAO;
	public void setFollowDAO(FollowDAO followDAO) {
		this.followDAO = followDAO;
	}

	/**	팔로우 추가	, 팔로워 목록에서 처리*/
	@RequestMapping("/follow/followerAdd.do")
	public ModelAndView addFollower(HttpServletRequest request,@RequestParam("paramId") String paramId, @RequestParam("id") String id, @RequestParam("follow") String follow){
		ModelAndView mav = new ModelAndView();
//		String loginId = (String)request.getSession().getAttribute("id");	/**	로그인 아이디	*/
		String loginId = "val1";
		FollowCommand followAdd = new FollowCommand(); /**	추가할 아이디를 저장할 객체 생성	*/
		System.out.println(paramId);
		followAdd.setFrom_id(loginId);	/**	내 아이디 저장	*/
		followAdd.setTo_id(paramId);	/**	추가할 아이디 저장	*/
		System.out.println(followAdd);
		
		if(loginId!=null) {	/**	로그인 상태일 경우 	*/
			if(follow.equals("follow")){ /**	팔로우 상태일 경우	*/
				FollowCommand followVo = new FollowCommand(loginId, paramId);	/**	formId - toId	*/
				int n = followDAO.followInsert(followVo); /**	팔로우 추가하고 변수 n에 값저장	*/
				if(n>0) {	/**	값이 있으면 팔로우 추가됨	*/
					System.out.println(loginId + "가" + paramId + "를 팔로우"); 
					
				} else {	/**	값이 없으면 팔로우 실패됨	*/
					System.out.println("팔로우 실패");
				}
			}
			if(follow.equals("unfollow")){	/**	팔로우 상태가 아닐 경우	*/
				FollowCommand followVo = new FollowCommand(loginId, paramId);
				int n = followDAO.remove(followVo);
				if(n>0) {
					System.out.println(loginId + "가" + paramId + "를 언팔로우");
				} else {
					System.out.println("언팔로우 실패");
				}
			}
		}
		List<FollowCommand> from_id_list = followDAO.fromList(id);
		mav.addObject("fromList", from_id_list);
		if( loginId != null ) {	/**	로그인 상태일 경우	*/
			List<FollowCommand> to_id_list = followDAO.toList(loginId);	/**	내가 팔로우한 목록 조회, 나의 팔로잉 목록	*/
			boolean followCheck = false;
			if( to_id_list != null ) {	/**	'내'가 팔로우한 목록이 있다면	*/
				for( FollowCommand following : to_id_list ) {	/**	내 팔로우 목록 리스트를 하나씩 꺼내어	*/
					if(following.equals(id)){
						followCheck = true;
						break;
					}
				}
			}
			mav.addObject("followCheck", followCheck);
		}
		mav.addObject("id", id);
		mav.setViewName("/follow/followerForm");
		return mav;
	}
	
	/**	팔로우 추가, 팔로잉 목록에서 처리	*/
	@RequestMapping("/follow/ingFollow.do")
	public ModelAndView addFollowing(HttpServletRequest request, @RequestParam("id") String paramId, @RequestParam("follow") String follow){
		ModelAndView mav = new ModelAndView();
		String loginId = (String)request.getSession().getAttribute("id");	/**	로그인 아이디	*/
		FollowCommand fcAdd = new FollowCommand(); /**	추가할 아이디를 저장할 객체 생성	*/
		fcAdd.setFrom_id(loginId);	/**	내 아이디 저장	*/
		fcAdd.setTo_id(paramId);	/**	추가할 아이디 저장	*/
		System.out.println("추가 팔로잉, 팔로워 아이디" + fcAdd);
		
		if(loginId!=null) {	/**	로그인 상태일 경우 	*/
			if(follow.equals("follow")){ /**	팔로우 상태일 경우	*/
				FollowCommand followVo = new FollowCommand(loginId, paramId);	/**	formId - toId	*/
				int n = followDAO.followInsert(followVo); /**	팔로우 추가하고 변수 n에 값저장	*/
				if(n>0) {	/**	값이 있으면 팔로우 추가됨	*/
					System.out.println(loginId + "가" + paramId + "를 팔로우"); 
				} else {	/**	값이 없으면 팔로우 실패됨	*/
					System.out.println("팔로우 실패");
				}
			}
			if(follow.equals("unfollow")){	/**	팔로우 상태가 아닐 경우	*/
				FollowCommand followVo = new FollowCommand(loginId, paramId);
				int n = followDAO.remove(followVo);
				if(n>0) {
					System.out.println(loginId + "가" + paramId + "를 언팔로우");
				} else {
					System.out.println("언팔로우 실패");
				}
			}
		}
		
		
		mav.addObject("id", paramId);
		mav.setViewName("follow/followingForm");
		return mav;
	}
}
