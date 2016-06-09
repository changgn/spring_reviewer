package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import command.MemberCommand;
import dao.MemberDAO;

@Controller
public class AdminMemController {
	@Autowired
	private MemberDAO memberDAO;
	
	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}
	@RequestMapping("adminMem.do")
	public ModelAndView adminMemForm(){
		ModelAndView mav = new ModelAndView();
		/**	회웍 목록	*/
		List<MemberCommand> member_list = null;
		member_list = memberDAO.getList();
		mav.addObject("memberList", member_list);	
		/**	전체 회원 수	*/
		int count = 0;
		count = memberDAO.count();
		mav.addObject("count", count);
		/**	view name 설정	*/
		mav.setViewName("administrator/adminMem");
		return mav;
	}
}