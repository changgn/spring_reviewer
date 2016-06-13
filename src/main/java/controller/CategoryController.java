package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import command.CategoryCommand;
import command.MemberCategoryCommand;
import dao.CategoryDAO;
import dao.MemberCategoryDAO;

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
		
		// �α����� id�� ��������
		String id = (String) request.getSession().getAttribute("id");
		id="val";
		int CategoryListSize = 0;

		// Vo���� ������� list ��������
		List<MemberCategoryCommand> membersCategoryList = null;
		List<CategoryCommand> CategoryList = new ArrayList<CategoryCommand>();
		
		// �ش� id�� ī�װ���id ��������
		membersCategoryList = memberCategoryDao.getlistById(id);
		
		// ī�װ���id�� ī�װ��� ��������
		for(MemberCategoryCommand membersCategory : membersCategoryList) {
			CategoryCommand Category = categoryDao.getOne(membersCategory.getCategory_id());
			CategoryList.add(Category);
		}
		
		CategoryListSize = CategoryList.size();
		
		// �信�� ����� Attribute �߰�
		model.addAttribute("CategoryListSize", CategoryListSize);
		model.addAttribute("CategoryList", CategoryList);

		return "categorySet/categorySetForm";
	}

	@RequestMapping(value="/categorySet/categorySet.do",method=RequestMethod.POST)
	public String categorySet(HttpServletRequest request, Model model, String addcount){
		// �α����� id�� ��������
		String id = (String) request.getSession().getAttribute("id");
		
		if(addcount != null) {
			int addcount_int = Integer.parseInt(addcount);
			if(addcount_int != 0) {
				int addedcount = 0;
				for(int i=1; i<addcount_int+1; i++) {
					String addname = "add" + i;
					String category_id = request.getParameter(addname);
					MemberCategoryCommand command = new MemberCategoryCommand(id, category_id);
					addedcount += memberCategoryDao.insert(command);
				}
				System.out.println(id + "�� �߰��� ī�װ��� ���� : " + addedcount);
			}
		}
		return "redirect:/categorySet/categorySet.do";
	}
	@RequestMapping(value="/categorySet/categorydel.do",method=RequestMethod.POST)
	public String categorydel(HttpServletRequest request, Model model, String delCategory){
		if(delCategory != null) {		
			
			// �α����� id�� ��������
			String id = (String) request.getSession().getAttribute("id");
			
			// add_ �κ� ����
			delCategory = delCategory.substring(4);
			MemberCategoryCommand vo = new MemberCategoryCommand(id, delCategory);
			int deleteOk = memberCategoryDao.delete(vo);
			
			if(deleteOk==0){
				System.out.println("ī�װ��� ���� ����");
			} else {
				System.out.println(id + "�� " + delCategory + " ī�װ��� ���� ����" );
			}
		}
		return "redirect:/categorySet/categorySet.do";
	}
}