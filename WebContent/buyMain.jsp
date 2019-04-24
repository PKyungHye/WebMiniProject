<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<!-- 뷰포트 -->
<meta name="viewport" content="width=device-width" initial-scale="1">

<!-- 스타일시트 참조  -->
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/bootstrap.min.css">
<title>buy main</title>
<style type="text/css">
th {
	width: 50%;
}
td {
	border-bottom: 1px solid #ddd;
}
.table_container {
	width: 32%;
	float: left;
	padding-right: 1%;
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
							<li><a href="maincon?command=logout">로그아웃</a></li>
						</ul>
					</li>
			<%
				}
			%>
			</ul>
		</div>
	</nav>

	<!-- 애니매이션 담당 JQUERY -->
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>

	<!-- 부트스트랩 JS  -->
	<script src="js/bootstrap.js"></script>

	<div class="container">
		<div class="col-lg-4"></div>

		<div class="col-lg-4">
			<div class="jumbotron" style="padding-top: 20px;">
				<h4 style="text-align: center;">이용권 비교</h4>
				사이트: 
				<input type="checkbox" id="checkAll" onclick="checkAll()" style="padding: 6px 12px;">전체
				<input type="checkbox" name="sites" id="genie" onclick="offCheckAll()" style="padding: 6px 12px;">지니
				<input type="checkbox" name="sites" id="bugs" onclick="offCheckAll()" style="padding: 6px 12px;">벅스
				<input type="checkbox" name="sites" id="melon" onclick="offCheckAll()" style="padding: 6px 12px;">멜론
				<br><br>
				
				가격: 
				<input name="price" type="number" size="3" min="0" step="500" max="49500" value="0" style="padding: 6px 12px;" required>원 이상 ~ 
				<input name="price" type="number" size="3" min="500" step="500" max="50000" value="50000" style="padding: 6px 12px;" required>원 이하
				<br><br><br><br>
				
				<button onclick="loadDoc()" class="btn btn-primary form-control">검색</button>
			</div>
		</div>
	</div>
	
	<div align="center">
		<div class="table_container" id="viewGenie" style="display: none;">
			<table class="table">
				<caption>Genie</caption>
				<tr><th>이용권</th><th>가격</th></tr>
				<tbody id="dataGenie"></tbody>
			</table>
			<br>
			<p align="center">
				<a href="https://www.genie.co.kr/buy/thirtyDays" target="_blank">
					Genie
				</a>
			</p>
		</div>
		<div class="table_container" id="viewBugs" style="display: none;">
			<table class="table">
				<caption>Bugs</caption>
				<tr><th>이용권</th><th colspan="2">가격</th></tr>
				<tbody id="dataBugs"></tbody>
			</table>
			<br>
			<p align="center">
				<a href="https://music.bugs.co.kr/pay/public" target="_blank">
					Bugs
				</a>
			</p>
		</div>
		<div class="table_container" id="viewMelon" style="display: none;">
			<table class="table">
				<caption>Melon</caption>
				<tr><th>이용권</th><th colspan="2">가격</th></tr>
				<tbody id="dataMelon"></tbody>
			</table>
			<br>
			<p align="center">
				<a href="https://www.melon.com/buy/pamphlet/all.htm" target="_blank">
					Melon
				</a>
			</p>
		</div>
	</div>
	
	<script type="text/javascript">
		function checkAll() {
			document.getElementsByName("sites").forEach(f1);
		    function f1(item) {
		    	if (document.getElementById("checkAll").checked) {
		    		item.checked = true;
		        } else {
		        	item.checked = false;
		        }
		    }
		}
		
		var count;
		function offCheckAll() {
			count = 0;
			document.getElementsByName("sites").forEach(f1);
		    function f1(item) {
		    	if (item.checked) {
		    		count += 1;
		    	}
		    }
			if (count == document.getElementsByName("sites").length) {
				document.getElementById("checkAll").checked = true;
			} else {
				document.getElementById("checkAll").checked = false;
			}
		}
		
		function priceToString(num) {
			var s = num.toString();
			var str = "";
			if (s.length >= 4) {
				str = s.substr(0, s.length-3) + "," + s.substr(s.length-3);
			} else {
				str = s;
			}
			
			return str;
		}
	
		function loadDoc() {
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
			    if (this.readyState == 4 && this.status == 200) {
			    	if (Number(document.getElementsByName("price")[0].value) <= Number(document.getElementsByName("price")[1].value)) {
						document.getElementById("viewGenie").style.display = "none";
						document.getElementById("viewBugs").style.display = "none";
						document.getElementById("viewMelon").style.display = "none";

						var viewTr = "";
						var trGenie = "";
						var trBugs = "";
						var trMelon = "";
						
						if (document.getElementById("genie").checked) {
							listGenie = JSON.parse(this.responseText).genie;
							viewTr = "";
							listGenie.forEach(drawTable);
							trGenie = checkViewTr(viewTr);
							document.getElementById("viewGenie").style.display = "block";
							document.getElementById("dataGenie").innerHTML = trGenie;
						}
						if (document.getElementById("bugs").checked) {
							listBugs = JSON.parse(this.responseText).bugs;
							viewTr = "";
							listBugs.forEach(drawTable);
							trBugs = checkViewTr(viewTr);
							document.getElementById("viewBugs").style.display = "block";
							document.getElementById("dataBugs").innerHTML = trBugs;
						}
						if (document.getElementById("melon").checked) {
							listMelon = JSON.parse(this.responseText).melon;
							viewTr = "";
							listMelon.forEach(drawTable);
							trMelon = checkViewTr(viewTr);
							document.getElementById("viewMelon").style.display = "block";
							document.getElementById("dataMelon").innerHTML = trMelon;
						}
						
						function drawTable(item) {
							if(item.title != null) {
								var newInfo = [];
								item.info.forEach(checkPrice);
								function checkPrice(item2) {
									if ( (item2.price >= Number(document.getElementsByName("price")[0].value))
										&& (item2.price <= Number(document.getElementsByName("price")[1].value)) ) {
										newInfo.push(item2);
									}
								}
								item.info = newInfo;
								
								var spanNum = 1;
								spanNum = item.info.length;
								if (spanNum > 0) {
									viewTr += "<tr><td rowspan="+ spanNum +">" + item.title + "</td>";
									item.info.forEach(fe3);
									function fe3(item2) {
										if (item2.type != null) {
											viewTr += "<td>" + item2.type + "</td><td width=\"25%\">" + priceToString(item2.price) + "<small>원</small></td></tr>";
										} else {
											viewTr += "<td>" + priceToString(item2.price) + "<small>원</small></td></tr>";
										}
									}
								}
							} else {
								viewTr += "<tr><td colspan=\"2\">" + item + "</td></tr>";
							}
						}
						
						function checkViewTr(str) {
							if (str == "") {
								return "<tr><td colspan=\"2\">검색 결과가 없습니다.</td></tr>"
							} else {
								return str;
							}
						}
					} else {
						alert("가격을 다시 입력하세요.");
					}
			    }
			};
			xhttp.open("GET", "buycon", true);
			xhttp.send();
		}
	</script>
</body>
</html>