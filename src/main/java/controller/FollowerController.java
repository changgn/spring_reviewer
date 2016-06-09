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
	/** �ȷο�, �ٸ������ '��'�� �ȷο� */
	@RequestMapping("follower.do")
	public ModelAndView followerForm(HttpServletRequest request, @RequestParam("id") String to_id ){
		ModelAndView mav = new ModelAndView();
		String from_id = (String) request.getSession().getAttribute("id");	/**	�α��� ���̵�	*/
		 /**	�ȷο� ���� DB���� ��� ��ȸ	*/
		List<FollowCommand> from_id_list = followDAO.fromList(to_id);
		mav.addObject("fromList", from_id_list);
		if( from_id != null ) {	/**	�α��� ������ ���	*/
			List<FollowCommand> to_id_list = followDAO.toList(from_id);	/**	���� �ȷο��� ��� ��ȸ, ���� �ȷ��� ���	*/
			boolean followCheck = false;
			if( to_id_list != null ) {	/**	'��'�� �ȷο��� ����� �ִٸ�	*/
				for( FollowCommand following : to_id_list ) {	/**	�� �ȷο� ��� ����Ʈ�� �ϳ��� ������	*/
					if( following.equals(to_id) ) {	/**	�ش� ���̵�� �� ������	*/
						followCheck = true;	/**	���� ���� �� Ż��	*/
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