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
public class FollowerController { 
	@Autowired
	private FollowDAO followDAO;
	public void setFollowDAO(FollowDAO followDAO) {
		this.followDAO = followDAO;
	}
	/** �ȷο�, �ٸ������ '��'�� �ȷο� */
	@RequestMapping("/follow/follower.do")
	public ModelAndView followerForm(HttpServletRequest request, @RequestParam("id") String to_id ){
		ModelAndView mav = new ModelAndView();
//		String from_id = (String) request.getSession().getAttribute("id");	/**	�α��� ���̵�	*/
		String from_id = "val1";
		 /**	�ȷο� ���� DB���� ��� ��ȸ	*/
		List<String> from_id_list = followDAO.fromList(to_id);
		mav.addObject("fromList", from_id_list);
		System.out.println(to_id+"�� �ȷο� ��� : "+from_id_list);
		if( from_id != null ) {	/**	�α��� ������ ���	*/
			List<String> to_id_list = followDAO.toList(from_id);	/**	���� �ȷο��� ��� ��ȸ, ���� �ȷ��� ���	*/
			System.out.println(from_id+"�� �ȷ��� ��� : " + to_id_list);
			Map map = new HashMap();
			List<Map> m = new ArrayList<Map>();
			if( to_id_list != null ) {	/**	'��'�� �ȷο��� ����� �ִٸ�	*/
				for( String follower : from_id_list ) {	/**	�� �ȷο� ��� ����Ʈ�� �ϳ��� ������	*/
					for(String following : to_id_list){
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
			System.out.println("�ȷο� ���°�:"+map);
		}
		mav.addObject("profileId", to_id);
		mav.setViewName("follow/followerForm");
		return mav;
	}
}