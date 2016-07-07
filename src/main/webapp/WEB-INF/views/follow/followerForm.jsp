<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link href="../css/style.css" rel="stylesheet" type="text/css">
		<script src="https://code.jquery.com/jquery-2.2.3.min.js"></script>
		<script>
		$(function(){
			$(".follow_profile_info_wrap").on("click", ".follow_button", function(e){
				event.preventDefault();
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
						var id = args.id;
						var select = $(".follow_image_"+id);
						if(follow == 'follow'){
							$(".follow_button").attr("id", "unfollow");
							select.attr("src", "../image/icon_36.png");
						} else{
							$(".follow_button").attr("id", "follow");
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
								<div class="follow_profile_info_wrap">
									<a href="/profile/myProfile.do?id=${fromId}" title="팔로워 정보" class="link_follow_profile">
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
										<c:choose>
											<c:when test="${followCheck[fromId] eq true}">
												<a id="unfollow" class="follow_button" name="${fromId}" href="/follow/followerAdd.do?follow=unfollow&profileId=${profileId}&add_id=${fromId}" title="팔로우 버튼">
													<img class="follow_image_${fromId}" src="../image/icon_36.png" width="45" height="45" align="right">
												</a>
											</c:when>
											<c:otherwise >
												<a id="follow" class="follow_button" name="${fromId}" href="/follow/followerAdd.do?follow=follow&profileId=${profileId}&add_id=${fromId}" title="팔로우 버튼">
													<img class="follow_image_${fromId}" src="../image/icon_35.png" width="45" height="45" align="right">
												</a>
											</c:otherwise>
										</c:choose>
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