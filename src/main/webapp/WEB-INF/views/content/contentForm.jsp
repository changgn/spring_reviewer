<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<%
	String cp = request.getContextPath();
%>
    
<html>
<head>
<title>게시글</title>
<script>

$(function(){
	var url = "<%=cp%>/content/contentPro";
})

$(function(){
	$(".cont_menu_option").click(function(){
		$(".cont_btn_option").css({
	    }).show();
	});
	$(".cont_btn_option").click(function(){
		$(this).hide();
	});
});
</script>
<script>
$(document).ready(function(){
	if("${error}"=="error") {
		$(location).attr("href", "/main/mainForm.do");
	}
});
$(window).load(function(){
	if("${comment}"=="true") {
		$("#content_comment_write").focus();
		var top = $("#content_comment_area").offset().top;
		$('html, body').animate({ scrollTop : top });
	}
});
$(function(){
	$("#content_btn_comment_write").click(function(){
		if($("#content_comment_write").val()=="") {
			alert("댓글을 입력해 주세요");
			return false;
		} else {
			// 시도테이블의 리스트 가져오기
			var url="<%=cp%>/content/contentPro.do";
			var params="comment_textarea="+$("#content_comment_write").value()
				params += "&board_num="+"${board_num}";
				alert(params);
			 $.ajax({
				type:"post"		// 전송할 data type -> 포스트방식
				,url:url		// url 주소 -> /sidoList.do
				,data:params	//  요청에 전달되는 프로퍼티를 가진 객체
				,dataType:"json" 
				,success:function(args){	//응답이 성공 상태 코드를 반환하면 호출되는 함수
					var write = "";
					write += "<div id='content_comment_wirted_area'>";
					write += "<div id='content_comment_info'>";
					write += "작성자 : <a href="/profile/myProfile.do?id=${comment.id}">${comment.id}</a>&nbsp;&nbsp;&nbsp; 작성시간 : <fmt:formatDate value="${comment.write_date}" pattern="yyyy-MM-dd HH:mm"/>";
					write += "</div>";
					write += "<div id='content_comment_wirted_area'>";
					write += '<textarea id="content_comment_writed" readonly>${comment.content}</textarea>';
					write += "</div>";
					write += "</div>";
					write += '<div id="comment_btn_delete" class="btn_short">';
					write += '<c:if test="${comment.id==id}">';
					write += '<a href="/content/contentPro.do?board_num=${board_num}&comment_num=${comment.coment_num}">삭&nbsp;&nbsp;&nbsp;제</a>';
					write += '</c:if>';
					write += '<c:if test="${comment.id!=id}">';
					write += '<a href="#" id="noDelete">삭&nbsp;&nbsp;&nbsp;제</a>';
					write += '</c:if>';
					write += "</div>";
					 for(var idx=0; idx<args.data.length; idx++) {
						 $("#writed_comment").append("<option value='"+args.data[idx]+"'>"+args.data[idx]+"</option>");
						 //id가 sido인 요소선택
						 //append로 기존 셀렉터로 선택된 요소 다음에 다음내용이 들어감
						 //<option value='0'>서울</option> 이런식으로 sido의 요소안에 자식으로 들어감
		   // args.data[idx] : args 는 function(args)의 인자. data는 controller.java에서 json객체에 넣어준 key(여기서는 list가 값이 된다). [idx]는 list의 몇번쨰 데이터를 가져올지 배열을 나타냄
					 }
				}
			    error:function(e) {	// 이곳의 ajax에서 에러가 나면 얼럿창으로 에러 메시지 출력
			    	alert(e.responseText);
			    }
			});
			$("#content_comment_write_form").submit(); */
		}
	});
	
	$("#noDelete").click(function(){
		alert("댓글 작성자만 삭제할 수 있습니다");
		var top = $("#content_comment_area").offset().top;
		$('html, body').animate({ scrollTop : top });
	});
});
</script>

<style>

</style>
</head>
<body>
<div id="content_area">
	<div class="content_wrap">
		<div class="content_first">	
			<div class="cont_writer">
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
						<ul class="cont_popup">
							<li>
								<a href="/content/reportPro.do?board_num=${board.board_num}" class="cont_popup_close" >이 게시글 신고</a>
							</li>
						<c:if test="${board.id == id}">						
							<li>
								<a href="/content/deleteContent.do?id=${board.id}&board_num=${board.board_num}" class="cont_popup_close" >이 게시글 삭제</a>
							</li>
						</c:if>
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
							<div class="content_photo"><img src="${photo.realPath}"></div>
						</c:forEach>
	            </span>
	      	</span>
       	
       	<div class="cont_category_info">
       		<p id="cont_category_info_f">${category.group1} > ${category.group2} > ${category.group3}</p>
       	</div>
       	<div class="cont_btns">
       		<div class="cont_btns_wrap">
				<div class="btns_re">
					<a href="/recommend/recommendPro.do?board_num=${board.board_num}" class="btns_re_item">
                		<span class="u_ico"></span><em class="u_txt">좋아요</em><em class="u_cnt"> ${board.recommend_num}</em>
                 	</a>
				</div>
				<a href="/content/contentForm.do?board_num=${board.board_num}&comment=true" class="btns_coment" >
					<span class="u_ico_coment">댓글</span>
					<span class="text_num">${commentCount}</span>				
				</a>
				<!-- <a href="#" class="btns_screp" >
					<span class="u_ico_screp">스크렙</span>
					<span class="text_num">19</span>
				</a> -->
       		</div>
       	</div>
	</div>
</div>
<!-- 댓글 -->
<div id="content_comment_area">
	댓글(${commentCount})
	<c:if test="${login_status==0 || login_status==1}">
		<div class="size_content">
			<form id="content_comment_write_form" method="post" action="/content/contentPro.do?board_num=${board_num}">
				<div id="content_comment_wirte_area">
					<textarea id="content_comment_write" name="comment_textarea" ></textarea>
				</div>
			</form>
			<div id="content_btn_comment_write" class="btn_short"><a href="#">작&nbsp;&nbsp;&nbsp;성</a></div>
		</div>
	</c:if>
	<c:forEach var="comment" items="${commentList}">
		<div id="writed_comment" class="size_content">
			<div id="content_comment_wirted_area" >
				<div id="content_comment_info">
					작성자 : <a href="/profile/myProfile.do?id=${comment.id}">${comment.id}</a>&nbsp;&nbsp;&nbsp; 작성시간 : <fmt:formatDate value="${comment.write_date}" pattern="yyyy-MM-dd HH:mm"/>
				</div>
				<div id="content_comment_wirted_area">
					<textarea id="content_comment_writed" readonly>${comment.content}</textarea>
				</div>
			</div>
			<div id="comment_btn_delete" class="btn_short">
				<c:if test="${comment.id==id}">
					<a href="/content/contentPro.do?board_num=${board_num}&comment_num=${comment.coment_num}">삭&nbsp;&nbsp;&nbsp;제</a>
				</c:if>
				<c:if test="${comment.id!=id}">
					<a href="#" id="noDelete">삭&nbsp;&nbsp;&nbsp;제</a>
				</c:if>
			</div>
		</div>
	</c:forEach>
</div>
</body>
</html>
