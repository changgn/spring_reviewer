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
	} // 해당번호의 게시물을 스크랩 한 회원 목록 / select id from screp where board_num=#{board_num}
	
	public ScrepCommand getScrep(ScrepCommand command){
		return getSqlSession().selectOne("screp.getScrep", command);
	} // 스크렙 모든 정보 / select id,board_num from screp
	
	public int insertScrep(ScrepCommand command){
		return getSqlSession().insert("screp.insertScrep", command);
	} // 스크렙 게시물 추가 / insert into screp values(#{id},#{board_num})
	
	public Integer getScrepCountByScrepNum(String id){
		return getSqlSession().selectOne("screp.getScrepCountByScrepNum", id);
	} // 해당 회원이 스크렙한 게시글 갯수 / select count(board_num) from screp where id=#{id}
	
	public int deleteScrep(ScrepCommand command){
		return getSqlSession().delete("screp.deleteScrep", command);
	} //스크렙 게시물 삭제 / delete from screp where id=#{id} and board_num=#{board_num}
	
    public List<ScrepCommand> getScrepList(){
    	return getSqlSession().selectList("screp.getScrepList");
    }
    public List<Integer>getScrepListById(String id){
    	return getSqlSession().selectList("screp.getScrepListById", id);
    } // 해당회원이 스크렙 한 글번호 / select board_num from screp where id=#{id}
    
    public Integer getCountByBoardNum(String id){
		return getSqlSession().selectOne("screp.getCountByBoardNum", id);
	} // 회원들이 스크렙한 게시글 번호 / select count(board_num) from board where id=#{id}
    
    public int updateScrepByBoardNum(HashMap<String, Object> map){
		return getSqlSession().update("screp.updateScrepByBoardNum", map);
	} // 스크렙 정보 수정 / update board set screp=#{screp} where board_num=#{board_num}
	
   
    public Integer getCountByScrepNum(Integer board_num){
		return getSqlSession().selectOne("screp.getCountByScrepNum", board_num);
	} // 해당번호의 게시글을 스크렙한 id수(즉, 해당 게시글 스크렙 갯수) / select count(id) from screp where board_num=#{board_num}
 }