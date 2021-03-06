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
	$("body").on("click", ".cont_menu_option", function(e){
		e.preventDefault();
		var a = $("#menu_" + $(this).attr("id"));
		a.show();
	});
	$("body").on("click", ".cont_btn_option", function(e){
		$(this).hide();
	});	
});

$(function(){
	$("#my_content").click(function(){ //Content 페이지 이동 요청
		$(location).attr("href", "/profile/myProfile.do?id=${paramId}");
	});
	$("#my_screp").click(function(){ //Screp 글 목록 페이지 이동 요청
		$(location).attr("href", "/profile/screpList.do?id=${paramId}");
	});
	
	$("#u_photo").change(function(){
		$("#profilePhotoForm").submit();	
	});
	$(".remove_profilePhoto_btn").click(function(event){ // 프로필 사진 삭제 페이지 이동 요청
		event.stopPropagation();
		$(location).attr("href", "/profile/profile_photo_remove.do");
	});
	// 좋아요 버튼
	$("body").on("click", ".btns_re_items", function(e){ 
		e.preventDefault();
		var url= "/recommend/recommend.do"; // 좋아요 클릭 후, 좋아요 상태 처리를 위한 페이지 요청
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
						selector.attr("src", "../image/recommend_off.png"); // 좋아요 상태 
					} else{
						selector.attr("src", "../image/recommend_on.png"); // 좋아요 취소 상태
					}
				} else {
					$(location).attr("href", "/logon/login.do"); // 비로그인 상태, 로그인 페이지로 이동 요청
				}
				
			}
		    ,error:function(e) {	// 이곳의 ajax에서 에러가 나면 얼럿창으로 에러 메시지 출력
		    	alert(e.responseText);
				$(location).attr("href", "/logon/login.do");
		    }
		});
	
	});
	$("body").on("click", ".re_menu_option", function(e){
		e.preventDefault();
		var b = $("#memList_" + $(this).attr("id"));
		var url= "/recommend/member.do";
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
					$(".re_popup").append("<li><a href='#' class='re_popup_close' onclick='event.preventDefault();'>게시물을 추천해 주세요</a></li>");
				}
				
			}
		    ,error:function(e) {	// 이곳의 ajax에서 에러가 나면 얼럿창으로 에러 메시지 출력
		    	alert(e.responseText);
		    }
		});
		b.css({}).show();
	});
	$("body").on("click", ".re_btn_option", function(){
		$(this).hide();
	});	
	
	$("body").on("click", ".btns_scr_items", function(e){
		e.preventDefault();
		var url= "/screp/screp.do"; // 스크렙 버튼, 스크렙 상태 처리를 위한 페이지 요청
		var params = "board_num=" + $(this).attr("id"); // 스크렙 상태처리를 위해 파라미터 값으로 받아올 대상 아이디
		params += "&page=profile"; // 페이지 정보로 받아올 파라미터 값
		params += "&paramId=" + $("#id_profile").text(); //버튼 클릭 후, 페이지이동을 수행하기 위해 받아올 파라미터 대상 아이디 값
		
		$.ajax({
			type:"post"		// 포스트방식
			,url:url		// url 주소
			,data:params	//  요청에 전달되는 프로퍼티를 가진 객체
			,dataType:"json"
			,success:function(args){	//응답이 성공 상태 코드를 반환하면 호출되는 함수
				if(args.error == null){

					var screp_num = args.screp_num;
					var screpFlag = args.screpFlag;
					var screpCount = args.screpCount;
					var board_num = args.board_num;
					var selector = $("#screp_img" + board_num); // 해당 게시물의 스크랩 상태
					var selector2 = $("#screp_cnt" + board_num); // 해당 게시물의 스크랩 수
					selector2.text(" " + screp_num);
					$("#screpCount").text(screpCount);

				if(screpFlag == 'screp'){
						selector.attr("src", "../image/screp_on.png");
					} else {
						selector.attr("src", "../image/screp_off.png");
						if($("#pageInfo").val()=="screp") { // 더보기 후 해당 페이지 게시글의 스크랩 상태
							$("#content_" + board_num).remove(); // 더보기 후 해당 페이지 이동, 더보기 아이콘이 사라지는 상태
						}
					}
				} else {
					$(location).attr("href", "/logon/login.do"); // (에러가 있을 경우) 비로그인시, 로그인 페이지로 이동요청
				}
				
			}
		    ,error:function(e) {	// 이곳의 ajax에서 에러가 나면 얼럿창으로 에러 메시지 출력
		    	alert(e.responseText);
				$(location).attr("href", "/logon/login.do"); 
		    }
		});
	
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
				
				if(follow == 'follow'){ // 팔로우 상태
					$(".follow_btn").attr("id", "unfollow"); // 버튼 클릭 후 언팔로우 상태 이미지로 변환
					$(".follow_btn").css("background-image", "url('../image/icon_36.png')");
				} else{ // 언팔로우 상태
					$(".follow_btn").attr("id", "follow"); // 버튼 클릭 후 팔로우 상태 이미지로 변환
					$(".follow_btn").css("background-image", "url('../image/icon_35.png')");
				}
				
				$("#follower_profile a").text("팔로워  " + followerCount + " >"); // 대상 아이디의 팔로워 수
			}
		    ,error:function(e) {	// 이곳의 ajax에서 에러가 나면 얼럿창으로 에러 메시지 출력
		    	alert(e.responseText);
		    }
		});
	
	});
	$("#secret_content").click(function(e){
		e.preventDefault();
		var url= "/content/secret.do"; // 게시글을 숨기기 위한 페이지 요청
		var params = "board_num=" + $(this).attr("id");
		$.ajax({
			type:"post"		// 포스트방식
			,url:url		// url 주소
			,data:params	//  요청에 전달되는 프로퍼티를 가진 객체
			,dataType:"json"
			,success:function(args){	//응답이 성공 상태 코드를 반환하면 호출되는 함수
				var nrecommend = args.recommend;
						
			}
		    ,error:function(e) {	// 이곳의 ajax에서 에러가 나면 얼럿창으로 에러 메시지 출력
		    	alert(e.responseText);
		    }
		});
	
	});
});

$(function() {
	$(".list_view_more").click(function(e) { 
    	e.preventDefault();
    	var url = "/profile/myProfileAjax.do";
    	var params = "lastBoard_num=" + $("#lastBoard_num").val();  // 현재 리스트의 마지막글 번호를 가져온다.
    	params += "&paramId=" + $("#id_profile").text(); // 프로필 페이지로 이동시키기 위한 대상 아이디의 파라미터 값  
    	if($("#pageInfo").val()=="screp") {
    		params += "&pageInfo=screp"; // 스크렙 게시글 페이지 정보에 들어갈 파라미터 값
    	}
    	$.ajax({
              type: "post",
              url: url,     	// 더보기 눌렀을때 데이터리스트를 html 형태로 만들어서 현재 리스트페이지로 보내준다.
              data:params,   	// 현재 리스트에 뿌려져있는 마지막 글 번호를 넣어준다. 그래야지 리스트의 마지막글 다음부터의 리스트를 가져온다.
              dataType:"json",
              beforeSend:  function() {
             	 $(".view_more").append('<img id="loadingimg" src="../image/loading.gif" />');  // 로딩 진행줄일때 .gif로 로딩중이라는거 표시 
	          },
	          success: function(args){
	        	  var allBoardList = args.allBoardList;
	        	  var view = "";
	        	  var lastBoard_num = $("#lastBoard_num").val();
	        	  $("#loadingimg").remove();
	        	  for(var idx=0; idx<allBoardList.length; idx++) {
	        		  view += '<div id="content_' + allBoardList[idx].board.board_num + '" class="content_wrap"><div class="content_first"><div class="cont_writer">';
	        		  if(allBoardList[idx].profilePhoto != null){
		        	  	  view += '<a href="/profile/myProfile.do?id=' + allBoardList[idx].board.id +'" class="profile_photo"> <span class="profile_thumb"> <img src="' + allBoardList[idx].profilePhoto.realPath + '"> <span class="profile_thumb_mask"></span></span></a>';
	        		  }
					  view += '<a href="/profile/myProfile.do?id=' + allBoardList[idx].board.id +'" class="cont_writer_id">'+ allBoardList[idx].board.id +'</a>';
		        	  view += '<div class="cont_wdate">' + allBoardList[idx].date + '</div>';
		        	  view += '<div class="cont_menu">';
		        	  view += '<a href="#" id="' + allBoardList[idx].board.board_num + '" class="cont_menu_option"><span id="cont_btn_menu">옵션</span></a>';
		        	  view += '<div id="menu_'+ allBoardList[idx].board.board_num +'" class="cont_btn_option"><div class="ly_dimmed"></div>';
		        	  view += '<ul class="cont_popup ul_list">';
		        	  view += '<li><a href="/content/reportPro.do?board_num='+ allBoardList[idx].board.board_num + '" class="cont_popup_close" >이 게시글 신고</a></li>';
		        	  if(allBoardList[idx].board.id == '${id}' || "${login_status}"==0){
						  view += '<li><a href="/content/deleteContent.do?id=' + allBoardList[idx].board.id +'&board_num='+ allBoardList[idx].board.board_num + '" class="cont_popup_close" >이 게시글 삭제</a></li>';
		        	  }
		        	  view += '</ul></div></div></div></div>';
		        	  view += '<div class="content_second"><span class="content_view"><span><pre id="pre_' + allBoardList[idx].board.board_num + '"> ' + allBoardList[idx].board.content +'</pre>';
		        	  if(allBoardList[idx].contentFlag == true){
		        		  view += '<span class="cont_theview"><span>...</span><a href="/content/contentForm.do?board_num=' + allBoardList[idx].board.board_num + '" class="btn_view_more">더보기</a></span>';
		        	  }
		        	  view +='</span></span></div>';
		        	  if(allBoardList[idx].photo != null){
		        		  view += '<a href="/content/contentForm.do?board_num=' + allBoardList[idx].board.board_num + '" class="item_info_wrap">';
		        		  view += '<span class="item_cont" title="컨텐츠 상세페이지">';
	        			  view += '<span class="item_thumb"><img class="list_photo" src="' + allBoardList[idx].photo.realPath + '"><span class="thumb_mask_bottom"></span></span></span></a>';
		        	  }
		        	  view += '<div class="cont_category_info"><p id="cont_category_info_f">' + allBoardList[idx].category.group1 + '>' + allBoardList[idx].category.group2 + '>' + allBoardList[idx].category.group3 +'</p></div>';
		        	  view += '<div class="cont_btns"> <div class="cont_btns_wrap">';
		        	  if("${login_status}"!=0 && "${login_status}"!=1){
		        		  view += '<div class="btns_re">';
		        		  view += '<a href="/logon/login.do" id="'+ allBoardList[idx].board.board_num +'" class="btns_re_item"><span id="u_ico" class="u_ico"><img src="../image/recommend_on.png"></span><em class="u_txt">좋아요</em></a>';
		        		  view += '&nbsp;';
		        		  view += '<a href="#" id="'+ allBoardList[idx].board.board_num +'" class="btns_re_item re_menu_option">';
		        		  view += '<em id="u_cnt'+ allBoardList[idx].board.board_num +'" class="u_cnt">'+ allBoardList[idx].board.recommend_num +'</em></a>';
		        		  view += '<div id="memList_'+ allBoardList[idx].board.board_num +'" class="re_btn_option">';	
		        		  view += '<div class="ly_dimmed"></div>';
		        		  view += '<ul class="re_popup ul_list"></ul>';
		        		  view += '</div></div>'
		        	  } else{
		        		 view += '<div class="btns_re">';
		        		 view += '<a href="#" id="'+ allBoardList[idx].board.board_num +'" class="btns_re_item btns_re_items">';
		        		 view += '<span id="u_ico" class="u_ico">';
		        		 if(allBoardList[idx].recommendFlag == 'recommend'){
		        			 view += '<img id="recommend_img'+ allBoardList[idx].board.board_num +'" src="../image/recommend_off.png">';
		        		 }else{
		        			 view += '<img id="recommend_img'+ allBoardList[idx].board.board_num +'" src="../image/recommend_on.png">';
		        		 }
		        		 view += '</span><em class="u_txt">좋아요</em></a>'; 
		        		 view += '&nbsp;';
		        		 view += '<a href="#" id="'+ allBoardList[idx].board.board_num +'" class="btns_re_item re_menu_option">';
		        		 view += '<em id="u_cnt'+ allBoardList[idx].board.board_num +'" class="u_cnt">'+ allBoardList[idx].board.recommend_num +'</em></a>';
		        		 view += '<div id="memList_'+ allBoardList[idx].board.board_num + '" class="re_btn_option">';
		        		 view += '<div class="ly_dimmed"></div>';
	        		     view += '<ul class="re_popup ul_list"></ul>';
	        		     view += '</div></div>';
		        	  }
		        	  view += '<a href="/content/contentForm.do?board_num='+ allBoardList[idx].board.board_num +'&comment=true" class="btns_coment">';
		        	  view += '<span class="u_ico_coment">댓글</span>';
		        	  view += '<span class="text_num">'+ allBoardList[idx].commentCount +'</span></a>';

		        	  if("${login_status}"==0 || "${login_status}"==1) {
			        	  view += '<a href="#" id="' + allBoardList[idx].board.board_num + '" class="btns_screp btns_scr_items" >';
			        	  view += '<span class="u_ico_screp">';
			        	  if(allBoardList[idx].screpFlag == 'screp') {
			        		  view += '<img id="screp_img' + allBoardList[idx].board.board_num + '" src="../image/screp_on.png">';
			        	  } else {
			        		  view += '<img id="screp_img' + allBoardList[idx].board.board_num + '" src="../image/screp_off.png">';
			        	  }
			        	  view += '</span><em class="u_txt">스크렙</em><em id="screp_cnt' + allBoardList[idx].board.board_num + '" class="u_cnt"> ' + allBoardList[idx].board.screp + '</em>';
			        	  view += '</a>';
		        	  }
		        	  view += '</div></div></div>';
		        	  lastBoard_num = allBoardList[idx].board.board_num;
	        	  } 
	        	  view += '<input type="hidden" id="lastBoard_num" value="' + lastBoard_num + '" />';
	        	  $("#lastBoard_num").remove();
	        	  $("#board_profile").append(view);
	        	  
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
			<form action="/profile/profile_photo.do" id="profilePhotoForm" method="post" enctype="multipart/form-data"> <!-- 동작 : Controller에 해당 페이지 요청 -> 컨트롤러 해당 요청을 수행하고 에서 JSP 페이지로 이동  -->
				<div class="profile_photo2" id="file_input_hidden">
					<c:if test="${id==paramId }">
						<input type="file" id="u_photo" name="u_photo" class="user_photo" maxlength="5" onchange="check();">
					</c:if>
					<a href="#">
						<span class="profile_thumb2" >
		                    <img id="myProfilePhoto" src="${myProfilePhoto.realPath}">
		                    <span class="profile_thumb_mask2"></span>
		                    <c:if test="${id==paramId && (login_status==0 || login_status==1)}"> <!-- 로그인 상태 & 자신의 아이디일 경우에만, 프로필 사진을 초기화 시킬 수 있다. -->
								<span id="remove_profilePhoto" class="remove_profilePhoto_btn" style="background-image: url('../image/X2.png')"></span>
							</c:if>
							<c:if test="${id!=paramId && (login_status==0 || login_status==1)}"> <!-- 로그인 상태일 경우에만, 대상아이디(상대방 아이디)에 대해 팔로우 언팔로우를 수행할 수 있다.. -->
								<c:if test="${followCheck == true}"> 
									<span id="unfollow" class="follow_btn" style="background-image: url('../image/icon_36.png')"></span>
								</c:if>
								<c:if test="${followCheck == false}">
									<span id="follow" class="follow_btn" style="background-image: url('../image/icon_35.png')"></span>
								</c:if>
							</c:if>
		              	</span>
	              	</a>
				</div>
			</form>

			<div class="my_profile" id="id_profile">${paramId}</div>

		</div>
		<div id="my_profile_follow">
			<div class="follow_profile" id="follower_profile"><a href="/follow/follower.do?id=${paramId}">팔로워  ${followerCount } ></a></div> <!-- 해당아이디의 팔로워 수보기 페이지로 이동 -->
			<div class="follow_profile" id="following_profile"><a href="/follow/follewing.do?id=${paramId}">팔로잉  ${followingCount } ></a></div> <!-- 해당아이디의 팔로잉 수보기 페이지로 이동 -->
			</div>
 		<div class="my_profile" id="category_my_profile">	<!-- 마이프로필 페이지 에서 내가 추가한 카테고리를 보여 준다. -->
			<c:forEach var="item" items="${CategoryList}" varStatus="status">
				<div>${item.group1} > ${item.group2} > ${item.group3}</div>
			</c:forEach> 
		</div>

	</div>
	<div id="nav_content_screp">
		<ul id="list_nav" class="ul_list">
			<li id="my_content"> <!-- Content 페이지로 요청, 내가 작성한 게시글 목록을 보여 준다. -->
				<div class="my_content_screp"><a class="nav_btn" href="#">게 시 글&nbsp;&nbsp;<span id="myCount">${myCount}</span></a></div>
			</li>
			<li id="my_screp"> <!-- Screp 글 목록 페이지 이동 요청, 내가 스크렙한 게시글 목록을 보여준다. -->
				<div class="my_content_screp"><a id="nav_btn_screp" class="nav_btn" href="#">스 크 랩&nbsp;&nbsp;<span id="screpCount">${screpCount}</span></a></div>
			</li>
		</ul>
	</div>
	<div class="my_profile" id="board_profile">
		<c:forEach var="board" items="${allBoardList}"> <!-- 화면에 보여질 게시글 정보 -->
			<div id="content_${board.board.board_num}" class="content_wrap"> 
				<div class="content_first">	
					<div class="cont_writer">
						<c:if test="${board.profilePhoto.realPath != null}"> <!-- 프로필 사진의 경로가 있을 경우(프로필 사진이 등록된 경우) -->
						<a href="/profile/myProfile.do?id=${board.board.id}" class="profile_photo"> <!-- 대상 아이디의 프로필 페이지로 이동  -->
							<span class="profile_thumb">
							
							
			                    <img src="${board.profilePhoto.realPath}"> <!-- 해당 경로안에 있는 이미지를 불러온다. -->
			                    <span class="profile_thumb_mask"></span>
		               		</span>
						</a>
						</c:if>
						<a href="/profile/myProfile.do?id=${board.board.id}" class="cont_writer_id">${board.board.id}</a>
						<div class="cont_wdate">
							<fmt:formatDate value="${board.board.write_date}" pattern="yyyy-MM-dd HH:mm"/>
						</div>
						<div class="cont_menu">
							<a href="#" id="${board.board.board_num}" class="cont_menu_option"> <!-- 화면에 보여지는 옵션 버튼  -->
								<span id="cont_btn_menu">옵션</span>						
							</a>
							 <div id="menu_${board.board.board_num}" class="cont_btn_option">
								<div class="ly_dimmed"></div>
								<ul class="cont_popup ul_list">
									<c:choose>
										<c:when test="${id != 'admin'}">
											<c:if test="${board.board.id != id}"> <!-- 옵션 클릭 후 해당 조건에 따라 보여지는 게시글삭제, 게시글 수정 버튼 / 클릭 후 동작 수행 -->
												<li>
													<a href="/content/reportPro.do?board_num=${board.board.board_num}" class="cont_popup_close" >이 게시글 신고</a>
												</li>
											</c:if>
											<c:if test="${board.board.id == id}">						
												<li>
													<a href="/content/deleteContent.do?id=${board.board.id}&board_num=${board.board.board_num}" class="cont_popup_close" >이 게시글 삭제</a>
												</li>
											</c:if>
										</c:when>
										<c:otherwise>						
											<li>
												<a href="/content/deleteContent.do?id=${board.board.id}&board_num=${board.board.board_num}" class="cont_popup_close" >이 게시글 삭제</a>
											</li>
										</c:otherwise>
									</c:choose>
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
						<c:if test="${login_status!=0 && login_status!=1}">
							<div class="btns_re">
								<a href="/logon/login.do" id="${board.board.board_num}" class="btns_re_item">
			                		<span id="u_ico" class="u_ico"><img src="../image/recommend_on.png"></span><em class="u_txt">좋아요</em>
			                 	</a>
			                 	<a href="#" id="${board.board.board_num}" class="btns_re_item re_menu_option">
				              		<em id="u_cnt${board.board.board_num}" class="u_cnt">${board.board.recommend_num}</em>
				              	</a>
		           				<div id="memList_${board.board.board_num}" class="re_btn_option">
									<div class="ly_dimmed"></div>
									<ul class="re_popup ul_list"></ul>
								</div>
							</div>
						</c:if>
						<c:if test="${login_status==0 || login_status==1}">
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
			                		<em id="u_cnt${board.board.board_num}" class="u_cnt">${board.board.recommend_num}</em>
			                	</a>
			                	<div id="memList_${board.board.board_num}" class="re_btn_option">
									<div class="ly_dimmed"></div>
									<ul class="re_popup ul_list"></ul>
								</div>
							</div>
						</c:if>
						<a href="/content/contentForm.do?board_num=${board.board.board_num}&comment=true" class="btns_coment" >
							<span class="u_ico_coment">댓글</span>
							<span class="text_num">${board.commentCount}</span>				
						</a>
						<c:if test="${login_status==0 || login_status==1}">
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
						</c:if>
		       		</div>
		       	</div>
			</div>
			<c:set var="lastBoard_num" value="${board.board.board_num}" />
		</c:forEach>
		<input type="hidden" id="lastBoard_num" value="${lastBoard_num}" />
	</div>
	<input type="hidden" id="pageInfo" value="${pageInfo}" />
	<c:if test="${boardCount >= 5}">
	<div class="view_more">
 		<a href="#" class="list_view_more">
 			<span class="ico_plus"><img src="../image/plus.png"></span><span class="txt_view_more">더 많은 리뷰 보기</span>
 		</a>
    </div>
    </c:if>
	<c:if test="${login_status==0 || login_status==1}">
		<div class="btn_posting_wrap">
			<a href="/write/writeForm.do" class="btn_posting">
				<span class="u_vc">글쓰기</span>
			</a>
		</div>
	</c:if>
<c:if test="${id==paramId }">
<script>
document.querySelector('.profile_thumb2').addEventListener('click', function(e) {
 	document.querySelector('.user_photo').click();
}, false);
</script> 
</c:if>
</body>
</html>