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

	/**	�ȷο� �߰�	, �ȷο� ��Ͽ��� ó��*/
	@RequestMapping("/follow/followerAdd.do")
	public ModelAndView addFollower(HttpServletRequest request,@RequestParam("add_id") String add_id, @RequestParam("follow") String follow){
		ModelAndView mav = new ModelAndView();
//		String from_id = (String)request.getSession().getAttribute("id");	/**	�α��� ���̵�	*/
		String from_id = "val1";
		FollowCommand followAdd = new FollowCommand(); /**	�߰��� ���̵� ������ ��ü ����	*/
		System.out.println(add_id);
		followAdd.setFrom_id(from_id);	/**	�� ���̵� ����	*/
		followAdd.setTo_id(add_id);	/**	�߰��� ���̵� ����	*/
		System.out.println(followAdd);
		
		if(from_id!=null) {	/**	�α��� ������ ��� 	*/
			if(follow.equals("follow")){ /**	�ȷο� ������ ���	*/
				FollowCommand followVo = new FollowCommand(from_id, add_id);	/**	formId - toId	*/
				int n = followDAO.followInsert(followVo); /**	�ȷο� �߰��ϰ� ���� n�� ������	*/
				if(n>0) {	/**	���� ������ �ȷο� �߰���	*/
					System.out.println(from_id + "��" + add_id + "�� �ȷο�"); 
					
				} else {	/**	���� ������ �ȷο� ���е�	*/
					System.out.println("�ȷο� ����");
				}
			}
			if(follow.equals("unfollow")){	/**	�ȷο� ���°� �ƴ� ���	*/
				FollowCommand followVo = new FollowCommand(from_id, add_id);
				int n = followDAO.remove(followVo);
				if(n>0) {
					System.out.println(from_id + "��" + add_id + "�� ���ȷο�");
				} else {
					System.out.println("���ȷο� ����");
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
