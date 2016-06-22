package dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import command.RecommendCommand;

public class RecommendDAO extends SqlSessionDaoSupport{
	
	public int insertRecommend(RecommendCommand command){
		return getSqlSession().insert("recommend.insertRecommend", command);
	}

	public RecommendCommand getRecommend(RecommendCommand command){
		return getSqlSession().selectOne("recommend.getRecommend", command);
	}
	
	public Integer getRecommendCountByRecommendNum(Integer board_num){
		return getSqlSession().selectOne("recommend.getRecommendCountByRecommendNum", board_num);
	}
	
	public List<String> getIdByRecommendNum(int board_num){
		return getSqlSession().selectList("recommend.getIdByRecommendNum", board_num);
	}
	
	public int deleteRecommend(RecommendCommand command){
		return getSqlSession().delete("recommend.deleteRecommend", command);
	}
	
}


