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
	
	public int updateReportNumByBoardNum(Integer board_num){
		return getSqlSession().update("board.updateReportNumByBoardNum", board_num);
	}
	
	public Integer getRecentBoardNumById(String id){
		return getSqlSession().selectOne("board.getRecentBoardNumById", id);
	}
	/**	인기글	*/
	public List<BoardCommand> pupulBoardList(){
		return getSqlSession().selectList("board.getPopularityBoardList");
	}
	/**	신고글	*/
	public List<BoardCommand> reportBoardList(){
		return getSqlSession().selectList("board.getReportBoardList");
	}
	/**	해당 ID 작성 게시글 수	*/
	public int getBoardCoutById(String id){
		return getSqlSession().selectOne("board.getBoardCoutById", id);
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
	
	public List<BoardCommand> getListById(String id){
		List<BoardCommand> list = null;
		list = getSqlSession().selectList("board.getListById", id);
		return list;
	}
	
	public List<BoardCommand> getListByCategoryIdExBoardNum(List<String> categoryIdList, List<Integer> secretBoardNumList) {
		HashMap<String, Object> categoryIdBoardNumMap = new HashMap<String, Object>();
		categoryIdBoardNumMap.put("categoryIdList", categoryIdList);
		categoryIdBoardNumMap.put("secretBoardNumList", secretBoardNumList);
		return getSqlSession().selectList("board.getListByCategoryIdExBoardNum", categoryIdBoardNumMap);
	}
	
	public List<BoardCommand> getListByExBoardNum(List<Integer> boardNumList){
		return getSqlSession().selectList("board.getListByExBoardNum", boardNumList);

	}
	public List<BoardCommand> getListByBoardNum(List<Integer> boardNumList){
		return getSqlSession().selectList("board.getListByBoardNum", boardNumList);

	}
	
	public List<BoardCommand> getListByCategoryId(List<String> categoryIdList) {
		return getSqlSession().selectList("board.getListByCategoryId", categoryIdList);
	}
	
	// 좋아요	증가
	public int updateRecommendNumByBoardNum (Integer board_num, Integer recommend_num){
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("board_num", board_num);
		map.put("recommend_num", recommend_num);
		return getSqlSession().update("board.updateRecommendNumByBoardNum", map);
	}
	
}
