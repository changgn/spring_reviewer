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
	public List<BoardCommand> getPageListByContent(String searchContent, int startBoardNum, int endBoardNum){

		HashMap<String, Object> pageListMap = new HashMap<String, Object>();
		pageListMap.put("startBoardNum", startBoardNum);
		pageListMap.put("endBoardNum", endBoardNum);
		pageListMap.put("searchContent", searchContent);
		
		return getSqlSession().selectList("main.getPageListByContent", pageListMap);
	}
	public List<BoardCommand> getMorePageListByContent(String searchContent, int lastBoard_num, int startBoardNum, int endBoardNum){
		System.out.println("lastBoard_num : "  + lastBoard_num);
		HashMap<String, Object> pageListMap = new HashMap<String, Object>();
		pageListMap.put("startBoardNum", startBoardNum);
		pageListMap.put("endBoardNum", endBoardNum);
		pageListMap.put("lastBoard_num", lastBoard_num);
		pageListMap.put("searchContent", searchContent);
		
		return getSqlSession().selectList("main.getMorePageListByContent", pageListMap);
	}
	public List<BoardCommand> getPageListByExBoardNum(List<Integer> secretBoardNumList, int startBoardNum, int endBoardNum){

		HashMap<String, Object> pageListMap = new HashMap<String, Object>();
		pageListMap.put("startBoardNum", startBoardNum);
		pageListMap.put("endBoardNum", endBoardNum);
		pageListMap.put("secretBoardNumList", secretBoardNumList);
		return getSqlSession().selectList("main.getPageListByExBoardNum", pageListMap);
	}
	public List<BoardCommand> getPageListByCategoryId(List<String> categoryIdList, int startBoardNum, int endBoardNum){

		HashMap<String, Object> pageListMap = new HashMap<String, Object>();
		pageListMap.put("startBoardNum", startBoardNum);
		pageListMap.put("endBoardNum", endBoardNum);
		pageListMap.put("categoryIdList", categoryIdList);
		return getSqlSession().selectList("main.getPageListByCategoryId", pageListMap);
	}
	public List<BoardCommand> getPageListByCategoryIdExBoardNum(List<String> categoryIdList, List<Integer> secretBoardNumList, int startBoardNum, int endBoardNum){

		HashMap<String, Object> pageListMap = new HashMap<String, Object>();
		pageListMap.put("startBoardNum", startBoardNum);
		pageListMap.put("endBoardNum", endBoardNum);
		pageListMap.put("categoryIdList", categoryIdList);
		pageListMap.put("secretBoardNumList", secretBoardNumList);
		return getSqlSession().selectList("main.getPageListByCategoryIdExBoardNum", pageListMap);
	}
	public List<BoardCommand> getPageListByCategoryIdContent(List<String> categoryIdList, String searchContent, int startBoardNum, int endBoardNum){

		HashMap<String, Object> pageListMap = new HashMap<String, Object>();
		pageListMap.put("startBoardNum", startBoardNum);
		pageListMap.put("endBoardNum", endBoardNum);
		pageListMap.put("categoryIdList", categoryIdList);
		pageListMap.put("searchContent", searchContent);
		return getSqlSession().selectList("main.getPageListByCategoryIdContent", pageListMap);
	}
	public List<BoardCommand> getMorePageListByCategoryIdContent(List<String> categoryIdList, String searchContent, int lastBoard_num, int startBoardNum, int endBoardNum){

		HashMap<String, Object> pageListMap = new HashMap<String, Object>();
		pageListMap.put("startBoardNum", startBoardNum);
		pageListMap.put("endBoardNum", endBoardNum);
		pageListMap.put("lastBoard_num", lastBoard_num);
		pageListMap.put("categoryIdList", categoryIdList);
		pageListMap.put("searchContent", searchContent);
		return getSqlSession().selectList("main.getMorePageListByCategoryIdContent", pageListMap);
	}
}
