package dao;

import java.sql.Date;
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import command.MemberCommand;

@Repository
public class MemberDAO extends SqlSessionDaoSupport{

	public MemberCommand deleteCf(String id){
		
		MemberCommand memInfo = null;
		
		try {
			
			memInfo = getSqlSession().selectOne("member.deleteCf",id);
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return memInfo;
		
	}

	public MemberCommand pwSearch(MemberCommand memberInfo){
		
		MemberCommand memInfo = null;
		
		try {
			memInfo = getSqlSession().selectOne("member.pwSearch", memberInfo);
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return memInfo;
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
	
	public void inputPro(MemberCommand memberInfo) {

		
		try {
			int n = getSqlSession().insert("member.add", memberInfo);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	// 占쏙옙占� 회占쏙옙 占쏙옙占�
		public List<MemberCommand> getMemberListVo(){
			List<MemberCommand> memberList = null;
			
			try{
				memberList = getSqlSession().selectList("member.getList");
				
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return memberList;
		}
		
		// 회占쏙옙占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙트 占싱깍옙
		public List<Date> getRegDate(List<String> id){
			List<Date> RegDateList = null;
			
			try{
				
				RegDateList = getSqlSession().selectList("member.getRegList", id);
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			return RegDateList;
		}
		
		// 占쏙옙천占쏙옙 占쏙옙占쏙옙트 占싱깍옙
		public List<Integer> getRecommedNum(List<String> id){
			List<Integer> RecommendNumList = null;
			
			try{
				
				RecommendNumList = getSqlSession().selectList("member.getRecList", id);
				
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			return RecommendNumList;
		}
		
		public int count(){
			Integer count = null;
			
			try{
				
				count = Integer.valueOf((String) getSqlSession().selectOne("member.count"));
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			return count;
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

		public List<String> idSearch(String phone_num) {
			
			List<String> memCommand = null;
			
			try {
				memCommand = getSqlSession().selectOne("member.pwSearch", phone_num);
				
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
			return memCommand;
		}
}
