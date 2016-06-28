package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import command.BoardCommand;

public class MainDAO extends SqlSessionDaoSupport{
	
	public List<BoardCommand> getPageList(int startBoardNum, int endBoardNum){

		HashMap<String, Integer> pageListMap = new HashMap<String, Integer>();
		pageListMap.put("startBoardNum", startBoardNum);
		pageListMap.put("endBoardNum", endBoardNum);
		
		return getSqlSession().selectList("main.getPageList", pageListMap);
	}
	public List<BoardCommand> getPageListByExBoardNum(HashMap<String, Integer> pageListMap){
		
		return getSqlSession().selectList("main.getPageListByExBoardNum", pageListMap);
	}
}
