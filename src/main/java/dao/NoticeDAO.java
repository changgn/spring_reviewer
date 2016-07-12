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
	public int insert2(NoticeCommand command){
		return getSqlSession().insert("notice.add2", command);
	}
	public int removeByBoard(NoticeCommand command){
		return getSqlSession().delete("notice.removeByBoard", command);
	}
	public int removeByMember(NoticeCommand command){
		return getSqlSession().delete("notice.removeByMember", command);
	}
	public int updateReadByNoticeNum(int notice_num){
		return getSqlSession().update("notice.updateReadByNoticeNum", notice_num);
	}
	public int getNoReadCountById(String id){
		return getSqlSession().selectOne("notice.getNoReadCountById", id);
	}
	public List<NoticeCommand> getListById(String id){
		return getSqlSession().selectList("notice.getListById", id);
	}
	public List<NoticeCommand> getListByBoard(NoticeCommand command){
		return getSqlSession().selectList("notice.getListByBoard", command);
	}
	public List<NoticeCommand> getListByMember(NoticeCommand command){
		return getSqlSession().selectList("notice.getListByMember", command);
	}
 }