package dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import command.BoardCommand;
import command.CommentCommand;

@Repository
public class CommentDAO extends SqlSessionDaoSupport{
	
	public int insert(CommentCommand command){
		return getSqlSession().insert("comment.add", command);
	}

	public List<CommentCommand> getListByBoardNum(Integer board_num){
		return getSqlSession().selectList("comment.getListByBoardNum", board_num);
	}

	//comment 占쏙옙占쏙옙
	public String getCountByBoardNum(Integer board_num){
		return getSqlSession().selectOne("comment.getCountByBoardNum", board_num);
	}
	
	public int removeByCommentNum(Integer board_num) {
		return getSqlSession().delete("comment.removeByCommentNum", board_num);
	}
	
	public List<CommentCommand> getCommentByNum(Integer comment_num){
		
		return getSqlSession().selectOne("comment.getCommentByNum",comment_num);
	}
	
	public int updateByCommentNum(CommentCommand command) {
		return getSqlSession().update("comment.updateByCommentNum", command);
	}
	
	public List<BoardCommand> getPageListByCategoryIdContent(List<String> categoryIdList, String searchContent){
		HashMap<String, Object> pageListMap = new HashMap<String, Object>();
		pageListMap.put("categoryIdList", categoryIdList);
		pageListMap.put("searchContent", searchContent);
		return getSqlSession().selectList("main.getPageListByCategoryIdContent", pageListMap);
	}
	
	//MaxCommentNum �젙蹂� 媛��졇�삤湲�
	public CommentCommand getOne(Integer comment_num) {
		return getSqlSession().selectOne("comment.getOne", comment_num);
	}
	public String getId(Integer comment_num) {
		return getSqlSession().selectOne("comment.getId", comment_num);
	}
	public int getRecentCommentNum() {
		return getSqlSession().selectOne("comment.getRecentCommentNum");
	}

	

	
	
}
