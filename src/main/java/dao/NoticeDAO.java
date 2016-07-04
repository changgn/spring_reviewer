package dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import command.NoticeCommand;

@Repository
public class NoticeDAO extends SqlSessionDaoSupport {
	
	public int insert(NoticeCommand command){
		return getSqlSession().insert("notice.add", command);
	}
	public int removeByBoard(NoticeCommand command){
		return getSqlSession().delete("notice.removeByBoard", command);
	}
	public List<NoticeCommand> getListById(String id){
		return getSqlSession().selectList("notice.getListById", id);
	}
	public List<NoticeCommand> getListByBoard(NoticeCommand command){
		return getSqlSession().selectList("notice.getListByBoard", command);
	}
 }