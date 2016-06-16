package dao;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import command.RecommendCommand;

public class RecommendDAO extends SqlSessionDaoSupport{
	
	public int insertBoard(RecommendCommand command){
		return getSqlSession().insert("recommend.insertRecommend", command);
	}

	public RecommendCommand getRecommend(RecommendCommand command){
		return getSqlSession().selectOne("recommend.getRecommend", command);
	}
	
	public int deleteRecommend(RecommendCommand command){
		return getSqlSession().delete("recommend.deleteRecommend", command);
	}
}


