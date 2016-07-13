<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false"%>
<html>
	<head>
		<style>
			.Title{
				text-align: center;
				font-size: 20px;
				border: 1px solid;
				border-color: #b9b9b9;
				padding: 20px;
				color: #4c4c4c;
				width: 40%;	
				height: auto;
				margin: 50 auto;
				cursor: pointer;
			}
			.change{ margin: 0; padding: 0; width: 100px; height: 20px; text-align: left; cursor: pointer;}
			
			.item{border:1px solid #ddd; border-left:none; background:#fff; overflow:hidden;  position: static; width: 100%; z-index: 1000000;}
			.item li {float:left; width:20%; border-left:1px solid #ddd; text-align:center; box-sizing:border-box;}
			.item li {display:inline-block; padding:5px; cursor:pointer; vertical-align: middle;}
			
			.Member_Detaile_Info{position: relative; top: 0;right: 0;bottom: 0;left: 0;line-height: 100%; margin: 0 auto; width: 500px;}
			.Member_Detaile_Info ul{float: left; width: 50%}
			.one, two li{text-align: left;}
			
			.Sort_Menu{text-align: left; margin: 0; padding: 0; width: 200px; height: 20px; text-align: right; cursor: pointer;}
		</style>
		<link href="../css/style.css" rel="stylesheet" type="text/css">
		<script src="https://code.jquery.com/jquery-2.2.3.min.js"></script>
		<script>
			$(function(){
				$(".change").click(function(){
		  			$(location).attr("href", "/administrator/adminBoard.do");
		  		}); 
				$(".Title").click(function(){
					$(location).attr("href", "/administrator/adminMem.do");
				});
				$(".profile").click(function(){
					$(location).attr("href", "/profile/myProfile.do?id=" + $(this).attr("id"));
				});
				$(".memOut").click(function(){
					$(location).attr("href", "/administrator/adminOutput.do?outId=" + $(this).attr("id"));
				});
				
				$("body").on("click", ".Member_Simple_Info", function(e){
					e.preventDefault();
					var member_id = $(this).attr("id");
					var member_info = $("#member_" + member_id);
					var style = member_info.attr("style");
					var member_detale_class = member_info.attr("class");
					var css_member = $("#css_" + member_id);
					if(style == 'display: none;'){
						$("." + member_detale_class).hide();
						member_info.show();
					}else{
						member_info.hide();
					}
				});
			});
			
		</script>
		<title>회원관리</title>
	</head>
	<body>
		<div class="change">
			게시판 <img src="../image/change_icon.png">
		</div>
		<div class="Title">	회원 관리(${count}) </div>
		<div class="Sort_Menu">
			<c:if test="${kind eq 'noKind' &&sort eq 'noSort'}">
				<a href="/administrator/adminMem.do?kind=id&sort=DESC">
					아이디
				</a>
				&nbsp;
				<img src="../image/icon_08.png" height="15">
				&nbsp;
				<a href="/administrator/adminMem.do?kind=recommend&sort=DESC">
					추천
				</a>
				&nbsp;
				<img src="../image/icon_08.png" height="15">
				&nbsp;
				<a href="/administrator/adminMem.do?kind=regDate&sort=DESC">
					회원가입 
				</a>
			</c:if>
			<c:if test="${kind ne 'noKind' &&sort ne 'noSort'}">
				<c:if test="${sort eq 'DESC'}">
					<a href="/administrator/adminMem.do?kind=id&sort=DESC">
						아이디
					</a>
				</c:if>
				<c:if test="${sort eq 'ASC'}">
					<a href="/administrator/adminMem.do?kind=id&sort=ASC">
						아이디 
					</a>
				</c:if>
				&nbsp;
				<img src="../image/icon_08.png" height="15">
				&nbsp;
				<c:if test="${sort eq 'DESC'}">
					<a href="/administrator/adminMem.do?kind=recommend&sort=DESC">
						추천 
					</a>
				</c:if>
				<c:if test="${sort eq 'ASC'}">
					<a href="/administrator/adminMem.do?kind=recommend&sort=ASC">
						추천
					</a>
				</c:if>
				&nbsp;
					<img src="../image/icon_08.png" height="15">
				&nbsp;
				<c:if test="${sort eq 'DESC'}">
					<a href="/administrator/adminMem.do?kind=regDate&sort=DESC">
						회원가입
					</a>
				</c:if>
				<c:if test="${sort eq 'ASC'}">
					<a href="/administrator/adminMem.do?kind=regDate&sort=ASC">
						회원가입 
					</a>
				</c:if>
			</c:if>
		</div>
		<div class="Member_List_wrap">
			<c:forEach var="memberList" items="${memberList}">
				<c:if test="${memberList.id ne admin}">
					<ul class="item"  id="css_${memberList.id}">
						<li class="Member_Simple_Info" id="${memberList.id}">
							아이디 : ${memberList.id}
						</li>
						<li class="Member_Simple_Info" id="${memberList.id}">
							<img src="../image/recommend_off.png" width="15" height="15"> 추천 받은 수 : ${memberList.recommend_num }
						</li>
						<li class="Member_Simple_Info" id="${memberList.id}">
							가입일시 : <fmt:formatDate value="${memberList.reg_date}" pattern="yyyy-MM-dd HH:mm"/>
						</li>
						<li class="profile" id="${memberList.id}">
							프로필 보기
						</li>
						<li class="memOut" id="${memberList.id}">
							탈퇴
						</li>
					</ul>
					<div class="Member_Detaile_Info" id="member_${memberList.id}"style="display: none;">
						<ul class="one">
							<li>
								아이디 : ${memberList.id}
							</li>
							<li>
								이름 : ${memberList.name}
							</li>
							<li>
								성별 : ${memberList.gender}	
							</li>
							<li>
								email : ${memberList.email}
							</li>
							<li>
			             		핸드폰 번호 : ${memberList.phone_num}
							</li>
							<li>
								추천 받은 수 : ${memberList.recommend_num}
							</li>
							<li>
								가입일시 : <fmt:formatDate value="${memberList.reg_date}" pattern="yyyy-MM-dd HH:mm"/>
							</li>
							<li>
								카테고리 정보 : <br/>
								<c:forEach var="category" items="${MemberCategory[memberList.id]}">
									<c:if test="${category eq 'not_category'}">
										&nbsp; &nbsp; &nbsp; 카테고리 설정 없음
									</c:if>
									<c:if test="${category ne 'not_category'}">
										&nbsp; &nbsp; &nbsp; ${CategoryId[category].group1} / ${CategoryId[category].group2} / ${CategoryId[category].group3}
									</c:if>
								</c:forEach>
							</li>
							<li>
								<img src="../image/list_icon.png" width="15" height="15">작성 게시글 : ${boardCount[memberList.id]}
							</li>
							<li>
								<img src="../image/recommend_off.png" width="15" height="15"> 추천한 게시글 : ${recommendCount[memberList.id]}
							</li>
							<li>
								<img src="../image/report.png"> 신고한 게시글 : ${reportCount[memberList.id]}
							</li>
							<li>
								<img src="../image/screp_on.png" width="15" height="15">스크랩 게시글 : ${scropCount[memberList.id]}
							</li>
						</ul>
						<ul class="two">
							<li>
								<c:choose>
									<c:when test="${profilePhoto[memberList.id].realPath != null}">
										<a href="/profile/myProfile.do?id=${memberList.id}">
											<img src="${profilePhoto[memberList.id].realPath}" width="120" height="120" align="middle">
										</a>
									</c:when>
									<c:otherwise>
										<a href="/profile/myProfile.do?id=${memberList.id}">
											<img src="../image/default_profile.png" width="120" height="120" align="middle">
										</a>
									</c:otherwise>
								</c:choose>
							</li>
							<li>
								<a id="name" href="/administrator/adminOutput.do?outId=${memberList.id}">
									<img src="../image/memOut_icon1.png" width="100" height="80" align="middle">
								</a>
							</li>
						</ul>
					</div>
				</c:if>
			</c:forEach>
			<div style="height: 5%;">
			</div>
		</div>
	</body>
</html>