package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import command.CategoryCommand;
import command.MemberCategoryCommand;
import dao.CategoryDAO;
import dao.MemberCategoryDAO;
import net.sf.json.JSONObject;

@Controller
public class CategoryController {

	@Autowired
	CategoryDAO categoryDao;
	@Autowired
	MemberCategoryDAO memberCategoryDao;
	
	public void setCategoryDao(CategoryDAO categoryDao) {
		this.categoryDao = categoryDao;
	}

	public void setMemberCategoryDao(MemberCategoryDAO memberCategoryDao) {
		this.memberCategoryDao = memberCategoryDao;
	}

	@RequestMapping(value="/categorySet/categorySet.do",method=RequestMethod.GET)
	public String categorySetForm(HttpServletRequest request, Model model){
		
		// 로그인한 id값 가져오기
		String id = (String) request.getSession().getAttribute("id");
		int CategoryListSize = 0;

		// Vo들을 담기위한 list 변수생성
		List<MemberCategoryCommand> membersCategoryList = null;
		List<CategoryCommand> CategoryList = new ArrayList<CategoryCommand>();
		
		// 해당 id의 카테고리id 가져오기
		membersCategoryList = memberCategoryDao.getlistById(id);
		
		// 카테고리id로 카테고리 가져오기
		for(MemberCategoryCommand membersCategory : membersCategoryList) {
			CategoryCommand Category = categoryDao.getOne(membersCategory.getCategory_id());
			CategoryList.add(Category);
		}
		
		CategoryListSize = CategoryList.size();
		
		// 뷰에서 사용할 Attribute 추가
		model.addAttribute("CategoryListSize", CategoryListSize);
		model.addAttribute("CategoryList", CategoryList);

		return "categorySet/categorySetForm";
	}

	@ResponseBody
	@RequestMapping(value="/categorySet/categorySet.do",method=RequestMethod.POST)
	public String categorySet(HttpServletResponse response, HttpServletRequest request, String addcount)
			 throws Exception{
		// 로그인한 id값 가져오기
		String id = (String) request.getSession().getAttribute("id");
		// JASON 객체생성
		JSONObject jso = new JSONObject(); 
		List<CategoryCommand> categoryList = new ArrayList<CategoryCommand>();

		if(addcount != null) {
			int addcount_int = Integer.parseInt(addcount);
			if(addcount_int != 0) {
				int addedcount = 0;
				for(int i=1; i<addcount_int+1; i++) {
					String addname = "add" + i;
					String category_id = request.getParameter(addname);
					MemberCategoryCommand command = new MemberCategoryCommand(id, category_id);
					addedcount += memberCategoryDao.insert(command);
					CategoryCommand category = categoryDao.getOne(category_id);
					categoryList.add(category);
				}
				System.out.println(id + "의 추가된 카테고리 갯수 : " + addedcount);
			}
		}
		
		jso.put("categoryList", categoryList);
		response.setContentType("text/html;charset=utf-8");
		return jso.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/categorySet/categorydel.do",method=RequestMethod.POST)
	public String categorydel(HttpServletResponse response, HttpServletRequest request, Model model, String delCategory){
		
		// JASON 객체생성
		JSONObject jso = new JSONObject(); 
		MemberCategoryCommand command = null;
		if(delCategory != null) {		
			
			// 로그인한 id값 가져오기
			String id = (String) request.getSession().getAttribute("id");
			
			// add_ 부분 제거
			delCategory = delCategory.substring(4);
			command = new MemberCategoryCommand(id, delCategory);
			int deleteOk = memberCategoryDao.delete(command);
			
			if(deleteOk==0){
				System.out.println("카테고리 삭제 실패");
			} else {
				System.out.println(id + "의 " + delCategory + " 카테고리 삭제 성공" );
			}
			jso.put("delCategory", delCategory);
		}
		response.setContentType("text/html;charset=utf-8");
		return jso.toString();
	}
}
