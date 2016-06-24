<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script src="https://code.jquery.com/jquery-2.2.3.min.js"></script>
<script>


$(function(){
	var top = 0;
	$(".cont_menu_option").click(function(e){
		e.preventDefault();
		var a = $("#menu_" + $(this).attr("id"));
		a.show();
	});
	$(".cont_btn_option").click(function(){
		$(this).hide();
	});
	$(".follow_btn").click(function(e){
		e.preventDefault();
		var url= "/follow/follow.do";
		var params = "to_id=" + "${paramId}";
		params += "&follow=" + $(this).attr("id");
		$.ajax({
			type:"post"		// 포스트방식
			,url:url		// url 주소
			,data:params	//  요청에 전달되는 프로퍼티를 가진 객체
			,dataType:"json"
			,success:function(args){	//응답이 성공 상태 코드를 반환하면 호출되는 함수
				
				var follow = args.follow;
				var followerCount = args.followerCount;
				
				if(follow == 'follow'){
					$(".follow_btn").attr("id", "unfollow");
					$("#follow_sta").attr("src", "../image/icon_36.png");
				} else{
					$(".follow_btn").attr("id", "follow");
					$("#follow_sta").attr("src", "../image/icon_35.png");
				}
				
				$("#follower_profile a").text("팔로워  " + followerCount + " >");
			}
		    ,error:function(e) {	// 이곳의 ajax에서 에러가 나면 얼럿창으로 에러 메시지 출력
		    	alert(e.responseText);
		    }
		});
	
	});
});
</script>

<title>${paramId} 프로필</title>
</head>
<body>

	<div id="my_profile_info_area">
		<div id="my_profile_name">
			<c:if test="${id!=paramId && (login_status==0 || login_status==1)}">
				<div id="btn_follow_add">
					<c:if test="${followCheck == true}">
						<a id="unfollow" class="follow_btn" href="/follow/follow.do?follow=unfollow&to_id=${paramId}"><img id="follow_sta" src='../image/icon_36.png'></a>
					</c:if>
					<c:if test="${followCheck == false}">
						<a id="follow" class="follow_btn" href="/follow/follow.do?follow=follow&to_id=${paramId}"><img id="follow_sta" src='../image/icon_35.png'></a>
					</c:if>
				</div>
			</c:if>
			<div class="my_profile" id="id_profile">${paramId}</div>
		</div>
		<div id="my_profile_follow">
			<div class="follow_profile" id="follower_profile"><a href="/follow/follower.do?id=${paramId}">팔로워  ${followerCount } ></a></div>
			<div class="follow_profile" id="following_profile"><a href="/follow/follewing.do?id=${paramId}">팔로잉  ${followingCount } ></a></div>
			<div class="follow_profile" id="content_scrap"><a href="/screp/screpList.do?id=${paramId}">스크랩 ${screpCount} ></a></div>
 		</div>
 		<div class="my_profile" id="category_my_profile">	
			<c:forEach var="item" items="${CategoryList}" varStatus="status">
				<div>${item.group1} > ${item.group2} > ${item.group3}</div>
			</c:forEach> 
		</div>
	</div>

	<div class="my_profile" id="board_profile">
		<c:forEach var="board" items="${allBoardList}">
			<div class="content_wrap">
				<div class="content_first">	
					<div class="cont_writer">
						<a href="/profile/myProfile.do?id=${board.board.id}" class="cont_writer_id">${board.board.id}</a>
						<div class="cont_wdate">
							<fmt:formatDate value="${board.board.write_date}" pattern="yyyy-MM-dd HH:mm"/>
						</div>
						<div class="cont_menu">
							<a href="#" id="${board.board.board_num}" class="cont_menu_option">
								<span id="cont_btn_menu">옵션</span>						
							</a>
							 <div id="menu_${board.board.board_num}" class="cont_btn_option">
								<div class="ly_dimmed"></div>
								<ul class="cont_popup">
									<li>
										<a href="/content/reportPro.do?board_num=${board.board.board_num}" class="cont_popup_close" >이 게시글 신고</a>
									</li>
								<c:if test="${board.board.id == id}">						
									<li>
										<a href="/content/deleteContent.do?id=${board.board.id}&board_num=${board.board.board_num}" class="cont_popup_close" >이 게시글 삭제</a>
									</li>
								</c:if>
								</ul>
							</div>
						</div>
					</div>
				</div>
				<div class="content_second">
					<span class="content_view">
						<span><pre id="pre_${board.board.board_num}">${board.board.content}</pre>
							<c:if test="${board.contentFlag == true}">
								<span class="cont_theview">
									<span>...</span>
									<a href="/content/contentForm.do?board_num=${board.board.board_num}" class="btn_view_more">더보기</a>
								</span>
							</c:if>
						</span>
					</span>
				</div>

				<div class="content_second">
					<span class="content_view_sp"> <span><pre>${board.content}</pre>
					</span>
					</span>
				</div>

				<c:if test="${board.photo.realPath != null}">
			   		<a href="/content/contentForm.do?board_num=${board.board.board_num}" class="item_info_wrap">
				        <span class="item_cont" title="컨텐츠 상세페이지">
				            <span class="item_thumb">
				                <img class="list_photo" src="${board.photo.realPath}">
				                <span class="thumb_mask_bottom"></span>
				            </span>
				      	</span>
			       	</a>
		       	</c:if>
		       	
		       	
		       	<div class="cont_category_info">
		       		<p id="cont_category_info_f">${board.category.group1}> ${board.category.group2}> ${board.category.group3}</p>
		       	</div>
		       	<div class="cont_btns">
		       		<div class="cont_btns_wrap">
						<div class="btns_re">
							<a href="/recommend/recommendPro.do?board_num=${board.board.board_num}" class="btns_re_item">
		                		<span class="u_ico"></span><em class="u_txt">좋아요</em><em class="u_cnt"> ${board.board.recommend_num}</em>
		                 	</a>
						</div>
						<a href="/content/contentForm.do?board_num=${board.board.board_num}&comment=true" class="btns_coment" >
							<span class="u_ico_coment">댓글</span>
							<span class="text_num">${board.commentCount}</span>				
						</a>
		 				<a href="/screp/screpInsert.do?board_num=${board.board.board_num}" class="btns_screp" >
							<span class="u_ico_screp">스크렙</span>
							<span class="text_num">19</span>
							</a>
		       		</div>
		       	</div>
			</div>
		</c:forEach>
	</div>		
	<c:if test="${login_status==0 || login_status==1}">
		<div class="btn_posting_wrap">
			<a href="/write/writeForm.do" class="btn_posting">
				<span class="u_vc">글쓰기</span>
			</a>
		</div>
	</c:if>


<%-- <div id="content_area">
	${board.id}, ${board.content}
</div>
<div id="content_photo_area">
	<c:forEach var="photo" items="${photoList}">
		<div class="content_photo"><img src="${photo.realPath}"></div>
	</c:forEach>
</div>
<div id="content_comment_area">
</div>
</div> --%>

</body>
</html>