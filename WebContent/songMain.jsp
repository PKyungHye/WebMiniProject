<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인화면</title>
<style>
body {
	margin: 0px;
}
a:link {
	text-decoration: none;
	color: black;
}
a:visited {
	color: black;
}
a:hover, a:active {
	color: gray;
}
.topnav {
	width: 100%;
}
h2 {
	text-align: center;
}
.nav_container {
	width: 100%;
	position:absolute;
	top: 70px;
}
.topnav {
	width: 97%;
}
.frame_container {
	width: 100%;
	position:absolute;
	top:110px;
	bottom:0;
	height:auto;
}
</style>
</head>
<body>
	<h2><a class="main_link" href='dummy.html' target="main_frame">메인</a></h2>
	<div class="nav_container" align="center">
		<div class ="topnav">
			<div style="float: left;">
				<a href="post/postMain.html" target="main_frame">게시판</a>
				<a href="buy/buyMain.html" target="main_frame">이용권 비교</a>
			</div>
			<div style='float:right;'>
				<%//미로그인 상태일 때
				if (session.getAttribute("userid") == null) {
				%>
					<a href='songLogin.html' target='main_frame'><button>로그인</button></a>
					<a href='signupForm.html' target='main_frame'><button>회원가입</button></a>
				<% //로그인 상태일 때
				} else {
				%>
					<form action="#" method="post">
						${sessionScope.id} 님	
						<button type='submit' name='submit' value='로그아웃'>로그아웃</button>	
						<a href='#' target='main_frame'><button type='button'>마이페이지</button></a>
					</form>
				<% } %>
			</div>
		</div>
	</div>
	<div class="frame_container" align="center">
		<iframe height="100%" width="97%" src="dummy.html" name="main_frame" style="border:none;"></iframe>
	</div>
</body>
</html>