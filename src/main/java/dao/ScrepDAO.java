package dao;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import command.MemberCommand;
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
	
	public ScrepCommand getListById(String id){
		return getSqlSession().selectOne("screp.getListById", id);
	}
	
	public String findId(String id){
		return getSqlSession().selectOne("screp.findId", id);
	}
	
	public List<ScrepCommand> getList(){
		return getSqlSession().selectList("screp.getlist");
	}
}