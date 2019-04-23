<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="model.domain.PostDTO"%>
<%@ page import="model.PostDAO"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- 뷰포트 -->
<meta name="viewport" content="width=device-width" initial-scale="1">
<!-- 스타일시트 참조  -->
<link rel="stylesheet" href="css/bootstrap.css">
<title>jsp 게시판 웹사이트</title>
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
							<li class="active"><a href="main.jsp">마이페이지</a></li>
							<li><a href="maincon?command=logout">로그아웃</a></li>
						</ul>
					</li>
			<%
				}
			%>
			</ul>
		</div>
	</nav>

	<!-- 게시판 -->
	<div class="container">
		<div class="row">
			<form method="post" action="maincon">
				
				<%
				if (request.getParameter("comm").equals("write")) {
				%>
					<input type="hidden" name="command" value="postWrite">
					<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
						<thead>
							<tr>
								<th colspan="2" style="background-color: #eeeeee; text-align: center;">글쓰기</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><input type="text" class="form-control" placeholder="글 제목" name="ptitle" maxlength="50"/ required></td>
							</tr>
							<tr>
								<td><textarea class="form-control" placeholder="글 내용" name="pcontent" maxlength="2048" style="height: 350px;" required></textarea></td>
							</tr>
						</tbody>
					</table>
					<a onclick="window.history.back();">취소</a>
					<input type="submit" class="btn btn-primary pull-right" value="글쓰기">
				<%
				} else {
					int postno = 0;
					if (request.getParameter("postno") != null) {
						postno = Integer.parseInt(request.getParameter("postno"));
					}
					
					PostDTO post = PostDAO.getPost(postno);
					
					if (! session.getAttribute("userid").equals(post.getUserid())) {
						PrintWriter script = response.getWriter();
						script.println("<script>");
						script.println("alert('권한이 없습니다.')");
						script.println("location.href = 'post.jsp'");
						script.println("</script>");
					}
					if (postno == 0) {
						PrintWriter script = response.getWriter();
						script.println("<script>");
						script.println("alert('유효하지 않은 글 입니다.')");
						script.println("location.href = 'post.jsp'");
						script.println("</script>");
					}
				%>
					<input type="hidden" name="command" value="postUpdate">
					<input type="hidden" name="postno" value="<%= postno %>">
					<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
						<thead>
							<tr>
								<th colspan="2" style="background-color: #eeeeee; text-align: center;">글 수정</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><input type="text" class="form-control" placeholder="글 제목" name="ptitle" maxlength="50" value="<%= post.getPtitle() %>" required></td>
							</tr>
							<tr>
								<td>
									<textarea class="form-control" placeholder="글 내용" name="pcontent" maxlength="2048" style="height: 350px;" required><%= post.getPcontent() %></textarea>
								</td>
							</tr>
						</tbody>
					</table>
					<a onclick="window.history.back();">취소</a>
					<input type="submit" class="btn btn-primary pull-right" value="수정">
				<%
				}
				%>
				
				
				
			</form>
		</div>
	</div>

	<!-- 애니매이션 담당 JQUERY -->
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<!-- 부트스트랩 JS  -->
	<script src="js/bootstrap.js"></script>
</body>
</html>