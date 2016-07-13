package dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import command.MemberCommand;
import command.MemberRecommendDeleteCommand;


@Repository
public class MemberDAO extends SqlSessionDaoSupport{
	
	/**	관리자에서 회원 탈퇴	*/
	public int MemberOut(String id){
		return getSqlSession().delete("member.output", id);
	}
	/**	ID 목록	*/
	public List<String> getIdList(){
		return getSqlSession().selectList("member.getIdList");
	}
	/**	회원 수	*/
	public int count(){
		return getSqlSession().selectOne("member.getMemberCount");
	}
/**	회원 목록	*/
		public List<MemberCommand> getList(){
			return getSqlSession().selectList("member.getList");
		}
	/*	DESC 정렬	*/
		// 회원 아이디
		public List<MemberCommand> getListById_DESC(){
			return getSqlSession().selectList("member.getListById_DESC");
		}
		// 회원가입일
		public List<MemberCommand> getListByRegDate_DESC(){
			return getSqlSession().selectList("member.getListByRegDate_DESC");
		}
		// 추천수
		public List<MemberCommand> getListByRecommend_DESC(){
			return getSqlSession().selectList("member.getListByRecommend_DESC");
		}
	/*	ASC 정렬	*/
		// 회원 아이디
		public List<MemberCommand> getListById_ASC(){
			return getSqlSession().selectList("member.getListById_ASC");
		}
		// 회원가입일
		public List<MemberCommand> getListByRegDate_ASC(){
			return getSqlSession().selectList("member.getListByRegDate_ASC");
		}
		// 추천수
		public List<MemberCommand> getListByReccomend_ASC(){
			return getSqlSession().selectList("member.getListByRecommend_ASC");
		}
	
	public void delete(HashMap<String, String> map){
		getSqlSession().delete("member.delete", map);
	}

	public String getPasswdById(String id){
		return getSqlSession().selectOne("member.getPasswdById", id);
	}

	public MemberCommand loginPro(String id) {
		return getSqlSession().selectOne("member.loginPro",id);
	}

	public MemberCommand modifyForm(String id) {
		return getSqlSession().selectOne("member.modifyForm",id);
	}

	public int modifyPro(MemberCommand memberInfo) {
		return getSqlSession().update("member.modifyPro", memberInfo);
	}

	public int inputPro(MemberCommand memberInfo) {
		return getSqlSession().insert("member.add", memberInfo);
	}

	public String idCheck(String id){
		return getSqlSession().selectOne("member.idCheck", id);
	}
	
	public List<MemberCommand> idSearch(String phone_num) {

		return getSqlSession().selectList("member.idSearch", phone_num);
	}

	public MemberCommand pwSearch(MemberCommand memberInfo){
		return getSqlSession().selectOne("member.pwSearch", memberInfo);
	}
	
	public int updateIncreaseRecommendNum(String id){
		return getSqlSession().update("member.updateIncreaseRecommendNum", id);
	}
	
	public int updateDecreaseRecommendNum(String id){
		return getSqlSession().update("member.updateDecreaseRecommendNum", id);
	}
	
	/**	추천수 변경 By 게시글 삭제	*/
	public int updateDecreaseRecommendNumByDeleteBoard(MemberRecommendDeleteCommand mrdc){
		return getSqlSession().update("member.updateDecreaseRecommendNumByDeleteBoard", mrdc);
	}
}
