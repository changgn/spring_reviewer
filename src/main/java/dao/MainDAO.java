package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import command.BoardCommand;

public class MainDAO extends SqlSessionDaoSupport{
	
	public List<BoardCommand> getPageList(HashMap<String, Integer> pageListMap){
		
		return getSqlSession().selectList("main.getPageList",pageListMap);
	}
}
