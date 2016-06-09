package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import command.BoardCommand;

@Repository
public class BoardDAO extends SqlSessionDaoSupport{

	//�� �Է��ϱ�
	public int insertBoard(BoardCommand command){
		return getSqlSession().insert("board.add", command);
	}


	//�Խù� ���뺸��
	public BoardCommand selectContent(Integer board_num){
		return getSqlSession().selectOne("board.getByBoardNum", board_num);
	}

	//�Խñ� �����ϱ�
	public int deleteContent(Integer board_num){
		return getSqlSession().delete("board.removeByBoardNum", board_num);
	}
	
	public Integer getRecentBoardNumById(String id){
		return getSqlSession().selectOne("board.getRecentBoardNumById", id);
	}
}
