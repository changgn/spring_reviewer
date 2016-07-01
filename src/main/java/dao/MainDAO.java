package dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import command.BoardCommand;

public class MainDAO extends SqlSessionDaoSupport{
	
	public List<BoardCommand> getPageList(){	
		return getSqlSession().selectList("main.getPageList");
	}
	public List<BoardCommand> getMorePageList(int lastBoard_num){	
		return getSqlSession().selectList("main.getMorePageList", lastBoard_num);
	}
	
	public List<BoardCommand> getPageListByContent(String searchContent){
		return getSqlSession().selectList("main.getPageListByContent", searchContent);
	}
	public List<BoardCommand> getMorePageListByContent(String searchContent, int lastBoard_num){		
		HashMap<String, Object> pageListMap = new HashMap<String, Object>();
		pageListMap.put("lastBoard_num", lastBoard_num);
		pageListMap.put("searchContent", searchContent);
		return getSqlSession().selectList("main.getMorePageListByContent", pageListMap);
	}
	
	public List<BoardCommand> getPageListByExBoardNum(List<Integer> secretBoardNumList){
		return getSqlSession().selectList("main.getPageListByExBoardNum", secretBoardNumList);
	}
	public List<BoardCommand> getMorePageListByExBoardNum(List<Integer> secretBoardNumList, int lastBoard_num){
		HashMap<String, Object> pageListMap = new HashMap<String, Object>();
		pageListMap.put("secretBoardNumList", secretBoardNumList);
		pageListMap.put("lastBoard_num", lastBoard_num);
		return getSqlSession().selectList("main.getMorePageListByExBoardNum", pageListMap);
	}
	
	public List<BoardCommand> getPageListByCategoryId(List<String> categoryIdList){
		return getSqlSession().selectList("main.getPageListByCategoryId", categoryIdList);
	}
	public List<BoardCommand> getMorePageListByCategoryId(List<String> categoryIdList, int lastBoard_num){
		HashMap<String, Object> pageListMap = new HashMap<String, Object>();
		pageListMap.put("categoryIdList", categoryIdList);
		pageListMap.put("lastBoard_num", lastBoard_num);
		return getSqlSession().selectList("main.getMorePageListByCategoryId", pageListMap);
	}
	
	public List<BoardCommand> getPageListByCategoryIdExBoardNum(List<String> categoryIdList, List<Integer> secretBoardNumList){
		HashMap<String, Object> pageListMap = new HashMap<String, Object>();
		pageListMap.put("categoryIdList", categoryIdList);
		pageListMap.put("secretBoardNumList", secretBoardNumList);
		return getSqlSession().selectList("main.getPageListByCategoryIdExBoardNum", pageListMap);
	}
	public List<BoardCommand> getMorePageListByCategoryIdExBoardNum(List<String> categoryIdList, List<Integer> secretBoardNumList, int lastBoard_num){
		HashMap<String, Object> pageListMap = new HashMap<String, Object>();
		pageListMap.put("categoryIdList", categoryIdList);
		pageListMap.put("secretBoardNumList", secretBoardNumList);
		pageListMap.put("lastBoard_num", lastBoard_num);
		return getSqlSession().selectList("main.getMorePageListByCategoryIdExBoardNum", pageListMap);
	}
	
	public List<BoardCommand> getPageListByCategoryIdContent(List<String> categoryIdList, String searchContent){
		HashMap<String, Object> pageListMap = new HashMap<String, Object>();
		pageListMap.put("categoryIdList", categoryIdList);
		pageListMap.put("searchContent", searchContent);
		return getSqlSession().selectList("main.getPageListByCategoryIdContent", pageListMap);
	}
	public List<BoardCommand> getMorePageListByCategoryIdContent(List<String> categoryIdList, String searchContent, int lastBoard_num){
		HashMap<String, Object> pageListMap = new HashMap<String, Object>();
		pageListMap.put("lastBoard_num", lastBoard_num);
		pageListMap.put("categoryIdList", categoryIdList);
		pageListMap.put("searchContent", searchContent);
		return getSqlSession().selectList("main.getMorePageListByCategoryIdContent", pageListMap);
	}
	
	public List<BoardCommand> getPageListById(String id){	
		return getSqlSession().selectList("main.getPageListById", id);
	}
	public List<BoardCommand> getMorePageListById(String id, int lastBoard_num){
		HashMap<String, Object> pageListMap = new HashMap<String, Object>();
		pageListMap.put("lastBoard_num", lastBoard_num);
		pageListMap.put("id", id);
		return getSqlSession().selectList("main.getMorePageListById", pageListMap);
	}
	
	public List<BoardCommand> getPageListByBoardNum(List<Integer> boardNumList){	
		return getSqlSession().selectList("main.getPageListByBoardNum", boardNumList);
	}
	public List<BoardCommand> getMorePageListByBoardNum(List<Integer> boardNumList, int lastBoard_num){
		HashMap<String, Object> pageListMap = new HashMap<String, Object>();
		pageListMap.put("lastBoard_num", lastBoard_num);	
		pageListMap.put("boardNumList", boardNumList);
		return getSqlSession().selectList("main.getMorePageListByBoardNum", pageListMap);
	}
}
