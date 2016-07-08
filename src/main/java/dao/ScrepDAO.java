package dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import command.ScrepCommand;

@Repository
public class ScrepDAO extends SqlSessionDaoSupport {
	

	public List<String> getIdByScrepNum(int board_num){
		return getSqlSession().selectList("screp.getIdByScrepNum", board_num);
	}
	
	public ScrepCommand getScrep(ScrepCommand command){
		return getSqlSession().selectOne("screp.getScrep", command);
	}
	
	public int insertScrep(ScrepCommand command){
		return getSqlSession().insert("screp.insertScrep", command);
	}
	
	public Integer getScrepCountByScrepNum(String id){
		return getSqlSession().selectOne("screp.getScrepCountByScrepNum", id);
	}
	/**	해당 게시글 스크랩 수	*/
	public Integer getBoardScrepCountById(int board_num){
		return getSqlSession().selectOne("screp.getBoardScrepCountById", board_num);
	}
	
	public int deleteScrep(ScrepCommand command){
		return getSqlSession().delete("screp.deleteScrep", command);
	}
	
    public List<ScrepCommand> getScrepList(){
    	return getSqlSession().selectList("screp.getScrepList");
    }
    public List<Integer>getScrepListById(String id){
    	return getSqlSession().selectList("screp.getScrepListById", id);
    }
    
    public Integer getCountByBoardNum(String id){
		return getSqlSession().selectOne("screp.getCountByBoardNum", id);
	}
    
    public int updateScrepByBoardNum(HashMap<String, Object> map){
		return getSqlSession().update("screp.updateScrepByBoardNum", map);
	}
   
    public Integer getCountByScrepNum(Integer board_num){
		return getSqlSession().selectOne("screp.getCountByScrepNum", board_num);
	}
 }