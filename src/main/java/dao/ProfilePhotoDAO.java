package dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import command.ProfilePhotoCommand;

@Repository
public class ProfilePhotoDAO extends SqlSessionDaoSupport{
	
	public int insert(ProfilePhotoCommand command){
		return getSqlSession().insert("profilephoto.add", command);
	}
	public int modify(ProfilePhotoCommand command){
		return getSqlSession().update("profilephoto.modify", command);
	}
	public List<ProfilePhotoCommand> getlist() {
		return getSqlSession().selectList("profilephoto.getlist");
	}
	
	public ProfilePhotoCommand getOneById(String id) {
		return getSqlSession().selectOne("profilephoto.getOneById", id);
	}
}