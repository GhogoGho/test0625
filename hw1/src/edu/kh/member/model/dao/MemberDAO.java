package edu.kh.member.model.dao;

import static edu.kh.member.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import edu.kh.member.model.vo.Member;

public class MemberDAO {
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private Properties prop = null; 
	
	public MemberDAO() {
		
		try {
			prop = new Properties();
			
			String filePath 
			= MemberDAO.class.getResource("/edu/kh/member/sql/member-query.xml").getPath();     
		
			
			prop.loadFromXML( new FileInputStream( filePath ) );
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/** 로그인 DAO
	 * @param conn
	 * @param memberId
	 * @param memberPw
	 * @return loginMember
	 * @throws Exception
	 */
	public Member login(Connection conn, String memberId, String memberPw) throws Exception {
		Member loginMember = null;
		
		try {
			// login이라는 키 값으로 SQL구문을 얻어온다.
			String sql = prop.getProperty("login");
			
			// 위치홀더를 사용하기 위해선 prepareStatement 사용해야 한다.
			// prepareStatement: 커넥션에서 생성하면서 동시에 SQL문이 DB에 전송된다.
			pstmt = conn.prepareStatement(sql);
			
			// 위치홀더에 값을 세팅한다.
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPw);
			
			// SQL 수행 후에 결과를 rs에 반환 받는다.
			// executeQuery: SELECT구문을 수행할 때 사용하며, 
			// 수행결과로 ResultSet에 결과값 담아서 반환할 수 있다. 
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				// 다음 조회 결과가 있을 경우 
				// 컬럼에 있는 값들을 각 변수에 저장하고
				// 객체를 생성하여 리턴
				// 리턴 직전 사용한 JDBC 객체 자원은 다시 반환(메모리의 확보)
				
				int memNo = rs.getInt("MEM_NO");
				String id = rs.getString("MEM_ID");
				String memNm = rs.getString("MEM_NM");
				String memPhone = rs.getString("MEM_PHONE");
				char memGender = rs.getString("MEM_GENDER").charAt(0);
				Date joinDate = rs.getDate("JOIN_DATE");
			
				loginMember = new Member(id, memNm, memPhone, 
										memGender, joinDate); 
				
				loginMember.setMemNo(memNo); // 생성자 없이 get/setter를 이용해서 값을 전달
			}
			
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return loginMember;
	}
}
