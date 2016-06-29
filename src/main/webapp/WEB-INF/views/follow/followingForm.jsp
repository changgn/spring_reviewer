<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<style>
			#followingTitle{
				min-height: 100px; 
				padding: 20px; 
				font-size: 20px; 
				margin: 0 auto; 
				background-color: #F6F6F6;
				font-size: 40px; 
				color: #4C4C4C;
			}
			#followingList{
				max-width: 300px;
				min-width: 200px; 
				margin: 5px auto; 
				border-bottom: solid 1px;
				background: white;
				text-align: left;
			}
			#name{
				font-family: '나눔고딕', 'Nanum Gothic', sans-serif; 
				font: 35px '나눔고딕', 'Nanum Gothic', sans-serif; 
			}
		</style>
		<script src="https://code.jquery.com/jquery-2.2.3.min.js"></script>
		<script>
		$(function(){
			$(".follow_button").click(function(e){
				e.preventDefault();
				var url= "/follow/followerAdd.do";
				var params = "profileId=" + "${profileId}";
				params += "&add_id=" + $(this).attr("name");
				params += "&follow=" + $(this).attr("id");
				$.ajax({
					type:"post"		// 포스트방식
					,url:url		// url 주소
					,data:params	//  요청에 전달되는 프로퍼티를 가진 객체
					,dataType:"json"
					,success:function(args){	//응답이 성공 상태 코드를 반환하면 호출되는 함수
						var follow = args.follow;
						if(follow == 'follow'){
							$(".follow_button").attr("id", "unfollow");
							$("#follow_image").attr("src", "../image/icon_36.png");
						} else{
							$(".follow_button").attr("id", "follow");
							$("#follow_image").attr("src", "../image/icon_35.png");
						}
					}
				    ,error:function(e) {	// 이곳의 ajax에서 에러가 나면 얼럿창으로 에러 메시지 출력
				    	alert("다시 한번 눌러주세요");
				    }
				});
				location.reload(true);
			});
		});
		</script>
		<title>${profileId} 팔로잉</title>
	</head>
	<body>
		<div id="followingTitle" align="center">
			<a id="name" href="/profile/myProfile.do?id=${profileId}">${profileId}</a>
			팔로잉 리스트
		</div>
		<c:forEach var="toId" items="${toIdList}"> 
			<div id="followingList" align="center">
				<a id="name" href="/profile/myProfile.do?id=${toId}">${toId}</a>
				<c:if test="${logId ne toId && logId ne null}">
 					<c:choose>
						<c:when test="${followCheck[toId] eq true}">
							<a id="unfollow" class="follow_button" name="${toId}" href="/follow/followingAdd.do?follow=unfollow&profileId=${profileId}&add_id=${toId}">
								<img id="follow_image" src="../image/icon_36.png" align="right">
							</a>
						</c:when>
						<c:otherwise>	<!-- test="${followCheck eq 'false'}" -->
							<a id="follow" class="follow_button" name="${toId}" href="/follow/followingAdd.do?follow=follow&profileId=${profileId}&add_id=${toId}">
								<img id="follow_image" src="../image/icon_35.png" align="right">
							</a>
						</c:otherwise>
					</c:choose>
				</c:if>
			</div>
		</c:forEach> 
	</body>
</html>