package dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import command.BoardCommand;

public class MainDAO extends SqlSessionDaoSupport{
	
	public List<BoardCommand> getPageList(int startBoardNum, int endBoardNum){

		HashMap<String, Integer> pageListMap = new HashMap<String, Integer>();
		pageListMap.put("startBoardNum", startBoardNum);
		pageListMap.put("endBoardNum", endBoardNum);
		
		return getSqlSession().selectList("main.getPageList", pageListMap);
	}
	public List<BoardCommand> getPageListByExBoardNum(List<Integer> secretBoardNumList, int startBoardNum, int endBoardNum){

		HashMap<String, Object> pageListMap = new HashMap<String, Object>();
		pageListMap.put("startBoardNum", startBoardNum);
		pageListMap.put("endBoardNum", endBoardNum);
		pageListMap.put("secretBoardNumList", secretBoardNumList);
		return getSqlSession().selectList("main.getPageListByExBoardNum", pageListMap);
	}
}
