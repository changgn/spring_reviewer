package dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import command.FollowCommand;

public class FollowDAO extends SqlSessionDaoSupport{
	/**	to_id�� �ȷο��� ��� ��ȸ	*/
	public List<FollowCommand> fromList(String to_id){ 
		return getSqlSession().selectList("follow.fromList", to_id);
	}
	/**	from_id�� �ȷο��� ��� ��ȸ	*/
	public List<String> toList(String from_id){ 
		return getSqlSession().selectList("follow.toList", from_id);
	}
	/**	�ȷο� �߰�	*/
	public int followInsert(FollowCommand fc){
		return getSqlSession().insert("follow.add", fc);
	}
	/**	�ȷο� ����	*/
	public int remove(FollowCommand fc){
		return getSqlSession().delete("follow.remove", fc);
	}
	/**	�ȷο� ��	*/
	public int countfrom(String id){
		return getSqlSession().selectOne("follow.countfrom", id);
	}
	/**	�ȷ��� ��	*/
	public int countto(String id){
		return getSqlSession().selectOne("follow.countto",id);
	}
}
