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
</head>
<body>

	<!-- 네비게이션  -->
	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expaned="false">
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
		<div class="col-lg-4"></div>

		<div class="col-lg-4">
			<!-- 점보트론 -->
			<div class="jumbotron" style="padding-top: 20px;">
				<!-- 로그인 정보를 숨기면서 전송post -->
				<form method="post" action="maincon">
					<input type="hidden" name="command" value="join">
					<h3 style="text-align: center;">회원가입</h3>

					<div class="form-group">
						<input type="text" class="form-control" placeholder="아이디" id="userid" name="userid" maxlength="12" required>
						<div><small>아이디 조건부</small></div>
					</div>

					<div class="form-group">
						<input type="password" class="form-control" placeholder="비밀번호" id="userpw" name="userpw" maxlength="16" required>
						<div><small>비밀번호 조건부</small></div>
					</div>
					
					<div class="form-group">
						<input type="text" class="form-control" placeholder="닉네임" id="usernickname" name="usernickname" maxlength="15" required>
						<div><small>닉네임 조건부</small></div>
					</div>
					
					<input type="submit" onclick="check();" class="btn btn-primary form-control"value="회원가입">
					<div style="text-align: center; padding: 6px 12px;" ><a onclick="window.history.back();">취소</a></div>
				</form>
			</div>
		</div>
	</div>

	<!-- 애니매이션 담당 JQUERY -->
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<!-- 부트스트랩 JS  -->
	<script src="js/bootstrap.js"></script>
	<script type="text/javascript">
		function check() {
			var idReg = /^[a-zA-Z]+[a-zA-Z0-9]{5,11}$/;
			var pwReg = /^[a-zA-Z]+[a-zA-Z0-9]{7,15}$/;
			var nnReg = /^[가-힣a-zA-Z0-9]+[가-힣a-zA-Z0-9\s]{1,14}$/;
			var password = document.getElementById("userpw").value;
			
			//아이디 검증
			if ( idReg.test(document.getElementById("userid").value) ) {
				//alert("아이디 성공");
			} else {
				//alert("아이디 실패");
			}
			
			//비밀번호 검증
			if ( pwReg.test(password) ) {
				var checkNumber = password.search(/[0-9]/g);
				var checkEnglish = password.search(/[a-z]/ig);

				if (checkNumber <0 || checkEnglish <0) {
					alert(password + "숫자와 영문자를 혼용하여야 합니다.");
				} else if (/(\w)\1\1/.test(password)) {
					alert(password + '같은 문자를 3번 이상 사용하실 수 없습니다.');
				} else if (password.search(document.getElementById("userid").value) > -1) {
					alert(password + "비밀번호에 아이디가 포함되었습니다.");
				} else {
					alert(password + "비밀번호 성공");
				}
			} else {
				alert(password + " 비밀번호 실패");
			}
			
			//닉네임 검증
			
		}
	</script>

</body>
</html>
