package edu.kh.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.kh.member.model.service.MemberService;
import edu.kh.member.model.vo.Member;

// Controller: 모델(service,DAO ..) 뷰(jsp) 컨트롤러(Servlet==요청/응답을 해줌) 패턴 중 컨트롤러
// 요청에 따른 service를 선택, 응답 화면을 제어하는 클래스

@WebServlet("/login.do") 
// 전달받은 요청을 처리하는 어노테이션
// 기존에 web.xml에 작성했던 url 패턴에 따른 servlet 연결 구문을 간소화 하는 설정
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// doGet() 메소드 : get방식으로 전달되는 요청을 처리하고 응답하는 메소드
		
		// request: 누가, 언제, 요청 주소, session id, parameter 등
		// response: 응답 문서 형식 설정, 문자 인코딩 설정, 클라이언트와 연결된 스트림 정보 등

		
		// POST 전달 방식은 문자 인코딩 형식이 다르기 때문에 인코딩을 변경하는 작업 필요
		request.setCharacterEncoding("UTF-8");

		// 전달 받은 파라미터를 변수에 저장
		// 클라이언트 요청에 담겨져 넘어온 값을 파라미터(parameter) 라고 함.
		// 파라미터 자료형은 String
		String memberId = request.getParameter("memberId");
		String memberPw = request.getParameter("memberPw");

		
		// MemberService 객체를 생성하여 service 호출
		// Service를 호출하기 위해 아이디, 비밀번호가 일치하는 정보를 DB에서 조회
		MemberService service = new MemberService();

		try {
			// service.login에 입력받은 값들을 매개변수로 전달
			Member loginMember = service.login(memberId, memberPw);

			// servlet에서 응답 화면을 만들기 위해 작성한 HTML코드 부분을 JSP로 위임하기
			
			
			// 요청 위임 객체 생성 (RequuestDispacher)
	        // RequestDispatcher : 요청 정보를 발송하는 객체(== 요청 위임 객체)

			// views/loginResult.jsp 파일로 요청을 위임하는 view 객체 생성
			// 경로 지정 시 기준 == WebContent 폴더
			RequestDispatcher view = request.getRequestDispatcher("/views/loginResult.jsp");
			
			// request.setAttribute("key", value)
	        // 요청 정보를 담고 있는 HttpServletRequest 객체에 새로운 정보를 key, value 형태로 추가 
			request.setAttribute("loginMember", loginMember);
			// loginMember와 같은 요청과 관계없는 데이터(가공된 데이터)는 전달되지 않는다.
			// ->결과적으로 HttpServletRequest를 위임받은 jsp파일에 새 객체 정보를 담아서 발송
			
			view.forward(request, response);
			// 요청 위임 객체에 작성된 jsp 파일로 request, response를 전달하여 
	        // 기존 Servlet이 만들어야 했던 HTML 응답화면을 대신 만들어줌
			// -> view(loginResult.jsp)에게 응답과 요청을 주면서 위임을 하는 것이다.


		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
		
		// doPost() 메소드 : post방식으로 전달되는 요청을 처리하고 응답하는 메소드
		// == get, post 관계 없이 처리 방법이 같음
	}

}
