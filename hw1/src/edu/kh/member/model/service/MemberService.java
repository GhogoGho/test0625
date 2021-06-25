package edu.kh.member.model.service;

import static edu.kh.member.common.JDBCTemplate.*;

import java.sql.Connection;

import edu.kh.member.model.dao.MemberDAO;
import edu.kh.member.model.vo.Member;

public class MemberService {

	private MemberDAO dao = new MemberDAO();
	// 아이디, 비밀번호를 DB까지 가지고 가서 일치하는 것을 가져오는 서비스

	/** 로그인 Service
	 * @param memberId
	 * @param memberPw
	 * @return loginMember
	 * @throws Exception
	 */
	public Member login(String memberId, String memberPw) throws Exception {
		// DB 연결과정에서 생길 수 있는 예외처리 구문 작성
		
		Connection conn = getConnection();
		// Connection: DB 연결정보를 담고 있는 JDBC객체. java, DB의 통로역할
		// 트랜잭션 처리를 위해 service에서 얻어온다
		
		Member loginMember = dao.login(conn, memberId, memberPw);
		// dao.login으로 매개변수 이용하여 데이터를 넘겨준다.
		
		close(conn);
		// 커넥션 닫음
		
		return loginMember;
		// LoginServlet으로 리턴
	}
	
	
	
	
}
