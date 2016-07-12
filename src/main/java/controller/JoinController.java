package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import command.MemberCommand;
import command.ProfilePhotoCommand;

@Controller
public class JoinController extends BaseController {
	
	//member 데이터 모델
	@ModelAttribute("memberCommand")
	public MemberCommand memberCommand(){
		return new MemberCommand();
	}
	// 회원가입 페이지 요청
	@RequestMapping(value="/member/join.do",method=RequestMethod.GET)
	public String regform(){
		return "member/inputForm";
	}
	@RequestMapping(value="/member/join.do",method=RequestMethod.POST) // 회원 가입 성공 페이지 요청
	public String action(MemberCommand memberInfo, Model model){
		int n = memberDao.inputPro(memberInfo); // 회원정보 추가,
	 //     insert into members values(#{id},#{passwd},#{name},#{birth},
	 //     #{gender},#{email},#{phone_num},sysdate,#{recommend_num})
		if(n != 0 ){
			ProfilePhotoCommand command = new ProfilePhotoCommand("default_profile.png", "default_profile.png", memberInfo.getId(), "/image/default_profile.png");
			//프로필 사진 정보 저장
			profilePhotoDao.insert(command); // 프로필 사진 정보 추가 / insert into profilephoto values(#{fileName},#{id},#{realPath},#{o_fileName})
			String smessage="회원 가입에 성공하셨습니다."; // 회원가입 성공메시지 저장
			model.addAttribute("smessage", smessage); // 회원가입 성공메시지 세션 KEY,VALUE 저장
		}
		else {
			String fmessage="회원 가입에 실패하셨습니다."; // 회원가입 실패 메시지 저장
			model.addAttribute("fmessage", fmessage); // 회원가입 실패메시지 세션 KEY,VALUE 저장
		}
		return "member/inputPro";
	}
	
	@RequestMapping("/member/idCheckForm.do") //ID 중복확인 페이지 요청
	public ModelAndView idCheck(String id){
		ModelAndView mav = new ModelAndView("member/idCheckForm"); // 해당 뷰 값 저장
		String ch = memberDao.idCheck(id); // 회원가입 시 해당에 대한 ID 중복 체크
		int check = 0; // 아이디 사용 가능 값
		if (ch != null) {
			check = 1; // 아이디 중복 값
		}
		mav.addObject("id", id); //해당 아이디 세션 KEY,VALUE 저장
		mav.addObject("idch", check); //id중복체크 세션 KEY, VALUE 저장
		
		return mav;
	}
	
}
