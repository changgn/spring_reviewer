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
		System.out.println(to_id+"�� �ȷ��� ��� : "+to_id_list);
		if(my_to_id!=null){
			List<String> my_to_id_list = followDAO.toList(my_to_id);	/**	�α����� Id. ��, '��'�� �ȷ��� ���	*/
			boolean followCheck = false;	/**	�ȷο� ���� ��	*/
			Map map = new HashMap();
			List<Map> m = new ArrayList<Map>();
			if(my_to_id_list!=null){	/**	'��'�� �ȷο��� ����� �ִٸ�	*/
				for(String following : my_to_id_list){	/**	�� �ȷο� ��� ����Ʈ�� �ϳ��� ������	*/
					for(String tofollowing : to_id_list){
						if(tofollowing.equals(following)){	/**	�ش� ���̵�� �� ������	*/
							map.put(tofollowing, true);
						}else{
							if(tofollowing.equals(my_to_id)){
								map.put(tofollowing, true);
							}
							map.put(tofollowing, false);
						}
					}
				}
			}
			mav.addObject("followCheck", map);
			System.out.println("�� ����Ʈ�� boolean "+ map);
		}
		mav.addObject("id", to_id);
		mav.setViewName("follow/followingForm");
		return mav;
	}
}
