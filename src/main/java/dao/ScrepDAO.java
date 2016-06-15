package dao;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;


import command.ScrepCommand;

@Repository
public class ScrepDAO extends SqlSessionDaoSupport {
	
//수정
	
	public void Screp(String id){
		
		int success;
		ScrepCommand Command = null;
		
		
		success = getSqlSession().insert("screp.add", Command);
		
		if(success > 0) {
			System.out.println("insert ok"); //출력 확인용
		}
		else {
			
			System.out.println("insert f");  //출력 확인용
		}
		
		success = getSqlSession().delete("screp.remove", id);
		
		System.out.println("delete 처리건수:" + success); //출력 확인용
		
		
	}
}