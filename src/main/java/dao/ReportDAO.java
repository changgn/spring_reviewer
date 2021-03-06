package dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import command.ReportCommand;

public class ReportDAO extends SqlSessionDaoSupport{
	/**	신고받은 게시글 번호, ID 저장	*/
	public int insertReport(ReportCommand rc){
		return getSqlSession().insert("report.insertReportBoard", rc);
	}
	/**	해당 ID의 해당 게시글 신고 여부	*/
	public ReportCommand getSureBoardReportById(ReportCommand rc){
		return getSqlSession().selectOne("report.getSureBoardReportById", rc);
	}
	/**	해당ID의 신고받은 게시글 수	*/
	public int getReportCountById(String id){
		return getSqlSession().selectOne("report.getReportCountById", id);
	}
	/**	게시글 신고 ID 정보	*/
	public List<String> getIdListByBoardNum(Integer board_num){
		return getSqlSession().selectList("report.getIdListByBoardNum", board_num);
	}
	/**	신고 테이블 해당 board_num 데이터 삭제 BY 게시글 삭제	*/
	public int deleteReport(int board_num){
		return getSqlSession().delete("report.delete", board_num);
	}
	/**	신고 테이블 해당 ID 데이터 삭제 BY 게시글 삭제	*/
	public int deleteReportById(String id){
		return getSqlSession().delete("report.deleteById", id);
	}
}
