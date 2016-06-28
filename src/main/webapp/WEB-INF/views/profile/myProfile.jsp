<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script src="https://code.jquery.com/jquery-2.2.3.min.js"></script>
<script>

// 스크랩 버튼


$(function(){
	$("#my_content").click(function(){
		$(location).attr("href", "/profile/myProfile.do?id=${paramId}");
	});
	$("#my_screp").click(function(){
		$(location).attr("href", "/profile/screpList.do?id=${paramId}");
	});
	
	$(function(){
		$(".btns_re_items").click(function(e){
			e.preventDefault();
			var url= "/recommend/recommend.do";
			var params = "board_num=" + $(this).attr("id");
			
			$.ajax({
				type:"post"		// 포스트방식
				,url:url		// url 주소
				,data:params	//  요청에 전달되는 프로퍼티를 가진 객체
				,dataType:"json"
				,success:function(args){	//응답이 성공 상태 코드를 반환하면 호출되는 함수
					if(args.error == null){
						var recommend_num = args.recommend_num;
						var recommendFlag = args.recommendFlag;
						var selector = $("#recommend_img"+args.board_num);
						var selector2 = $("#u_cnt"+args.board_num);
						selector2.text(" " + recommend_num);
						if(recommendFlag == 'recommend'){
							selector.attr("src", "../image/recommend_off.png");
						} else{
							selector.attr("src", "../image/recommend_on.png");
						}
					} else {
						$(location).attr("href", "/logon/login.do");
					}
					
				}
			    ,error:function(e) {	// 이곳의 ajax에서 에러가 나면 얼럿창으로 에러 메시지 출력
			    	alert(e.responseText);
					$(location).attr("href", "/logon/login.do");
			    }
			});
		
		});
	
	
	$(".btns_scr_items").click(function(e){
		e.preventDefault();
		var url= "/screp/screp.do";
		var params = "board_num=" + $(this).attr("id");
		
		$.ajax({
			type:"post"		// 포스트방식
			,url:url		// url 주소
			,data:params	//  요청에 전달되는 프로퍼티를 가진 객체
			,dataType:"json"
			,success:function(args){	//응답이 성공 상태 코드를 반환하면 호출되는 함수
				if(args.error == null){

					var screp_num = args.screp_num;
					var screpFlag = args.screpFlag;
					var selector = $("#screp_img"+args.board_num);
					var selector2 = $("#screp_cnt"+args.board_num);
					selector2.text(" " + screp_num);

				if(screpFlag == 'screp'){
						selector.attr("src", "../image/screp_on.png");
					} else{
						selector.attr("src", "../image/screp_off.png");
					}
				} else {
					$(location).attr("href", "/logon/login.do");
				}
				
			}
		    ,error:function(e) {	// 이곳의 ajax에서 에러가 나면 얼럿창으로 에러 메시지 출력
		    	alert(e.responseText);
				$(location).attr("href", "/logon/login.do");
		    }
		});
	
	});
	
	$(".re_menu_option").click(function(e){
		e.preventDefault();
		var b = $("#memList_" + $(this).attr("id"));
		var url= "/screp/member.do";
		var params = "board_num=" + $(this).attr("id");
		$.ajax({
			type:"post"		// 포스트방식
			,url:url		// url 주소
			,data:params	//  요청에 전달되는 프로퍼티를 가진 객체
			,dataType:"json"
			,success:function(args){	//응답이 성공 상태 코드를 반환하면 호출되는 함수
				var members = args.members;
				$(".re_popup_close").remove();
				for(var idx=0; idx<members.length; idx++) {
					$(".re_popup").append("<li><a href='/profile/myProfile.do?id=" + members[idx] + "' class='re_popup_close'>" + members[idx] + "</a></li>")
				}
				if(members.length==0) {
					$(".re_popup").append("<li><a href='#' class='re_popup_close' onclick='event.preventDefault();'>게시물을 스크랩 해주세요</a></li>");
				}
				
			}
		    ,error:function(e) {	// 이곳의 ajax에서 에러가 나면 얼럿창으로 에러 메시지 출력
		    	alert(e.responseText);
		    }
		});
		b.css({}).show();
	});
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
<style>
#nav_content_screp { position: static; width: 100%; height: 40px; z-index: 999; background-color: white; border-bottom: 1px solid #E6E6E6; padding: 0; }
</style>
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
			</div>
 		<div class="my_profile" id="category_my_profile">	
			<c:forEach var="item" items="${CategoryList}" varStatus="status">
				<div>${item.group1} > ${item.group2} > ${item.group3}</div>
			</c:forEach> 
		</div>

	</div>
	<div id="nav_content_screp">
		<ul id="list_nav">
			<li id="my_content">
				<div class="my_content_screp"><a class="nav_btn" href="#">게 시 물&nbsp;&nbsp;${myCount}</a></div>
			</li>
			<li id="my_screp">
				<div class="my_content_screp"><a class="nav_btn" href="#">스 크 랩&nbsp;&nbsp;${screpCount}</a></div>
			</li>
		</ul>
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
							<a href="#" id="${board.board.board_num}" class="btns_re_item btns_re_items">
		                		<span id="u_ico" class="u_ico">
		                		<c:if test="${board.recommendFlag == 'recommend'}">
		                			<img id="recommend_img${board.board.board_num}" src="../image/recommend_off.png">	                		
		                		</c:if>
		                		<c:if test="${board.recommendFlag == 'nrecommend'}">
		                			<img id="recommend_img${board.board.board_num}" src="../image/recommend_on.png">	                		
		                		</c:if>
                		    </span><em class="u_txt">좋아요</em>
	                 	</a>
	                 	<a href="#" id="${board.board.board_num}" class="btns_re_item re_menu_option">
	                		<em id="u_cnt${board.board.board_num}" class="u_cnt"> ${board.board.recommend_num}</em>
	                	</a>
						<a href="/content/contentForm.do?board_num=${board.board.board_num}&comment=true" class="btns_coment" >
							<span class="u_ico_coment">댓글</span>
							<span class="text_num">${board.commentCount}</span>				
						</a>
		 				<a href="#" id="${board.board.board_num}" class="btns_screp btns_scr_items" >
							<span class="u_ico_screp">
								<c:if test="${board.screpFlag == 'screp'}">
									<img id="screp_img${board.board.board_num}" src="../image/screp_on.png">	  
								</c:if>              
								
								<c:if test="${board.screpFlag == 'nscrep'}">
									<img id="screp_img${board.board.board_num}" src="../image/screp_off.png">	  
								</c:if>		
		                	</span><em class="u_txt">스크렙</em><em id="screp_cnt${board.board.board_num}" class="u_cnt"> ${board.board.screp}</em>
							</a>
							<div id="memList_${board.board.board_num}" class="re_btn_option">
							<div class="ly_dimmed"></div>
							<ul class="re_popup"></ul>
						</div>
					</div>
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