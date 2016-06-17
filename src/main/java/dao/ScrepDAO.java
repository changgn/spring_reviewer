package dao;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;


import command.ScrepCommand;

@Repository
public class ScrepDAO extends SqlSessionDaoSupport {
	
//수정
	public int insertScrep(ScrepCommand command){
		return getSqlSession().insert("screp.add", command);
	}
	
	public void deleteScrep(String id){
		int n = getSqlSession().delete("screp.remove", id);
	}
	
	public ScrepCommand findScrep(String id){
		return getSqlSession().selectOne("screp.find", id);
	}
	
	public String findId(String id){
		return getSqlSession().selectOne("screp.findId", id);
	}
		
		
		
		
		
		
}