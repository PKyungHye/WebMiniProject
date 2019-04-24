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
<style>
table .songlist {
    width: 100%;
    text-align: left;
}

.dropdown {
    width: 100%;
  position: relative;
  display: inline-block;
}

.dropdown-content {
    width: 100%;
  display: none;
  position: absolute;
  background-color: #f1f1f1;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  z-index: 1;
}

.dropdown-content a {
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
}

.dropdown-content a:hover {background-color: #ddd;}
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
								<td><input type="text" class="form-control" placeholder="글 제목" name="ptitle" maxlength="50" required></td>
							</tr>
							<tr>
								<td>
									<div>
										<div class="dropdown">
											<input type="text" id="searchBar2" class="form-control"
												placeholder="노래 검색" maxlength="50" onkeyup="loadDoc2()">
											<div id="viewResult2" class="dropdown-content"></div>
										</div>
										<div id="viewSelect" style="display: none;">
											<span style="float: left">선택한 곡:</span>
											<a  style="float: right;" onclick="reset()">취소</a>
											<div id="viewResult"></div>
										</div>
									</div>
								</td>
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
					
					PostDTO p = PostDAO.getPost(postno);
					
					if (! session.getAttribute("userid").equals(p.getUserid())) {
				%>
						<script>
							alert('권한이 없습니다.');
							location.href = 'post.jsp';
						</script>
				<%
					}
					if (postno == 0) {
				%>
						<script>
							alert('유효하지 않은 글 입니다.');
							location.href = 'post.jsp';
						</script>
				<%
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
								<td><input type="text" class="form-control" placeholder="글 제목" name="ptitle" maxlength="50" value="<%= p.getPtitle() %>" required></td>
							</tr>
							<tr>
								<td>
									<div>
										<div class="dropdown">
											<input type="text" id="searchBar2" class="form-control" style="display: none;"
												placeholder="노래 검색" maxlength="50" onkeyup="loadDoc2()">
											<div id="viewResult2" class="dropdown-content"></div>
										</div>
										<div id="viewSelect" style="display: block;">
											<span style="float: left">선택한 곡:</span>
											<a  style="float: right;" onclick="reset()">취소</a>
											<div id="viewResult">
												<input type='hidden' name='surl' value="<%= p.getSurl() %>">
												<input type='hidden' name='stitle' value="<%= p.getStitle() %>">
												<input type='hidden' name='sartist' value="<%= p.getSartist() %>">
												<input type='hidden' name='salbum' value="<%= p.getSalbum() %>">
												<table class='songlist'>
													<tr>
														<td><img src='<%= p.getSurl() %>'height='30' width='30'></td>
														<td width='33%'><strong><%= p.getStitle() %></strong></td>
														<td width='30%'><%= p.getSartist() %></td>
														<td width='30%'><%= p.getSalbum() %></td>
													</tr>
												</table>
											</div>
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<td>
									<textarea class="form-control" placeholder="글 내용" name="pcontent" maxlength="2048" style="height: 350px;" required><%= p.getPcontent() %></textarea>
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
	<script type="text/javascript">

		function chooseSong(url, title, artist, album) {
			document.getElementById('searchBar2').value = "";
	    	document.getElementById("viewResult2").style.display = "none";
	    	document.getElementById("searchBar2").style.display = "none";
	    	document.getElementById("viewSelect").style.display = "block";
			document.getElementById("viewResult").innerHTML = "<table class='songlist'><tr><td><img src='" + url + "'height='30' width='30'></td><td width='33%'><strong>" + title + "</strong></td><td width='30%'>" + artist + "</td><td width='30%'>" + album + "</td></tr></table>"
															+ "<input type='hidden' name='surl' value='" + url + "'>"
															+ "<input type='hidden' name='stitle' value='" + title + "'>"
															+ "<input type='hidden' name='sartist' value='" + artist + "'>"
															+ "<input type='hidden' name='salbum' value='" + album + "'>";
		}
		
		function loadDoc2() {
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
			    if (this.readyState == 4 && this.status == 200) {
			    	document.getElementById("viewResult2").style.display = "block";
			    	var dataList = JSON.parse(this.responseText);
			    	var viewList = "<a><small>검색 결과는 10곡까지 제공합니다.(검색 결과 제공: Bugs)</small></a>";
			    	if (dataList.length != 0) {
				    	dataList.forEach(dropBox);
		    		} else {
		    			viewList += "<a><small>검색 결과가 없습니다.</small></a>";
		    		}
					document.getElementById("viewResult2").innerHTML = viewList;
			    	
					
			    	function dropBox(item, index) {
			    		viewList += "<a onclick='chooseSong(\"" + item.thumbUrl + "\",\"" + item.title + "\",\"" + item.artist + "\",\"" + item.album + "\")'><table class='songlist'><tr><td><img src='" + item.thumbUrl + "'height='30' width='30'></td><td width='33%'><strong>" + item.title + "</strong></td><td width='30%'>" + item.artist + "</td><td width='30%'>" + item.album + "</td></tr></table></a>";
			    	}
			    }
			};
			xhttp.open("GET", "search?searchWord=" + document.getElementById('searchBar2').value, true);
			xhttp.send();
		}
		
		function reset() {
	    	document.getElementById("searchBar2").style.display = "block";
	    	document.getElementById("viewSelect").style.display = "none";
			document.getElementById("viewResult").innerHTML = "";
		}
	</script>
</body>
</html>