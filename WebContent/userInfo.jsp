<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="model.domain.UserDTO"%>
<% String url = application.getContextPath() + "/"; %> 
   
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>
	<%
	if (session.getAttribute("userInfo") != null) {
	%>
		${sessionScope.userInfo.usernickname} 님 안녕하세요
	<%
	} else {
	%>
		오류: ${sessionScope.errorMsg}
	<% } %>
</body>
</html>