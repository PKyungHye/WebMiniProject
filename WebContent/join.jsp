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
<style type="text/css">
.conditions {
	padding-top: 5px;
	padding-left: 10px;
	font-size: 10px;
}
.conditions p {
	font-size: 10px;
	color: red;
}
strong {
	padding-left: 10px;
}
</style>
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

		<div class="col-lg-4" style="width: 80%;">
			<!-- 점보트론 -->
			<div class="jumbotron" style="padding-top: 20px;">
				<!-- 로그인 정보를 숨기면서 전송post -->
				<form method="post" action="maincon">
					<input type="hidden" name="command" value="join">
					<h3 style="text-align: center;">회원가입</h3>
					
					<!-- 아이디 -->
					<div class="form-group">
						<div>
						<strong>아이디</strong>
						<input type="text" class="form-control" placeholder="아이디"
							name="userid" id="userid" maxlength="20" onkeyup="checkId()"
							autocomplete="off">
						</div>
						<div class="conditions">
							<p id="idCon">6~20자의 영어 소문자, 숫자 조합</p>
						</div>
					</div>

					<!-- 비밀번호 -->
					<div class="form-group">
						<strong>비밀번호</strong>
						<input type="password" class="form-control" placeholder="비밀번호"
							name="userpw" id="userpw" maxlength="16" onkeyup="checkPw()"
							autocomplete="off">
						<div class="conditions">
							<p id="pwCon">8~16자의 영어 대소문자, 숫자 조합</p>
						</div>
					</div>

					<!-- 비밀번호 확인 -->
					<div class="form-group">
						<strong>비밀번호 확인</strong>
						<input type="password" class="form-control" placeholder="비밀번호 확인"
							id="userpw2" maxlength="16" onkeyup="checkPw2()" autocomplete="off">
						<div class="conditions">
							<p id="pwCon2">비밀번호를 입력하세요.</p>
						</div>
					</div>
					
					<!-- 닉네임 -->
					<div class="form-group">
						<strong>닉네임</strong>
						<input type="text" class="form-control" placeholder="닉네임"
							name="usernickname" id="usernickname" maxlength="15"
							onkeyup="checkNn()" autocomplete="off">
						<div class="conditions">
							<p id="nnCon">2~15자의 영어, 숫자, 한글 가능</p>
						</div>
					</div>

					<input type="submit" id="subBtn" class="btn btn-primary form-control" value="회원가입" disabled>
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
		var idCon = document.getElementById("idCon");
		var pwCon = document.getElementById("pwCon");
		var pwCon2 = document.getElementById("pwCon2");
		var nnCon = document.getElementById("nnCon");
		
		function isSubmitAble() {
			if ( (idCon.style.color == "green") && (pwCon.style.color == "green")
					&& (pwCon2.style.color == "green") && (nnCon.style.color == "green") ) {
				  document.getElementById("subBtn").disabled = false;
			} else {
				  document.getElementById("subBtn").disabled = true;
			}
		}
		
		function checkId() {
			var id = document.getElementById("userid").value;
			
			idCon.style.color="red";
	    	
			if ( ! /^[a-z]/.test(id) ) {
				idCon.innerText="아이디는 영어로 시작해야 합니다.";
			} else if ( ! /[a-z0-9]{6,20}$/.test(id) ) {
				idCon.innerText="6~20자의 영어 소문자, 숫자 조합";
		    } else {
		    	idCon.style.color="green";
		    	idCon.innerText="OK!";
		    }
			isSubmitAble();
		}
	
		function checkPw() {
			var pw = document.getElementById("userpw").value;
			var pw2 = document.getElementById("userpw2").value;
			var id = document.getElementById("userid").value;
			
			pwCon.style.color="red";
			pwCon2.style.color="red";
		    
		    if (pw == "") {
		    	pwCon2.innerText="비밀번호를 입력하세요.";
		    } else if (pw2 != pw) {
		    	pwCon2.innerText="비밀번호가 틀립니다.";
		    } else {
		    	pwCon2.style.color="green";
		    	pwCon2.innerText="OK!";
		    }
		    
		    if ( (! /(?=.*\d{1,20})/g.test(pw)) || (! /(?=.*[a-zA-Z]{1,20})/ig.test(pw)) ) {
		    	pwCon.innerText="영어와 숫자를 모두 포함해야 합니다.";
		    } else if (/(\w)\1\1/.test(pw)) {
		    	pwCon.innerText="같은 문자를 3번 이상 사용하실 수 없습니다.";
		    } else if ( (id != "") && (pw.search(id) > -1) ) {
		    	pwCon.innerText="비밀번호에 아이디가 포함될 수 없습니다.";
		    } else if ( ! /[a-zA-Z0-9]{8,16}$/.test(pw) ) {
		    	pwCon.innerText="8~16자의 영어 대소문자, 숫자 조합";
		    } else {
		    	pwCon.style.color="green";
		    	pwCon.innerText="OK!";
		    }
			isSubmitAble();
		}
	
		function checkPw2() {
			var pw = document.getElementById("userpw").value;
			var pw2 = document.getElementById("userpw2").value;

			pwCon2.style.color="red";
			
		    if (pw == "") {
		    	pwCon2.innerText="비밀번호를 입력하세요.";
		    } else if (pw2 != pw) {
		    	pwCon2.innerText="비밀번호가 틀립니다.";
		    } else {
		    	pwCon2.style.color="green";
		    	pwCon2.innerText="OK!";
		    }
			isSubmitAble();
		}
	
		function checkNn() {
			var nn = document.getElementById("usernickname").value;
			var idReg = /^[가-힣a-zA-Z0-9]+[가-힣a-zA-Z0-9\s]{1,14}$/;

			nnCon.style.color="red";
			
		    if ( ! /^[가-힣a-zA-Z0-9]/.test(nn) ) {
		    	nnCon.innerText="영어 또는 숫자 또는 한글로 시작해야 합니다.";
		    } else if ( ! /[가-힣a-zA-Z0-9\s]{2,15}$/.test(nn) ) {
		    	nnCon.innerText="2~15자의 영어, 숫자, 한글 가능";
		    } else {
		    	nnCon.style.color="green";
		    	nnCon.innerText="OK!";
		    }
			isSubmitAble();
		}
	</script>

</body>
</html>
