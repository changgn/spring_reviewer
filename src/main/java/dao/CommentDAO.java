package dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import command.CommentCommand;

@Repository
public class CommentDAO extends SqlSessionDaoSupport{
	
	public int insert(CommentCommand command){
		return getSqlSession().insert("comment.add", command);
	}

	public List<CommentCommand> getListByBoardNum(Integer board_num){
		return getSqlSession().selectList("comment.getListByBoardNum", board_num);
	}

	//comment ����
	public String getCountByBoardNum(Integer board_num){
		return getSqlSession().selectOne("comment.getCountByBoardNum", board_num);
	}
	
	public int removeByCommentNum(Integer comment_num) {
		return getSqlSession().delete("comment.removeByCommentNum", comment_num);
	}
	
	//comment 정보 가져오기
	public String getRecentOne() {
		return getSqlSession().selectOne("comment.getRecentOne");
	}
}
