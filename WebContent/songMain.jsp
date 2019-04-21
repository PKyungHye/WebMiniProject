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
				if (session.getAttribute("id") == null) {
				%>
					<form action="#" method="post">
						ID <input type='text' name='id' placeholder='ID' size='5' required>	
						PW <input type='password' name='pw' placeholder='Password' size='5' required>	
						<button type='submit' name='submit' value='로그인'>로그인</button>
					</form>
				<% //로그인 상태일 때
				} else {
				%>
					<form action="#" method="post">
						${sessionScope.id} 님	
						<button type='submit' name='submit' value='로그아웃'>로그아웃</button>	
						<a href='#' target='frame1'><button type='button'>마이페이지</button></a>
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