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
	public List<NoticeCommand> getListById(String id){
		return getSqlSession().selectList("notice.getListById", id);
	}

 }