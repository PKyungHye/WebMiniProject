<%@ page language="java" contentType="text/html; charset=UTF-8"

    pageEncoding="UTF-8"%>
<%@ page import="model.UserDAO" %>
<%@ page import="java.io.PrintWriter" %> <!-- 자바 클래스 사용 -->
<% request.setCharacterEncoding("UTF-8"); %>

<!-- 한명의 회원정보를 담는 user클래스를 자바 빈즈로 사용 / scope:페이지 현재의 페이지에서만 사용-->
<jsp:useBean id="user" class="model.domain.UserDTO" scope="page" />
<jsp:setProperty name="user" property="userid" />
<jsp:setProperty name="user" property="userpw" /> 

<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>jsp 게시판 웹사이트</title>
</head>
<body>
	<%
		int result = UserDAO.login(user.getUserid(), user.getUserpw());
		PrintWriter script = response.getWriter();
		if (result == 1) {
			session.setAttribute("userid", user.getUserid());
			script.println("<script>location.href='main.jsp';</script>");
		} else if (result == 2) {
			script.println("<script>alert(\"비밀번호가 올바르지 않습니다.\");history.back();</script>");
		} else if (result == 3) {
			script.println("<script>alert(\"존재하지 않는 아이디 입니다.\");history.back();</script>");
		} else {	//result == 0
			script.println("<script>alert(\"오류가 발생하였습니다.\");history.back();</script>");
		}
	%>
</body>
</html>
