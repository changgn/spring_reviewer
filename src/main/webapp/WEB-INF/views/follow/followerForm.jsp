<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link href="../css/style.css" rel="stylesheet" type="text/css">
		<script src="https://code.jquery.com/jquery-2.2.3.min.js"></script>
		<script>
		$(function(){
			$(".profile_follow_body").on("click", ".link_follow_button", function(e){
				event.preventDefault();
				var add_id = $(this).attr("name");
				var follow_selector = $(".follow_image_"+add_id);
				
				var url= "/follow/followerAdd.do";
				var params = "profileId=" + "${profileId}";
 				params += "&add_id=" + $(this).attr("name");
				params += "&follow=" + follow_selector.attr("id");
				$.ajax({
					type:"post"		// 포스트방식
					,url:url		// url 주소
					,data:params	//  요청에 전달되는 프로퍼티를 가진 객체
					,dataType:"json"
					,success:function(args){	//응답이 성공 상태 코드를 반환하면 호출되는 함수
						var follow = args.follow;
						var id = args.id;
						var select = $(".follow_image_"+id);
						if(follow == 'follow_off'){
							select.attr("id", "follow_on");
							select.attr("src", "../image/icon_36.png");
						} else{
							select.attr("id", "follow_off");
							select.attr("src", "../image/icon_35.png");
						}
					}
				    ,error:function(event) {	// 이곳의 ajax에서 에러가 나면 얼럿창으로 에러 메시지 출력
				    	alert("다시 한번 눌러주세요");
				    }
				});
			});
		});
		</script>
		<title>${profileId} 팔로워</title>
	</head>
	<body>
		<!-- 프로필 정보 -->
		<div class="profile_follow_header" id="profile_name">
			<h2 class="follow_header">
				<a href="/profile/myProfile.do?id=${profileId}" title="프로필 정보" class="link_profile">
					<span class="profile_thumb">
						<img src="${profileIdPhoto.realPath}" width="55" height="55" align="middle"> 
					    <span class="profile_thumb_mask"></span>
					</span>
					&nbsp;&nbsp;&nbsp;
					<span class="profile_name">
						<span class="user_name">
							${profileId}
						</span>
						님의 팔로워
					</span>
				</a>
			</h2>
		</div>
		<!-- 팔로우 목록 -->
		<div class="profile_follow_body" id="follower_body">
			<div class="follow_list_wrap" id="follower_wrap">
				<ul class="follow_list" id="list">
					<c:forEach items="${fromList}" var="fromId">
						<li class="follow_list_item_wrap">
							<div class="follow_List_item" id="follower_info">
								<div class="follow_List_item_profile_info_wrap">
									<a href="/profile/myProfile.do?id=${fromId}" title="팔로워 정보" class="link_follow_profile" >
										<span class="profile_thumb">
											<img src="${list_profile_photo[fromId].realPath}" width="55" height="55" align="middle"> 
					  						<span class="profile_thumb_mask"></span>
										</span>
										&nbsp;&nbsp;&nbsp;
										<span class="profile_name">
											${fromId}
										</span>
									</a>
									<c:if test="${logId ne fromId && logId ne null}">
										<a class="link_follow_button" name="${fromId}" href="/follow/followerAdd.do?follow=unfollow&profileId=${profileId}&add_id=${fromId}" title="팔로우 버튼">
											<c:choose>
												<c:when test="${followCheck[fromId] eq true}">
													<img class="follow_image_${fromId}" id="follow_on" src="../image/icon_36.png" align="right">
												</c:when>
												<c:otherwise >
													<img class="follow_image_${fromId}" id="follow_off" src="../image/icon_35.png" align="right">
												</c:otherwise>
											</c:choose>
										</a>
									</c:if>
								</div>
							</div>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</body>
</html>