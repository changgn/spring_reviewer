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
	/**	�ȷ���, '��'�� �ٸ������ �ȷο�	*/
	@RequestMapping("/follow/follewing.do")
	public ModelAndView followingForm(HttpServletRequest request, @RequestParam("id") String to_id ){
		ModelAndView mav = new ModelAndView();
		String my_to_id = (String)request.getSession().getAttribute("id");	/**	�α��� ���̵�	*/
		List<String> to_id_list = followDAO.toList(to_id);	/**	����� ������ Id�� �ȷ��� ��� ��ȸ, ����	*/
		mav.addObject("toIdList", to_id_list);
		if(my_to_id!=null){
			List<String> my_to_id_list = followDAO.toList(my_to_id);	/**	�α����� Id. ��, '��'�� �ȷ��� ���	*/
			boolean followCheck = false;	/**	�ȷο� ���� ��	*/
			if(my_to_id_list!=null){	/**	'��'�� �ȷο��� ����� �ִٸ�	*/
				for(String following : my_to_id_list){	/**	�� �ȷο� ��� ����Ʈ�� �ϳ��� ������	*/
					if(following.equals(to_id)){	/**	�ش� ���̵�� �� ������	*/
						followCheck = true;	/**	���� ���� �� Ż��	*/
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
