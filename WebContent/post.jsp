<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="model.domain.PostDTO"%>
<%@ page import="model.PostDAO"%>
<%@ page import="model.UserDAO"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- 뷰포트 -->
<meta name="viewport" content="width=device-width" initial-scale="1">
<!-- 스타일시트 참조  -->
<link rel="stylesheet" href="css/bootstrap.css">
<title>jsp 게시판 웹사이트</title>
<style type="text/css">
table .songlist {
    width: 100%;
    text-align: left;
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
				String userid = (String) session.getAttribute("userid");
				if (userid == null) {
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

	<%
		int pageNum = 1; //기본 페이지 넘버

		//페이지넘버값이 있을때
		if (request.getParameter("pageNum") != null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
		}
	%>
	
	<!-- 게시판 -->
	<div class="container">
		<div class="row">
					<%
						ArrayList<PostDTO> list = PostDAO.getList(pageNum);
						for (int i = 0; i < list.size(); i++) {
							PostDTO p = list.get(i);
							
							String star = "";
							for (int j = 0; j < p.getUserid().length()-3; j++) {
								star += "*";
							}

							String userInfo = UserDAO.getNickname(p.getUserid()) + " (" + p.getUserid().substring(0, 3) + star + ")";
							if (UserDAO.getNickname(p.getUserid()).equals("null")) {
								userInfo = "탈퇴한 회원";
							}
					%>
			<table class="table table-striped"
				style="text-align: center; border: 1px solid #dddddd">
				<tbody>
					<tr>
						<td width='10%'><%=p.getPostno()%></td>
						<td width='40%'><strong><%=p.getPtitle()%></strong></td>
						<td width='30%'><%=userInfo%></td>
						<td><%=p.getPostdate().substring(0, 11) + p.getPostdate().substring(11, 13)
							+ ":" + p.getPostdate().substring(14, 16)%></td>
						<%
							//글작성자 본인일시 수정 삭제 가능 
							if ( userid != null && userid.equals(p.getUserid()) ) {
						%>
						<td>
							<a onclick="if(confirm('수정할까요?'))location.href='postWrite.jsp?command=update&postno=<%=p.getPostno()%>';">수정</a>
							<a onclick="if(confirm('삭제할까요?'))location.href='maincon?command=postDelete&postno=<%=p.getPostno()%>';">삭제</a>
						</td>
						<%
							} else {
						%>
							<td>
							</td>
						<%
							}
						%>
					</tr>
					<tr>
						<td colspan = "5" align="center">
							<div style="width: 70%; padding: 5px; padding-left: 20px; border-style: solid; border-color: #f1f1f1;">
								<table class='songlist'>
									<tr>
										<td><img src="<%=p.getSurl()%>" height='30px' width='30px'></td>
										<td width='33%'><strong><%=p.getStitle()%></strong></td>
										<td width='30%'><%=p.getSartist()%></td>
										<td width='30%'><%=p.getSalbum()%></td>
									</tr>
								</table>
							</div>
							<div style="padding: 10px;">
								<%=p.getPcontent().replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>")%>
							</div>
						</td>
				</tbody>
			</table>
					<%
						}
					%>
			
			<!-- 페이지 넘기기 -->
			<div>
				<ul class="nav navbar-nav">
				<%
					int startNum = pageNum - 2;
					int endNum = pageNum + 2;
					int lastPageNum = (PostDAO.getCount())/10 + 1;
					
					if (startNum <= 0) {
						startNum = 1;
						endNum = 5;
					}
					
					if (pageNum >= lastPageNum - 2) {
						startNum = lastPageNum - 4;
						endNum = lastPageNum;
					}
					
					if (pageNum != 1) {
				%>
						<li><a href="post.jsp?pageNum=1">처음으로</a></li>
				<%
					}
					for (int i = startNum; i <= endNum; i++) {
						if (i == pageNum) {
				%>
								<li><a href="post.jsp?pageNum=<%=i%>"><strong style="text-decoration: underline;"><big><%=i%></big></strong></a></li>
				<%
						} else {
				%>
							<li><a href="post.jsp?pageNum=<%=i%>"><%=i%></a></li>
				<%
						}
					}
					if (PostDAO.nextPage(pageNum)) {
				%>
						<li><a href="post.jsp?pageNum=<%=lastPageNum%>">마지막으로</a></li>
				<%
					}
				%>
				</ul>
			</div>
				
			<%
				if (session.getAttribute("userid") != null) {
			%>
			<a href="postWrite.jsp?command=write" class="btn btn-primary pull-right">글쓰기</a>
			<%
				} else {
			%>
			<button class="btn btn-primary pull-right" onclick="if(confirm('로그인 하세요'))location.href='login.jsp';" type="button">글쓰기</button>
			<%
				}
			%>
		</div>
	</div>

	<!-- 애니매이션 담당 JQUERY -->
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<!-- 부트스트랩 JS  -->
	<script src="js/bootstrap.js"></script>

</body>
</html>

