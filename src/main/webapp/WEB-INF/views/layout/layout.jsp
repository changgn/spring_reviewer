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
		setInterval(function(){
			if("${login_status}"==0 || "${login_status}"==1) {
				var url= "/notice/noticeCount.do";
				var params = "notice=true";
				$.ajax({
					type:"post"		// 포스트방식
					,url:url		// url 주소
					,data:params	//  요청에 전달되는 프로퍼티를 가진 객체
					,dataType:"json"
					,success:function(args){	//응답이 성공 상태 코드를 반환하면 호출되는 함수
						var noReadNoticeCount = args.noReadNoticeCount;
						$("#notice_count").text(noReadNoticeCount);
					}
				    ,error:function(e) {	// 이곳의 ajax에서 에러가 나면 얼럿창으로 에러 메시지 출력
				    	alert(e.responseText);
				    }
				});
			}
		}, 3000);
		$(document).ready(function(){
			$("#notice").css("display","none");
			$("#user").css("display","none");
			$(window).scroll(function(){
				if($(window).scrollTop()>79) {
					$("#nav").css("position", "fixed");
					$("#nav").css('top', "0px");
				} else {
					$("#nav").css("position", "static");
				}
			});
			$(document).mousedown(function(e) {
				$('#notice').each(function() {
					if( $(this).css('display') == 'block' ) {
						var objPos = $(this).offset();
						objPos.right = (objPos.left + $(this).width());
						objPos.bottom = (objPos.top + $(this).height());
						if( e.pageX < objPos.left || e.pageX > objPos.right
						 || e.pageY < objPos.top || e.pageY > objPos.bottom ) {
							$(this).slideUp('fast');
							$(this).removeAttr("class");
						}
					}
				});
				$('#user').each(function() {
					if( $(this).css('display') == 'block' ) {
						var objPos = $(this).offset();
						objPos.right = (objPos.left + $(this).width());
						objPos.bottom = (objPos.top + $(this).height());
						if( e.pageX < objPos.left || e.pageX > objPos.right
						 || e.pageY < objPos.top || e.pageY > objPos.bottom ) {
							$(this).slideUp('fast');
							$(this).slideUp('fast');
							$(this).removeAttr("class");
						}
					}
				});
			});
		});
		$(function(){		
			$("body").on("mouseover", ".ul_list li", function(){
				$(this).css("background-color","#F6F6F6");
			});
			$("body").on("mouseleave", ".ul_list li", function(){
				$(this).css("background-color","white");
			});
			$("body").on("click", ".li_notice", function(){
				//ajax
				var url= "/notice/noticeRead.do";
				var params = "notice_num=" + $(this).attr("id");
				$.ajax({
					type:"post"		// 포스트방식
					,url:url		// url 주소
					,data:params	//  요청에 전달되는 프로퍼티를 가진 객체
					,dataType:"json"
					,success:function(args){	//응답이 성공 상태 코드를 반환하면 호출되는 함수
						var notice_num = args.notice_num;
						$(".li_notice[id='" + notice_num + "']").css("background-color","#F6F6F6");
					}
				    ,error:function(e) {	// 이곳의 ajax에서 에러가 나면 얼럿창으로 에러 메시지 출력
				    	alert(e.responseText);
				    }
				});
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

			$(".btn_notice_toggle").click(function(){
				if($("#notice").attr("class")=="selected"){
					$("#notice").slideUp("fast");
					$("#notice").removeAttr("class");
				}
				else {
					//ajax
					$(".li_notice").remove();
					var url= "/notice/notice.do";
					var params = "notice=true";
					$.ajax({
						type:"post"		// 포스트방식
						,url:url		// url 주소
						,data:params	//  요청에 전달되는 프로퍼티를 가진 객체
						,dataType:"json"
						,success:function(args){	//응답이 성공 상태 코드를 반환하면 호출되는 함수
							var view = "";
							var noticeList = args.noticeList;
							var dateList = args.dateList;
							var noReadNoticeCount = args.noReadNoticeCount;
							$("#notice_count").text(noReadNoticeCount);
							
							for(var idx=0; idx<noticeList.length; idx++) {
								view += '<li id="' + noticeList[idx].notice_num + '" class="li_notice">';
							
								view += '<span class="notice_text">';
								
								if(noticeList[idx].kind == 'content') {
									if(noticeList[idx].read == 1) {
										view += '<a href="/content/contentForm.do?board_num=' + noticeList[idx].board_num +'" style="color: #C6C6C6">' + noticeList[idx].id + '님이 새 리뷰를 작성하셨습니다.</a>';
									} else {
										view += '<a href="/content/contentForm.do?board_num=' + noticeList[idx].board_num +'">' + noticeList[idx].id + '님이 새 리뷰를 작성하셨습니다.</a>';
									}
								}
								if(noticeList[idx].kind == 'recommend') {
									if(noticeList[idx].read == 1) {
										view += '<a href="/content/contentForm.do?board_num=' + noticeList[idx].board_num +'" style="color: #C6C6C6">' + noticeList[idx].id + '님이 회원님의 게시물을 추천하였습니다.</a>';
									} else {
										view += '<a href="/content/contentForm.do?board_num=' + noticeList[idx].board_num +'">' + noticeList[idx].id + '님이 회원님의 게시물을 추천하였습니다.</a>';
									}
								}
								if(noticeList[idx].kind == 'report') {
									if(noticeList[idx].read == 1) {
										view += '<a href="/content/contentForm.do?board_num=' + noticeList[idx].board_num +'" style="color: #C6C6C6">' + noticeList[idx].id + '님이 회원님의 게시물을 신고하였습니다.</a>';
									} else {
										view += '<a href="/content/contentForm.do?board_num=' + noticeList[idx].board_num +'">' + noticeList[idx].id + '님이 회원님의 게시물을 신고하였습니다.</a>';
									}
								}
								if(noticeList[idx].kind == 'comment') {
									if(noticeList[idx].read == 1) {
										view += '<a href="/content/contentForm.do?board_num=' + noticeList[idx].board_num +'&comment=true" style="color: #C6C6C6">' + noticeList[idx].id + '님이 회원님의 게시물에 댓글을 남겼습니다.</a>';
									} else {
										view += '<a href="/content/contentForm.do?board_num=' + noticeList[idx].board_num +'&comment=true">' + noticeList[idx].id + '님이 회원님의 게시물에 댓글을 남겼습니다.</a>';
									}
								}
								if(noticeList[idx].kind == 'follow') {
									if(noticeList[idx].read == 1) {
										view += '<a href="/profile/myProfile.do?id=' + noticeList[idx].id + '" style="color: #C6C6C6">' + noticeList[idx].id + '님이 회원님을 팔로우 하였습니다.</a>';
									} else {
										view += '<a href="/profile/myProfile.do?id=' + noticeList[idx].id + '">' + noticeList[idx].id + '님이 회원님을 팔로우 하였습니다.</a>';
									}
								}
								if(noticeList[idx].kind == 'unfollow') {
									if(noticeList[idx].read == 1) {
										view += '<a href="/profile/myProfile.do?id=' + noticeList[idx].id + '" style="color: #C6C6C6">' + noticeList[idx].id + '님이 회원님을 언팔로우 하였습니다.</a>';
									} else {
										view += '<a href="/profile/myProfile.do?id=' + noticeList[idx].id + '">' + noticeList[idx].id + '님이 회원님을 언팔로우 하였습니다.</a>';
									}
								}
								view += '</span>';
								if(noticeList[idx].read == 1) {
									view += '<span class="notice_time" style="color: #C6C6C6">' + dateList[idx] + '</span>';
								} else {
									view += '<span class="notice_time">' + dateList[idx] + '</span>';
								}
								view += '</li>';

								if(noticeList[idx].read == 1) {
								}
							}
							if(noticeList.length==0) {
								view += '<li class="li_notice">';
								view += '<span class="notice_text">';
								view += '알림이 없습니다.';
								view += '</span>';
								view += '</li>';
							}
							$("#list_notice").append(view);
						}
					    ,error:function(e) {	// 이곳의 ajax에서 에러가 나면 얼럿창으로 에러 메시지 출력
					    	alert(e.responseText);
					    }
					});
					
					$("[class='selected']").css("display","none");
					$("[class='selected']").removeAttr("class");
					$("#notice").attr("class","selected");
					$("#notice").slideDown("fast");
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

			$("body").on("click", ".li_notice", function(){
				$(location).attr("href", $(this).find("a").attr("href"));
			});
			
			$("#btn_newsfeed").click(function(){
				$(location).attr("href", "/main/main.do?sort=all");
			});

			$("#btn_my").click(function(){
				$(location).attr("href", $("#btn_my a").attr("href"));
			});
		});
	</script>
	<style>
		#notice_count { position: absolute; top: 20px; right: 115px; color: white; }
	</style>
</head>
<body>
	<div id="header">
	<!-- 헤더 -->
		<h1 id="logo">
			<a id="logo_btn" href="/main/main.do?sort=all" title="Reviewer">Reviewer</a>
		</h1>
		<span id="main_btn">
		
			<c:if test="${login_status==0 || login_status==1}">
			<a class="btn_gnb btn_notice_toggle" href="#" onclick="event.preventDefault();">
				<span id="btn_notice">알림버튼</span>
				<c:if test="${(login_status==0 || login_status==1) && noReadNoticeCount!=0}">
					<span id="notice_count">${noReadNoticeCount}</span>
				</c:if>
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
				<ul id="list_user" class="ul_list">
					<li id="btn_mod_categoty"><a href="#">카테고리 수정</a></li>
					<c:if test="${login_status==0}">
						<li id="btn_admin"><a href="#">관리자 페이지</a></li>
					</c:if>
					<li id="btn_mod_member"><a href="#">회원정보 수정</a></li>
					<li id="btn_logout"><a href="#">로그아웃</a></li>
				</ul>
			</div>
		</c:if>
	 	<div id="notice">
			<ul id="list_notice" class="ul_list"></ul>
		</div> 

	</div>
	
	<div id="nav">
	<!-- 네비게이션 -->
		<ul id="list_nav" class="ul_list">
			<li>
				<div id="btn_newsfeed"><a href="#" class="nav_btn" onclick="event.preventDefault();">뉴&nbsp;&nbsp;스&nbsp;&nbsp;피&nbsp;&nbsp;드</a></div>
			</li>
			<li>
				<div id="btn_my">
					<c:if test="${login_status!=0 && login_status!=1}">
						<a href="/logon/login.do" class="nav_btn">로 그 인</a>
					</c:if>
					<c:if test="${login_status==0 || login_status==1}">
						<a href="/profile/myProfile.do?id=${sessionScope.id}" class="nav_btn">${sessionScope.id}</a>
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