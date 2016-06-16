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
public class FollowController {
	@Autowired
	private FollowDAO followDAO;
	public void setFollowDAO(FollowDAO followDAO) {
		this.followDAO = followDAO;
	}

	/**	팔로우 추가	, 팔로워 목록에서 처리*/
	@RequestMapping("/follow/followerAdd.do")
	public ModelAndView addFollower(HttpServletRequest request,@RequestParam("add_id") String add_id, @RequestParam("profileId") String profileId, @RequestParam("follow") String follow){
		ModelAndView mav = new ModelAndView();
//		String from_id = (String)request.getSession().getAttribute("id");	/**	로그인 아이디	*/
		String from_id = "val1";
		FollowCommand followAdd = new FollowCommand(); /**	추가할 아이디를 저장할 객체 생성	*/
		System.out.println(add_id);
		followAdd.setFrom_id(from_id);	/**	내 아이디 저장	*/
		followAdd.setTo_id(add_id);	/**	추가할 아이디 저장	*/
		System.out.println(followAdd);
		
		if(from_id!=null) {	/**	로그인 상태일 경우 	*/
			if(follow.equals("follow")){ /**	팔로우 상태일 경우	*/
				FollowCommand followVo = new FollowCommand(from_id, add_id);	/**	formId - toId	*/
				int n = followDAO.followInsert(followVo); /**	팔로우 추가하고 변수 n에 값저장	*/
				if(n>0) {	/**	값이 있으면 팔로우 추가됨	*/
					System.out.println(from_id + "가" + add_id + "를 팔로우"); 
					
				} else {	/**	값이 없으면 팔로우 실패됨	*/
					System.out.println("팔로우 실패");
				}
			}
			if(follow.equals("unfollow")){	/**	팔로우 상태가 아닐 경우	*/
				FollowCommand followVo = new FollowCommand(from_id, add_id);
				int n = followDAO.remove(followVo);
				if(n>0) {
					System.out.println(from_id + "가" + add_id + "를 언팔로우");
				} else {
					System.out.println("언팔로우 실패");
				}
			}
			
		}
		List<String> from_id_list = followDAO.fromList(add_id);
		mav.addObject("fromList", from_id_list);
		System.out.println(from_id_list);
		if( from_id != null ) {	/**	로그인 상태일 경우	*/
			List<String> to_id_list = followDAO.toList(from_id);	/**	내가 팔로우한 목록 조회, 나의 팔로잉 목록	*/
			Map map = new HashMap();
			if( to_id_list != null ) {	/**	'내'가 팔로우한 목록이 있다면	*/
				for( String following : to_id_list ) {	/**	내 팔로우 목록 리스트를 하나씩 꺼내어	*/
					for(String follower : from_id_list){
						if(following.equals(follower)){
							map.put(follower, true);
						}else{
							if(follower.equals(from_id)){
								map.put(follower, true);
							}
							map.put(follower, false);
						}
					}
				}
			}
			mav.addObject("followCheck", map);
			System.out.println(map);
		}
		mav.addObject("profileId", profileId);
		mav.setViewName("/follow/followerForm");
		return mav;
	}
	
/*	*//**	팔로우 추가, 팔로잉 목록에서 처리	*//*
	@RequestMapping("/follow/ingFollow.do")
	public ModelAndView addFollowing(HttpServletRequest request, @RequestParam("id") String to_id, @RequestParam("follow") String follow){
		ModelAndView mav = new ModelAndView();
		String loginId = (String)request.getSession().getAttribute("id");	*//**	로그인 아이디	*//*
		FollowCommand fcAdd = new FollowCommand(); *//**	추가할 아이디를 저장할 객체 생성	*//*
		fcAdd.setFrom_id(loginId);	*//**	내 아이디 저장	*//*
		fcAdd.setTo_id(paramId);	*//**	추가할 아이디 저장	*//*
		System.out.println("추가 팔로잉, 팔로워 아이디" + fcAdd);
		
		if(loginId!=null) {	*//**	로그인 상태일 경우 	*//*
			if(follow.equals("follow")){ *//**	팔로우 상태일 경우	*//*
				FollowCommand followVo = new FollowCommand(loginId, paramId);	*//**	formId - toId	*//*
				int n = followDAO.followInsert(followVo); *//**	팔로우 추가하고 변수 n에 값저장	*//*
				if(n>0) {	*//**	값이 있으면 팔로우 추가됨	*//*
					System.out.println(loginId + "가" + paramId + "를 팔로우"); 
				} else {	*//**	값이 없으면 팔로우 실패됨	*//*
					System.out.println("팔로우 실패");
				}
			}
			if(follow.equals("unfollow")){	*//**	팔로우 상태가 아닐 경우	*//*
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
	}*/
}
