package dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import command.BoardCommand;

@Repository
public class BoardDAO extends SqlSessionDaoSupport{

	public int insertBoard(BoardCommand command){
		return getSqlSession().insert("board.add", command);
	}

	public BoardCommand selectContent(Integer board_num){
		return getSqlSession().selectOne("board.getByBoardNum", board_num);
	}
	
	public int deleteContent(Integer board_num){
		return getSqlSession().delete("board.removeByBoardNum", board_num);
	}
	
	public Integer getRecentBoardNumById(String id){
		return getSqlSession().selectOne("board.getRecentBoardNumById", id);
	}
	
	public List<BoardCommand> pupulBoardList(){
		return getSqlSession().selectList("board.getPopularityBoardList");
	}
	
	public List<BoardCommand> reportBoardList(){
		return getSqlSession().selectList("board.getReportBoardList");
	}
	
	public List<BoardCommand> getList(){
		return getSqlSession().selectList("board.getList");
	}
	
	public List<BoardCommand> getListByCategoryIdContent(HashMap<String, Object> categoryIdContentMap){
		return getSqlSession().selectList("board.getListByCategoryIdContent", categoryIdContentMap);
	}
	public List<BoardCommand> getListByContent(String content) {
		return getSqlSession().selectList("board.getListByContent", content);
	}
}
