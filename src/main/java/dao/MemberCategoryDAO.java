package dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import command.MemberCategoryCommand;

public class MemberCategoryDAO extends SqlSessionDaoSupport{
	public int insert(MemberCategoryCommand command) {
		return getSqlSession().insert("membercategory.add", command);
	}
	public List<MemberCategoryCommand> getlistById(String id) {
		return getSqlSession().selectList("membercategory.getlistById", id);
	}
	public List<String> getCategoryIdById(String id) {
		return getSqlSession().selectList("membercategory.getCategoryIdById", id);
	}
	public int delete(MemberCategoryCommand command) {
		return getSqlSession().delete("membercategory.remove", command);
	}
}
