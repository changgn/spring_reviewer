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
	/**	회원 모든 정보 목록	*/
	public List<MemberCommand> getList(){
		return getSqlSession().selectList("member.getList");
	}
	
	public void delete(HashMap<String, String> map){

		getSqlSession().delete("member.delete", map);
	}

	public String getPasswdById(String id){

		String passwd = null;

		try {
			passwd = getSqlSession().selectOne("member.getPasswdById", id);

		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return passwd;
	}

	public MemberCommand loginPro(String id) {
		MemberCommand memInfo = null;

		try {
			memInfo = getSqlSession().selectOne("member.loginPro",id);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return memInfo;

	}

	public MemberCommand modifyForm(String id) {
		MemberCommand memInfo = null;

		try {
			memInfo = getSqlSession().selectOne("member.modifyForm",id);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return memInfo;
	}

	public int modifyPro(MemberCommand memberInfo) {
		int n = 0;

		try {

			n = getSqlSession().update("member.modifyPro", memberInfo);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return n;
	}

	public int inputPro(MemberCommand memberInfo) {

		return getSqlSession().insert("member.add", memberInfo);
	}

	public String idCheck(String id){
		String ch=null;

		try{

			ch = getSqlSession().selectOne("member.idCheck", id);

		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return ch;
	}

	private static MemberDAO instance = new MemberDAO();

	public static MemberDAO getInstance() {
		return instance;
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
	
	public int updateDecreaseRecommendNumByDeleteBoard(MemberRecommendDeleteCommand mrdc){
		return getSqlSession().update("member.updateDecreaseRecommendNumByDeleteBoard", mrdc);
	}
}
