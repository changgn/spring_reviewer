<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page isELIgnored="false" %>
<html lang="ko">
<head>
	<link href="../css/style.css" rel="stylesheet" type="text/css">
	<script src="https://code.jquery.com/jquery-2.2.4.min.js"></script>
	<title><decorator:title /></title>
	<decorator:head />
	<script>
		$(document).ready(function(){
			$("#alarm").css("display","none");
			$("#user").css("display","none");
			$(window).scroll(function(){
				if($(window).scrollTop()>40) {
					$("#nav").css("position", "fixed");
					$("#nav").css('top', "0px");
				} else {
					$("#nav").css("position", "static");
				}
			});

		});
		$(function(){		
			
			$("li").mouseover(function(){
				$(this).css("background-color","#F6F6F6");
			});
			$("li").mouseleave(function(){
				$(this).css("background-color","white");
			});
			
			
			$("#btn_mod_categoty").click(function(){
				$(location).attr("href", "/categorySet/categorySet.do");
			});		
			$("#btn_admin").click(function(){
				$(location).attr("href", "/administrator/adminForm.do");
			});				
			$("#btn_mod_member").click(function(){
				$(location).attr("href", "/member/modify.do");
			});	
			$("#btn_logout").click(function(){
				$(location).attr("href", "/logon/logout.do");
			});	
			
			$("#nav, #content").click(function(){
				$("#alarm, #user").slideUp("fast");
				$("#alarm, #user").removeAttr("class");
			});			
			$(".btn_alarm_toggle").click(function(){
				if($("#alarm").attr("class")=="selected"){
					$("#alarm").slideUp("fast");
					$("#alarm").removeAttr("class");
				}
				else {
					$("[class='selected']").css("display","none");
					$("[class='selected']").removeAttr("class");
					$("#alarm").attr("class","selected");
					$("#alarm").slideDown("fast");
				}
			});
			$(".btn_user_toggle").click(function(){
				if($("#user").attr("class")=="selected"){
					$("#user").slideUp("fast");
					$("#user").removeAttr("class");
				}
				else {
					$("[class='selected']").css("display","none");
					$("[class='selected']").removeAttr("class");
					$("#user").attr("class","selected");
					$("#user").slideDown("fast");
				}
			});
			$("#btn_newsfeed").click(function(){
				$(location).attr("href", "/main/main.do");
			});

			$("#btn_my").click(function(){
				if($("#btn_my a").attr("href")=="/logon/login.do"){
					alert("로그인이 필요합니다");
				}
				$(location).attr("href", $("#btn_my a").attr("href"));
			});
		});
	</script>
</head>
<body>
	<div id="header">
	<!-- 헤더 -->
		<h1 id="logo">
			<a id="logo_btn" href="/main/main.do" title="Reviewer">Reviewer</a>
		</h1>
		<span id="main_btn">
		
			<c:if test="${login_status==0 || login_status==1}">
			<a class="btn_gnb btn_alarm_toggle" href="#" onclick="event.preventDefault();">
				<span id="btn_alarm">알림버튼</span>
			</a> 
			</c:if>
			<a class="btn_gnb" href="/search/searchForm.do">
				<span id="btn_search">검색버튼</span>
			</a>
			<c:if test="${login_status!=0 && login_status!=1}">
				<a class="btn_gnb" href="/logon/login.do">
					<span id="btn_login">로그인 버튼</span>
				</a>
			</c:if>
			<c:if test="${login_status==0 || login_status==1}">
				<a class="btn_gnb btn_user_toggle" href="#" onclick="event.preventDefault();">
					<span id="btn_user">계정설정</span>
				</a>
			</c:if>

		</span>
		<c:if test="${login_status==0 || login_status==1}">
			<div id="user">
				<ul id="list_user">
					<li id="btn_mod_categoty"><a href="#">카테고리 수정</a></li>
					<c:if test="${login_status==0}">
						<li id="btn_admin"><a href="#">관리자 페이지</a></li>
					</c:if>
					<li id="btn_mod_member"><a href="#">회원정보 수정</a></li>
					<li id="btn_logout"><a href="#">로그아웃</a></li>
				</ul>
			</div>
		</c:if>
	 	<div id="alarm">
			<ul id="list_alarm">
				<li>알림1<br><a href="#">알림1</a></li>
				<li>알림2<br><a href="#">알림2</a></li>
				<li>알림3<br><a href="#">알림3</a></li>
				<li>알림4<br><a href="#">알림4</a></li>
				<li>알림5<br><a href="#">알림5</a></li>
			</ul>
		</div> 

	</div>
	
	<div id="nav">
	<!-- 네비게이션 -->
		<ul id="list_nav">
			<li>
				<div id="btn_newsfeed"><a href="#" class="nav_btn" onclick="event.preventDefault();">뉴&nbsp;&nbsp;스&nbsp;&nbsp;피&nbsp;&nbsp;드</a></div>
			</li>
			<li>
				<div id="btn_my">
					<c:if test="${login_status!=0 && login_status!=1}">
						<a href="/logon/login.do" class="nav_btn">M&nbsp;&nbsp;&nbsp;Y</a>
					</c:if>
					<c:if test="${login_status==0 || login_status==1}">
						<a href="/profile/myProfile.do?id=${sessionScope.id }" class="nav_btn">M&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Y</a>
					</c:if>
				</div>
			</li>
		</ul>
	</div>
	<div id="content">
		<decorator:body />
	</div>
	
	<div id="footer">
	<!-- 풋터 -->
		<ul id="list_policy">
			<li>
				<a href="#" class="list_link">이용약관</a>
			</li>
			<li>
				<a href="#" class="list_link">개인정보취급방침</a>
			</li>
		</ul>
	</div>
</body>
</html>