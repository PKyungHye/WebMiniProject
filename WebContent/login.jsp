<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- 뷰포트 -->
<meta name="viewport" content="width=device-width" initial-scale="1">

<!-- 스타일시트 참조  -->
<link rel="stylesheet" href="css/bootstrap.min.css">
<title>jsp 게시판 웹사이트</title>
<style>
strong {
	padding-left: 10px;
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
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false">접속하기<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li class="active"><a href="login.jsp">로그인</a></li>
						<li><a href="join.jsp">회원가입</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</nav>

	<!-- 로긴폼 -->
	<div class="container">
		<div class="col-lg-4" style="width: 80%;">
			<div class="jumbotron" style="padding-top: 20px;">
				<form method="post" action="maincon">
					<input type="hidden" name="command" value="login">
					<h3 style="text-align: center;">로그인화면</h3>

					<div class="form-group">
						<strong>아이디</strong>
						<input type="text" class="form-control" placeholder="아이디"
							name="userid" maxlength="20" required>
					</div>
					
					<div class="form-group">
						<strong>비밀번호</strong>
						<input type="password" class="form-control" placeholder="비밀번호"
							name="userpw" maxlength="20" required>
					</div>
					
					<input type="submit" class="btn btn-primary form-control"
						value="로그인">
					<div style="text-align: center; padding: 6px 12px;" ><a onclick="window.history.back();">취소</a></div>
				</form>
			</div>
		</div>
	</div>

	<!-- 애니매이션 담당 JQUERY -->
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>

	<!-- 부트스트랩 JS  -->
	<script src="js/bootstrap.js"></script>

</body>
</html>