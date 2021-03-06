package edu.kh.member.common;


import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {
	/* DB 연결, JDBC 자원 반환 등의 JDBC관련 공통 내용을 모아둔 클래스
	 * getConnection() : 커넥션을 반환하는 메소드
	 * close(stmt | pstmt | rs | conn) : 자원 반환 메소드
	 * commit() / rollback() : 트랜잭션 처리용 메소드
	 * */
	
	private static Connection conn = null;
	
	// DB 연결을 위한 커넥션 반환 메소드
	public static Connection getConnection() {
		
		try {
			
			if( conn == null || conn.isClosed() ) { 
				// isClosed() : 자원이 반환되어 있으면 true, 아니면 false
				
				
				// 외부 파일에서 정보를 읽어와 이를 저장할 Properties 객체 생성
				Properties prop = new Properties();
				// Properties :  K, V가 String으로 제한된 Map
				// --> 파일 입출력에 특화되어 있음.
				
				
				// * 실행의 주체가 자바가 아닌 웹(서버)에 있기 때문에 웹 프로젝트에서 경로를 읽어들이는 방법이 다르다.

				// 웹 프로젝트에서 원하는 위치에 있는 파일의 경로를 찾는 방법
				String filePath = JDBCTemplate.class.getResource("/edu/kh/member/sql/driver.xml").getPath();
				// JDBCTemplate 라는 클래스 코드가 모여있는 폴더 안에서 driver.xml의 실제 경로를 얻어옴
				// 정확한 경로-> Navigator -> WebContent -> WEB-INF -> classes -> ...common 
				
				// driver.xml 파일을 읽어와서 prop에 저장
				prop.loadFromXML( new FileInputStream(filePath) );
				// FileInputStream:  InputStream 클래스를 상속받은 자식 클래스, 하드 디스크 상에 있는 파일로부터 바이트 단위의 입력을 받는 클래스다. 
				// 출발 지점과 도착 지점을 연결하는 통로(스트림)을 생성 데이터를 읽고 원하는 형태로 변환하기 위한 기능을 제공해준다.
				
				// * 즉, 웹 프로젝트에서 지정된 경로에 있는 xml 파일을 읽어와 Properties 형태로 변형하라는 의미
				
				// 중간 확인
				//System.out.println(prop);
				
				
				// 새로운 커넥션 생성
				Class.forName( prop.getProperty("driver") );
				String url = prop.getProperty("url");
				String user = prop.getProperty("user");
				String pass = prop.getProperty("pass");
				conn = DriverManager.getConnection(url, user, pass);
				
				conn.setAutoCommit(false); // SQL 구문 수행 후 자동 Commit 기능 끔
				
				// 드라이버, url, 아이디, 비밀번호는 바뀔 가능성이 많아서
				// 바뀔 때 마다 자바 코드를 다시 컴파일 하는 것은 너무 비효율적임.
				// --> 외부 파일에 내용을 작성해 읽어오는 방법으로 문제 해결 가능
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	
	// Connection 반환 메소드
	public static void close(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				// conn이 참조하는 Connection 객체가 있고
				// 그 객체가 반환되지 않았을 때 
				conn.close();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	// Statement 반환 메소드 + (다형성을 이용하여 PreparedStatement도 같이 반환 가능)
	public static void close(Statement stmt) {
		try {
			if(stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// ResultSet 반환 메소드 
	public static void close(ResultSet rs) {
		try {
			if(rs != null && !rs.isClosed()) {
				rs.close();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	// commit용 메소드
	public static void commit(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				// conn이 참조하는 Connection 객체가 있고
				// 그 객체가 반환되지 않았을 때 
				conn.commit();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// rollback용 메소드
	public static void rollback(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) {
				// conn이 참조하는 Connection 객체가 있고
				// 그 객체가 반환되지 않았을 때 
				conn.rollback();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
}





