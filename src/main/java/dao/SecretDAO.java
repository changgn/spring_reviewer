package dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import command.SecretCommand;

public class SecretDAO extends SqlSessionDaoSupport{
	
	public int insert(SecretCommand command){
		return getSqlSession().insert("secret.add", command);
	}
	
	public List<SecretCommand> getListById(String	id){
		return getSqlSession().selectList("secret.getListById", id);
	}
	
}
