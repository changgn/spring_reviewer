package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import command.FollowCommand;
import dao.FollowDAO;
import net.sf.json.JSONObject;

@Controller
public class FollowController {
	@Autowired
	private FollowDAO followDAO;
	public void setFollowDAO(FollowDAO followDAO) {
		this.followDAO = followDAO;
	}

	@ResponseBody
	@RequestMapping("/follow/follow.do")
	public String Follow(HttpServletRequest request, HttpServletResponse resp, String to_id, String follow, Model model){
		String from_id = (String)request.getSession().getAttribute("id");

		JSONObject jso = new JSONObject();
		
		FollowCommand followAdd = new FollowCommand();
		followAdd.setFrom_id(from_id);
		followAdd.setTo_id(to_id);
		
		if(from_id!=null) {
			if(follow.equals("follow")){
				FollowCommand followVo = new FollowCommand(from_id, to_id);
				int n = followDAO.followInsert(followVo);
				if(n>0) {
					model.addAttribute("followCheck", false);
					System.out.println(from_id + "가" + to_id + "팔로우"); 
				} else {
					System.out.println("팔로우 실패");
				}
			}
			if(follow.equals("unfollow")){
				FollowCommand followVo = new FollowCommand(from_id, to_id);
				int n = followDAO.remove(followVo);
				if(n>0) {
					model.addAttribute("followCheck", true);
					System.out.println(from_id + "가" + to_id + "언팔로우");
				} else {
					System.out.println("언팔로우 실패");
				}
			}

			//팔로워 숫자 저장
			int followerCount =followDAO.countfrom(to_id);
			jso.put("followerCount", followerCount);
		}
		jso.put("follow", follow);
		resp.setContentType("text/html;charset=utf-8");
		return jso.toString();
	
	}
	
	/**	�ȷο� �߰�	, �ȷο� ��Ͽ��� ó��*/
	@RequestMapping("/follow/followerAdd.do")
	public ModelAndView addFollower(HttpServletRequest request,@RequestParam("add_id") String add_id, @RequestParam("follow") String follow){
		ModelAndView mav = new ModelAndView();
		String from_id = (String)request.getSession().getAttribute("id");	/**	�α��� ���̵�	*/

		FollowCommand followAdd = new FollowCommand(); /**	�߰��� ���̵� ������ ��ü ����	*/
		followAdd.setFrom_id(from_id);	/**	�� ���̵� ����	*/
		followAdd.setTo_id(add_id);	/**	�߰��� ���̵� ����	*/
		
		if(from_id!=null) {	/**	�α��� ������ ��� 	*/
			if(follow.equals("follow")){ /**	�ȷο� ������ ���	*/
				FollowCommand followVo = new FollowCommand(from_id, add_id);	/**	formId - toId	*/
				int n = followDAO.followInsert(followVo); /**	�ȷο� �߰��ϰ� ���� n�� ������	*/
				if(n>0) {	/**	���� ������ �ȷο� �߰���	*/
					System.out.println(from_id + "가" + add_id + "팔로우"); 
					
				} else {	/**	���� ������ �ȷο� ���е�	*/
					System.out.println("팔로우 실패");
				}
			}
			if(follow.equals("unfollow")){	/**	�ȷο� ���°� �ƴ� ���	*/
				FollowCommand followVo = new FollowCommand(from_id, add_id);
				int n = followDAO.remove(followVo);
				if(n>0) {
					System.out.println(from_id + "가" + add_id + "언팔로우");
				} else {
					System.out.println("언팔로우 실패");
				}
			}
			
		}
		List<String> from_id_list = followDAO.fromList(add_id);
		mav.addObject("fromList", from_id_list);
		System.out.println(from_id_list);
		if( from_id != null ) {	/**	�α��� ������ ���	*/
			List<String> to_id_list = followDAO.toList(from_id);	/**	���� �ȷο��� ��� ��ȸ, ���� �ȷ��� ���	*/
			Map map = new HashMap();
			if( to_id_list != null ) {	/**	'��'�� �ȷο��� ����� �ִٸ�	*/
				for( String following : to_id_list ) {	/**	�� �ȷο� ��� ����Ʈ�� �ϳ��� ������	*/
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
	/*	mav.addObject("profileId", profileId);*/
		mav.setViewName("/follow/followerForm");
		return mav;
	}
	
/*	*//**	�ȷο� �߰�, �ȷ��� ��Ͽ��� ó��	*//*
	@RequestMapping("/follow/ingFollow.do")
	public ModelAndView addFollowing(HttpServletRequest request, @RequestParam("id") String to_id, @RequestParam("follow") String follow){
		ModelAndView mav = new ModelAndView();
		String loginId = (String)request.getSession().getAttribute("id");	*//**	�α��� ���̵�	*//*
		FollowCommand fcAdd = new FollowCommand(); *//**	�߰��� ���̵� ������ ��ü ����	*//*
		fcAdd.setFrom_id(loginId);	*//**	�� ���̵� ����	*//*
		fcAdd.setTo_id(paramId);	*//**	�߰��� ���̵� ����	*//*
		System.out.println("�߰� �ȷ���, �ȷο� ���̵�" + fcAdd);
		
		if(loginId!=null) {	*//**	�α��� ������ ��� 	*//*
			if(follow.equals("follow")){ *//**	�ȷο� ������ ���	*//*
				FollowCommand followVo = new FollowCommand(loginId, paramId);	*//**	formId - toId	*//*
				int n = followDAO.followInsert(followVo); *//**	�ȷο� �߰��ϰ� ���� n�� ������	*//*
				if(n>0) {	*//**	���� ������ �ȷο� �߰���	*//*
					System.out.println(loginId + "��" + paramId + "�� �ȷο�"); 
				} else {	*//**	���� ������ �ȷο� ���е�	*//*
					System.out.println("�ȷο� ����");
				}
			}
			if(follow.equals("unfollow")){	*//**	�ȷο� ���°� �ƴ� ���	*//*
				FollowCommand followVo = new FollowCommand(loginId, paramId);
				int n = followDAO.remove(followVo);
				if(n>0) {
					System.out.println(loginId + "��" + paramId + "�� ���ȷο�");
				} else {
					System.out.println("���ȷο� ����");
				}
			}
		}
		
		
		mav.addObject("id", paramId);
		mav.setViewName("follow/followingForm");
		return mav;
	}*/
}
