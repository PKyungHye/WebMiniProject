<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="model.domain.UserDTO"%>
<%@ page import="model.UserDAO"%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 뷰포트 -->
<meta name="viewport" content="width=device-width" initial-scale="1">
<!-- 스타일시트 참조  -->
<link rel="stylesheet" href="css/bootstrap.css">
<title>jsp 사용자 정보 수정 사이트</title>
<style type="text/css">
th, td {
	text-align: center;
}
</style>
</head>
<body>
	<!-- 네비게이션  -->
	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expaned="false">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="main.jsp">메인으로</a>
		</div>

		<div class="collapse navbar-collapse"
			id="#bs-example-navbar-collapse-1">

			<ul class="nav navbar-nav">
				<li><a href="buyMain.jsp">이용권 비교</a></li>
				<li><a href="post.jsp">게시판</a></li>
			</ul>
			
			<ul class="nav navbar-nav navbar-right">
			<%
				if (session.getAttribute("userid") == null) {
			%>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">접속하기<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li class="active"><a href="login.jsp">로그인</a></li>
							<li><a href="join.jsp">회원가입</a></li>
						</ul>
					</li>
			<%
				} else {
			%>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false">${sessionScope.userid} 님<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li class="active"><a href="userMyPage.jsp">마이페이지</a></li>
							<li><a href="logoutAction.jsp">로그아웃</a></li>
						</ul>
					</li>
			<%
				}
			%>
			</ul>
		</div>
	</nav>
	
	<!-- 정보 수정 -->
	
	<div class="container">
		<div class="row">
			<form method="post" action="maincon">
				<input type="hidden" name="command" value="userUpdate">
				<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th>아이디</th>
						<th>비밀번호</th>
						<th>닉네임</th>
					</tr>
				</thead>
				<% 
					UserDTO user = UserDAO.getUser((String) session.getAttribute("userid"));
				%>
				<tbody>
					<tr>
						<td>${sessionScope.userid}</td>
						<td><input type="password" name="userpw" value="<%= user.getUserpw() %>"></td>
						<td><input type="text" name="usernickname" value="<%= user.getUsernickname() %>"></td>
					</tr>

					<tr>
						<td colspan="4"><input type="submit" value="수정">
							&nbsp;&nbsp;&nbsp; <input type=button value="취소" OnClick="javascript:history.back(-1)">
					</tr>
				</tbody>
			</table>
			</form>
		</div>
	</div>

	<!-- 애니매이션 담당 JQUERY -->
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<!-- 부트스트랩 JS  -->
	<script src="js/bootstrap.js"></script>
</body>
</html>