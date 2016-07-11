package dao;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import command.CategoryCommand;

@Repository
public class CategoryDAO extends SqlSessionDaoSupport{
	
	public CategoryCommand getOne(String category_id){
		return getSqlSession().selectOne("category.getOne", category_id);
	}
}
