package dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import command.FollowCommand;

public class FollowDAO extends SqlSessionDaoSupport{
	/**	to_id에게 팔로우한 목록 조회	*/
	public List<String> fromList(String to_id){ 
		return getSqlSession().selectList("follow.fromList", to_id);
	}
	/**	from_id가 팔로우한 목록 조회	*/
	public List<String> toList(String from_id){ 
		return getSqlSession().selectList("follow.toList", from_id);
	}
	/**	팔로우 추가	*/
	public int followInsert(FollowCommand fc){
		return getSqlSession().insert("follow.add", fc);
	}
	/**	팔로우 삭제	*/
	public int remove(FollowCommand fc){
		return getSqlSession().delete("follow.remove", fc);
	}
	/**	팔로워 수	*/
	public int countfrom(String id){
		return getSqlSession().selectOne("follow.countfrom", id);
	}
	/**	팔로잉 수	*/
	public int countto(String id){
		return getSqlSession().selectOne("follow.countto",id);
	}
}
