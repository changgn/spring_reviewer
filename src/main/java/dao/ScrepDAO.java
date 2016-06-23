package dao;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import command.MemberCommand;
import command.RecommendCommand;
import command.ScrepCommand;

@Repository
public class ScrepDAO extends SqlSessionDaoSupport {
	

	public List<String> getIdByScrepdNum(int board_num){
		return getSqlSession().selectList("screp.getIdByScrepNum", board_num);
	}
	
	public ScrepCommand getScrep(ScrepCommand command){
		return getSqlSession().selectOne("screp.getScrep", command);
	}
	
	public int insertScrep(ScrepCommand command){
		return getSqlSession().insert("screp.insertScrep", command);
	}
	
	public Integer getScrepCountByScrepNum(Integer board_num){
		return getSqlSession().selectOne("screp.getRecommendCountByScrepNum", board_num);
	}
	
	public int deleteScrep(ScrepCommand command){
		return getSqlSession().delete("screp.deleteScrep", command);
	}
	
    public List<ScrepCommand> getScrepList(){
    	return getSqlSession().selectList("screp.getScrepList");
    }
}