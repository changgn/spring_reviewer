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
	
	
	
	public int inputPro(MemberCommand memberInfo) {
		
		
		return getSqlSession().insert("member.add", memberInfo);
			
		
		
	}
	
	public int count(){
		return getSqlSession().selectOne("member.count");
	}
	
	public List<MemberCommand> getList(){
		return getSqlSession().selectList("member.getList");
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
