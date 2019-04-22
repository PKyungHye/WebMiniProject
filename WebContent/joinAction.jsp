<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.UserDAO" %>
<%@ page import="java.io.PrintWriter" %> <!-- 자바 클래스 사용 -->
<% request.setCharacterEncoding("UTF-8"); %>

<!-- 한명의 회원정보를 담는 user클래스를 자바 빈즈로 사용 / scope:페이지 현재의 페이지에서만 사용-->
<jsp:useBean id="user" class="model.domain.UserDTO" scope="page" />
<jsp:setProperty name="user" property="userid" />
<jsp:setProperty name="user" property="userpw" /> 
<jsp:setProperty name="user" property="usernickname" />

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>jsp 게시판 웹사이트</title>
</head>
<body>
	<%
		int result = UserDAO.join(user);
		PrintWriter script = response.getWriter();
		if (result == 1) {
			session.setAttribute("userid", user.getUserid());
			script.println("<script>location.href='main.jsp';</script>");
		} else { // result == 0
			script.println("<script>alert(\"이미 존재하는 아이디 입니다.\");history.back();</script>");
		}
	%>
</body>
</html>
