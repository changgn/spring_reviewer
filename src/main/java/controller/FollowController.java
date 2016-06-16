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

	/**	�ȷο� �߰�	, �ȷο� ��Ͽ��� ó��*/
	@RequestMapping("/follow/followerAdd.do")
	public ModelAndView addFollower(HttpServletRequest request,@RequestParam("paramId") String paramId, @RequestParam("id") String id, @RequestParam("follow") String follow){
		ModelAndView mav = new ModelAndView();
//		String loginId = (String)request.getSession().getAttribute("id");	/**	�α��� ���̵�	*/
		String loginId = "val1";
		FollowCommand followAdd = new FollowCommand(); /**	�߰��� ���̵� ������ ��ü ����	*/
		System.out.println(paramId);
		followAdd.setFrom_id(loginId);	/**	�� ���̵� ����	*/
		followAdd.setTo_id(paramId);	/**	�߰��� ���̵� ����	*/
		System.out.println(followAdd);
		
		if(loginId!=null) {	/**	�α��� ������ ��� 	*/
			if(follow.equals("follow")){ /**	�ȷο� ������ ���	*/
				FollowCommand followVo = new FollowCommand(loginId, paramId);	/**	formId - toId	*/
				int n = followDAO.followInsert(followVo); /**	�ȷο� �߰��ϰ� ���� n�� ������	*/
				if(n>0) {	/**	���� ������ �ȷο� �߰���	*/
					System.out.println(loginId + "��" + paramId + "�� �ȷο�"); 
					
				} else {	/**	���� ������ �ȷο� ���е�	*/
					System.out.println("�ȷο� ����");
				}
			}
			if(follow.equals("unfollow")){	/**	�ȷο� ���°� �ƴ� ���	*/
				FollowCommand followVo = new FollowCommand(loginId, paramId);
				int n = followDAO.remove(followVo);
				if(n>0) {
					System.out.println(loginId + "��" + paramId + "�� ���ȷο�");
				} else {
					System.out.println("���ȷο� ����");
				}
			}
		}
		List<FollowCommand> from_id_list = followDAO.fromList(id);
		mav.addObject("fromList", from_id_list);
		if( loginId != null ) {	/**	�α��� ������ ���	*/
			List<String> to_id_list = followDAO.toList(loginId);	/**	���� �ȷο��� ��� ��ȸ, ���� �ȷ��� ���	*/
			boolean followCheck = false;
			if( to_id_list != null ) {	/**	'��'�� �ȷο��� ����� �ִٸ�	*/
				for( String following : to_id_list ) {	/**	�� �ȷο� ��� ����Ʈ�� �ϳ��� ������	*/
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
	
	/**	�ȷο� �߰�, �ȷ��� ��Ͽ��� ó��	*/
	@RequestMapping("/follow/ingFollow.do")
	public ModelAndView addFollowing(HttpServletRequest request, @RequestParam("id") String paramId, @RequestParam("follow") String follow){
		ModelAndView mav = new ModelAndView();
		String loginId = (String)request.getSession().getAttribute("id");	/**	�α��� ���̵�	*/
		FollowCommand fcAdd = new FollowCommand(); /**	�߰��� ���̵� ������ ��ü ����	*/
		fcAdd.setFrom_id(loginId);	/**	�� ���̵� ����	*/
		fcAdd.setTo_id(paramId);	/**	�߰��� ���̵� ����	*/
		System.out.println("�߰� �ȷ���, �ȷο� ���̵�" + fcAdd);
		
		if(loginId!=null) {	/**	�α��� ������ ��� 	*/
			if(follow.equals("follow")){ /**	�ȷο� ������ ���	*/
				FollowCommand followVo = new FollowCommand(loginId, paramId);	/**	formId - toId	*/
				int n = followDAO.followInsert(followVo); /**	�ȷο� �߰��ϰ� ���� n�� ������	*/
				if(n>0) {	/**	���� ������ �ȷο� �߰���	*/
					System.out.println(loginId + "��" + paramId + "�� �ȷο�"); 
				} else {	/**	���� ������ �ȷο� ���е�	*/
					System.out.println("�ȷο� ����");
				}
			}
			if(follow.equals("unfollow")){	/**	�ȷο� ���°� �ƴ� ���	*/
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
	}
}
