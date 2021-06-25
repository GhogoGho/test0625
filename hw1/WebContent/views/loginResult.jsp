<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@page import="edu.kh.member.model.vo.Member"%>
<!-- page: 현재의 jsp 페이지를 컨테이너에서 처리하는데 필요한 각 속성을 기술하는 부분 --> 

<% 
	// 스크립틀릿: jsp에서 java코드 작성이 가능한 영역
	Member loginMember = (Member)request.getAttribute("loginMember");
	// 오브젝트 타입으로 반환함. 다운 캐스팅
%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>로그인 결과 화면</title>
</head>
<body>
	
	<% if(loginMember != null){ %> <!-- loginMember 값이 비어있지 않을 때 실행 -->
		<h1>로그인 성공</h1>
		
		<!--
         	파라미터(Parameter) : 클라이언트가 요청할 때 전달한 값
         	속성(Attribute) : Servlet에서 가공을 통해 request에 추가한 값
        -->
		
		<!-- EL형태로 작성됨 -->
		<!-- JSP 표현식을 좀 더 효율적으로 간단하게 사용하는 방법-->

		<ul>
			<li> 입력한 아이디 : ${param.memberId }</li>
			 <!-- request에 있는 전달 받은 파라미터 중 memberId의 값을 출력 -->
			<li> 입력한 비밀번호 : ${param.memberPw }</li>
		</ul>
		
		<ul>
			<li> 회원 번호 : ${loginMember.memNo }</li>
			<!-- loginMember에 저장되어있는 memNo 값을 얻어와 출력  -->
			<li> 아이디 : ${loginMember.memId }</li>
			<li> 이름 : ${loginMember.memNm }</li>
			<li> 전화번호 : ${loginMember.memPhone }</li>
			<li> 성별 : ${loginMember.memGender }</li>
			<li> 전화번호 : ${loginMember.joinDate }</li>
		</ul>


	<% } else {%>
		<h1>아이디 또는 비밀번호가 일치하지 않습니다.</h1>
	
	<% } %>
	
	
</body>
</html>