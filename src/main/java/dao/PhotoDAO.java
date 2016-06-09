package dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import command.PhotoCommand;

@Repository
public class PhotoDAO extends SqlSessionDaoSupport{
	
	public int insert(PhotoCommand command){
		return getSqlSession().insert("photo.add", command);
	}
	
	public List<PhotoCommand> getListByBoardNum(Integer board_num) {
		return getSqlSession().selectList("photo.getListByBoardNum", board_num);
	}
	
	public PhotoCommand getOneByBoardNum(Integer board_num) {
		return getSqlSession().selectOne("photo.getOneByBoardNum", board_num);
	}
}