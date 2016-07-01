package dao;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import command.ReportCommand;

public class ReportDAO extends SqlSessionDaoSupport{
	/**	신고받은 게시글 번호, ID 저장	*/
	public int insertRecommend(ReportCommand rc){
		return getSqlSession().insert("report.insertReportBoard", rc);
	}
	
	/**	해당ID의 신고받은 게시글 수	*/
	public int getReportCountById(String id){
		return getSqlSession().selectOne("report.getReportCountById", id);
	}
}
