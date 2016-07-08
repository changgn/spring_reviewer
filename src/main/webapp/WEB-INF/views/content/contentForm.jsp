<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<html>
<head>
<title>게시글</title>
<script src="https://code.jquery.com/jquery-2.2.4.min.js"></script>
<script>
$(document).ready(function(){
	if("${error}"=="error") {
		$(location).attr("href", "/main/main.do");
	}
	$(".cont_popup li").mouseover(function(){
		$(this).css("background-color","#F6F6F6");
	});
	$(".cont_popup li").mouseleave(function(){
		$(this).css("background-color","white");
	});
	$(document).mousedown(function(e) {
		$('.comment_menu').each(function() {
			if( $(this).css('display') == 'block' ) {
				var objPos = $(this).offset();
				objPos.right = (objPos.left + $(this).width());
				objPos.bottom = (objPos.top + $(this).height());
				if( e.pageX < objPos.left || e.pageX > objPos.right
				 || e.pageY < objPos.top || e.pageY > objPos.bottom ) {
					$(this).slideUp('fast');
				}
			}
		});
	});
});
$(document).on("mouseover", ".re_popup li", function(){
	$(this).css("background-color","#F6F6F6");
});
$(document).on("mouseleave", ".re_popup li", function(){
	$(this).css("background-color","white");
});
$(document).on("mouseover", ".comment_menu div", function(){
	$(this).css("background-color","#F6F6F6");
});
$(document).on("mouseleave", ".comment_menu div", function(){
	$(this).css("background-color","white");
});
$(window).load(function(){
	if("${comment}"=="true") {
		$("#content_comment_write").focus();
		var top = $("#content_comment_area").offset().top;
		$('html, body').animate({ scrollTop : top });
	}
});

$(function(){

	$(".cont_menu_option").click(function(e){
		e.preventDefault();
		$(".cont_btn_option").show();
	});
	$(".cont_btn_option").click(function(){
		$(this).hide();
	});

	$("body").on("click", ".comment_menu_btn", function(e){
		e.preventDefault();
		var top = $(this).find("span").find("span").offset().top + 27;
		var left = $(this).find("span").find("span").offset().left - 37;
		var selector = $("#comment_menu_" + $(this).attr("id"));
		
		selector.css("top", top);
		selector.css("left", left);
		selector.slideDown("fast");
	});
	
	
	$(".content_photo_a").click(function(e){
		e.preventDefault();
		$(".view_content_photo_list").show();
		var src = $(this).find("img").attr("src");
		$(".view_content_photo_img[src='" + src +"']").parent().css("z-index", "10001");
		$(".view_content_photo_img[src='" + src +"']").parent().addClass("selected");
		
	});
	$(".view_content_photo_list").click(function(){
		$(".view_content_photo").css("z-index", "10000");
		$(".view_content_photo").removeClass("selected");
		$(this).hide();
	});
	$("#left_btn_a").click(function(e){
		e.preventDefault();
		e.stopPropagation();

		var maxlength = Number($("#ul_view_content_photo_list li:last-child img").attr("id"));
		var currlength = Number($("li[class='view_content_photo selected'] img").attr("id"));

		if(currlength > 1) {
			$(".view_content_photo").css("z-index", "10000");
			$(".view_content_photo").removeClass("selected");
			$("li img[id='" + (currlength-1) + "']").parent().css("z-index", "10001");
			$("li img[id='" + (currlength-1) + "']").parent().addClass("selected");
		} else {
			$(".view_content_photo").css("z-index", "10000");
			$(".view_content_photo").removeClass("selected");
			$("li img[id='" + maxlength + "']").parent().css("z-index", "10001");
			$("li img[id='" + maxlength + "']").parent().addClass("selected");
		}
	});
	$("#right_btn_a").click(function(e){
		e.preventDefault();
		e.stopPropagation();
		
		var maxlength = Number($("#ul_view_content_photo_list li:last-child img").attr("id"));
		var currlength = Number($("li[class='view_content_photo selected'] img").attr("id"));
		
		if(currlength < maxlength) {
			$(".view_content_photo").css("z-index", "10000");
			$(".view_content_photo").removeClass("selected");
			$("li img[id='" + (currlength+1) + "']").parent().css("z-index", "10001");
			$("li img[id='" + (currlength+1) + "']").parent().addClass("selected");
		} else {
			$(".view_content_photo").css("z-index", "10000");
			$(".view_content_photo").removeClass("selected");
			$("li img[id='1']").parent().css("z-index", "10001");
			$("li img[id='1']").parent().addClass("selected");
		}
	});
	
	$(".re_menu_option").click(function(e){
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
	$(".re_btn_option").click(function(){
		$(this).hide();
	});	
	
	// 좋아요
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
	
	$("#content_btn_comment_write").click(function(e){
		// 스크롤 방지
		e.preventDefault();
		
		//만약 댓글이 입력이 안되 있을경우 
		if($("#content_comment_write").val()=="") {
			alert("댓글을 입력해 주세요");
			return false;
		} else {
 			var url="comment.do";
			var params = "comment_textarea="+$("#content_comment_write").val();
				params += "&board_num="+'${board.board_num}';
				
				$.ajax({
				type:"post"		// 전송할 data type -> 포스트방식
				,url:url		// url 주소 -> /sidoList.do
				,data:params	//  요청에 전달되는 프로퍼티를 가진 객체
				,dataType:"json" 
				,success:function(args){	//응답이 성공 상태 코드를 반환하면 호출되는 함수
	 				var writer = "";
					writer += '<div id="writed_comment" class="size_content">';
					writer += '<div id="content_comment_writed_area" >';
					writer += '<div id="content_comment_info">';
					writer += '작성자 : <a href="/profile/myProfile.do?id=' + args.data.id + '">' + args.data.id + '</a>&nbsp;&nbsp;&nbsp; 작성시간 : ' + args.date;
					if(args.data.id=='${id}'){
						writer += '<a id="' + args.data.comment_num + '" class="comment_menu_btn" href="#"><span id="comment_menu_btn_img_rap"><span id="comment_menu_btn_img">메 뉴</span></span></a>';
					}
					writer += '</div>';
					writer += '<div id="content_comment_writed_area">';
					writer += '<textarea id="content_comment_writed_' + args.data.comment_num + '" class="content_comment_writed" readonly>' + args.data.content + '</textarea>';
					writer += '</div>';
					writer += '</div>';
					
					writer += '<div id="comment_menu_' + args.data.comment_num + '" class="comment_menu">';
					writer += '<div><a id="' + args.data.comment_num + '" class="comment_mod" href="#">수 정</a></div>';
					writer += '<div><a href="/content/commentdel.do?board_num=' + args.data.board_num + '&comment_num=' + args.data.comment_num + '">삭 제</a></div>';
					writer += '</div>';

					writer += '</div>';
					$("#comment_cnt").text("댓글(" + args.cnt + ")");
					$(".text_num").text(args.cnt);
					
					$("#writed_comment_area").prepend(writer);
					$("#content_comment_write").val("");
					$("#content_comment_write").focus();
				}
				
			}); 
			/* $("#content_comment_write_form").submit(); */
		}
	});
	
	
	$("body").on("click", ".comment_mod", function(e){
		e.preventDefault();
		var selector = $("#content_comment_writed_" + $(this).attr("id"));
		selector.removeAttr("readonly");
		selector.focus();
		$(this).text("완 료");
		$(this).attr("class", "comment_mod_ok");
	});

	$("body").on("click", ".comment_mod_ok", function(e){
		e.preventDefault();
		var selector = $("#content_comment_writed_" + $(this).attr("id"));
		
		var url= "/content/commentMod.do";
		var params = "comment_num=" + $(this).attr("id");
		params += "&content=" + selector.val();
		
		$.ajax({
			type:"post"		// 포스트방식
			,url:url		// url 주소
			,data:params	//  요청에 전달되는 프로퍼티를 가진 객체
			,dataType:"json"
			,success:function(args){	//응답이 성공 상태 코드를 반환하면 호출되는 함수
				var comment = args.comment;
				selector.text(comment.content);
				
			}
		    ,error:function(e) {	// 이곳의 ajax에서 에러가 나면 얼럿창으로 에러 메시지 출력
		    	alert(e.responseText);
		    }
		});

		$('.comment_menu').slideUp('fast');
		selector.attr("readonly", "true");
		$(this).text("수 정");
		$(this).attr("class", "comment_mod");
	});	
	
	$("#noDelete").click(function(){
		alert("댓글 작성자만 삭제할 수 있습니다");
		var top = $("#content_comment_area").offset().top;
		$('html, body').animate({ scrollTop : top });
	});
	
	$("#noModify").click(function(){
		alert("댓글 작성자만 수정할 수 있습니다");
		var top = $("#content_comment_area").offset().top;
		$('html, body').animate({ scrollTop : top });
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
});

</script>
</head>
<body>
<div id="content_area">
	<div class="content_wrap">
		<div class="content_first">	
			<div class="cont_writer">
				<c:if test="${profilePhoto.realPath != null}">
				<a href="/profile/myProfile.do?id=${board.id}" class="profile_photo">
					<span class="profile_thumb">
	                    <img src="${profilePhoto.realPath}">
	                    <span class="profile_thumb_mask"></span>
               		</span>
				</a>
				</c:if>
				<a href="/profile/myProfile.do?id=${board.id}" class="cont_writer_id">${board.id}</a>
				<div class="cont_wdate">
					<fmt:formatDate value="${board.write_date}" pattern="yyyy-MM-dd HH:mm"/>
				</div>
				<div class="cont_menu">
					<a href="#" class="cont_menu_option">
						<span id="cont_btn_menu">옵션</span>						
					</a>
					 <div class="cont_btn_option">
						<div class="ly_dimmed"></div>
						<ul class="cont_popup ul_list">
							<c:choose>
								<c:when test="${id != 'admin' }">
									<c:if test="${board.id != id}">
										<li>
											<a href="/content/reportPro.do?board_num=${board.board_num}" class="cont_popup_close" >이 게시글 신고</a>
										</li>
									</c:if>
									<c:if test="${board.id == id}">						
										<li>
											<a href="/content/deleteContent.do?id=${board.id}&board_num=${board.board_num}" class="cont_popup_close" >이 게시글 삭제</a>
										</li>
									</c:if>
								</c:when>
								<c:otherwise>						
									<li>
										<a href="/content/deleteContent.do?id=${board.id}&board_num=${board.board_num}" class="cont_popup_close" >이 게시글 삭제</a>
									</li>
								</c:otherwise>
							</c:choose>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div class="content_second">
			<span class="content_view_sp">
				<span><pre>${board.content}</pre>
				</span>
			</span>
		</div>
 
	        <span class="item_cont" title="컨텐츠 상세페이지">
	            <span class="item_thumb">
	                	<c:forEach var="photo" items="${photoList}">
							<div class="content_photo"><a class="content_photo_a" href="#"><img class="content_photo_img" src="${photo.realPath}"></a></div>
						</c:forEach>
	            </span>
	      	</span>
       	
       	<div class="cont_category_info">
       		<p id="cont_category_info_f">${category.group1} > ${category.group2} > ${category.group3}</p>
       	</div>
       	<div class="cont_btns">
       		<div class="cont_btns_wrap">
       			<c:if test="${login_status!=0 && login_status!=1}">
					<div class="btns_re">
						<a href="/logon/login.do" id="${board.board_num}" class="btns_re_item">
	                		<span id="u_ico" class="u_ico"><img src="../image/recommend_on.png"></span><em class="u_txt">좋아요</em><em id="u_cnt${board.board_num}" class="u_cnt"> ${board.recommend_num}</em>
	                 	</a>
					</div>
				</c:if>
				<c:if test="${login_status==0 || login_status==1}">
					<div class="btns_re">
						<a href="#" id="${board.board_num}" class="btns_re_item btns_re_items">
	                		<span id="u_ico" class="u_ico">
		                		<c:if test="${recommendFlag == 'recommend'}">
		                			<img id="recommend_img${board.board_num}" src="../image/recommend_off.png">	                		
		                		</c:if>
		                		<c:if test="${recommendFlag == 'nrecommend'}">
		                			<img id="recommend_img${board.board_num}" src="../image/recommend_on.png">	                		
		                		</c:if>
	               		    </span><em class="u_txt">추천</em>
	                 	</a>
	                 	<a href="#" id="${board.board_num}" class="btns_re_item re_menu_option">
	                		<em id="u_cnt${board.board_num}" class="u_cnt"> ${board.recommend_num}</em>
	                	</a>
	                 	<div id="memList_${board.board_num}" class="re_btn_option">
							<div class="ly_dimmed"></div>
							<ul class="re_popup"></ul>
						</div>
					</div>
				</c:if>
				<a href="/content/contentForm.do?board_num=${board.board_num}&comment=true" class="btns_coment" >
					<span class="u_ico_coment">댓글</span>
					<span class="text_num">${commentCount}</span>
				</a>
				<a href="#" id="${board.board_num}" class="btns_screp btns_scr_items" >
					<span class="u_ico_screp">
						<c:if test="${screpFlag == 'screp'}">
							<img id="screp_img${board.board_num}" src="../image/screp_on.png">	  
						</c:if>              
						
						<c:if test="${screpFlag == 'nscrep'}">
							<img id="screp_img${board.board_num}" src="../image/screp_off.png">	  
						</c:if>		
                	</span><em class="u_txt">스크렙</em><em id="screp_cnt${board.board_num}" class="u_cnt"> ${board.screp}</em>
				</a>
       		</div>
       	</div>
	</div>
</div>
<!-- 댓글 -->
<div id="content_comment_area">
	<div id="comment_cnt">댓글(${commentCount})</div>
	<c:if test="${login_status==0 || login_status==1}">
		<div class="size_content">
			<form id="content_comment_write_form" method="post" action="/content/contentPro.do?board_num=${board_num}">
				<div id="content_comment_write_area">
					<textarea id="content_comment_write" name="comment_textarea" ></textarea>
				</div>
			</form>
			<div id="content_btn_comment_write" class="btn_short"><a href="#">작&nbsp;&nbsp;&nbsp;성</a></div>
		</div>
	</c:if>
	<div id="writed_comment_area">
	<c:forEach var="comment" items="${commentList}">
		<div id="writed_comment" class="size_content">
			<div id="content_comment_writed_area" >
				<div id="content_comment_info">
					작성자 : <a href="/profile/myProfile.do?id=${comment.id}">${comment.id}</a>&nbsp;&nbsp;&nbsp; 작성시간 : <fmt:formatDate value="${comment.write_date}" pattern="yyyy-MM-dd HH:mm"/>
					<c:if test="${comment.id == id || login_status==0}">
						<a id="${comment.comment_num}" class="comment_menu_btn" href="#"><span id="comment_menu_btn_img_rap"><span id="comment_menu_btn_img">메 뉴</span></span></a>
					</c:if>
				</div>
				<div id="content_comment_writed_area">
					<textarea id="content_comment_writed_${comment.comment_num}" class="content_comment_writed" readonly>${comment.content}</textarea>
				</div>
			</div>

			<div id="comment_menu_${comment.comment_num}" class="comment_menu">
				<c:if test="${login_status !=0}">
					<div><a id="${comment.comment_num}" class="comment_mod" href="#">수 정</a></div>
				</c:if>
				
				<div><a href="/content/commentdel.do?board_num=${board_num}&comment_num=${comment.comment_num}">삭 제</a></div>
			</div>
		</div>
	</c:forEach>
	</div>
</div>
<div class="view_content_photo_list">
	<div class="ly_dimmed"></div>
	<ul id="ul_view_content_photo_list">
	<c:set var="photo_count" value="0" />
	<c:forEach var="photo" items="${photoList}">
		<c:set var="photo_count" value="${photo_count + 1}" />
		<li class="view_content_photo"><img id="${photo_count}" class="view_content_photo_img" src="${photo.realPath}"></li>
	</c:forEach>
	</ul>
	<a id="left_btn_a" href="#"><span id="left_btn"></span></a>
	<a id="right_btn_a" href="#"><span id="right_btn"></span></a>
</div>
</body>
</html>
