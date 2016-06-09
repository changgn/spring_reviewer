package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import command.BoardCommand;

@Repository
public class BoardDAO extends SqlSessionDaoSupport{

	//ï¿½ï¿½ ï¿½Ô·ï¿½ï¿½Ï±ï¿½
	public int insertBoard(BoardCommand command){
		return getSqlSession().insert("board.add", command);
	}


	//ï¿½Ô½Ã¹ï¿½ ï¿½ï¿½ï¿½ëº¸ï¿½ï¿½
	public BoardCommand selectContent(Integer board_num){
		return getSqlSession().selectOne("board.getByBoardNum", board_num);
	}

	//ï¿½Ô½Ã±ï¿½ ï¿½ï¿½ï¿½ï¿½ï¿½Ï±ï¿½
	public int deleteContent(Integer board_num){
		return getSqlSession().delete("board.removeByBoardNum", board_num);
	}
	
	public Integer getRecentBoardNumById(String id){
		return getSqlSession().selectOne("board.getRecentBoardNumById", id);
	}
	
	/**	ÀÎ±â±Û	*/
	public List<BoardCommand> pupulBoardList(){
		return getSqlSession().selectList("board.getPopularityBoardList");
	}
	/**	½Å°í±Û	*/
	public List<BoardCommand> reportBoardList(){
		return getSqlSession().selectList("board.getReportBoardList");
	}
}
