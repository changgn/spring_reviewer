<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ page trimDirectiveWhitespaces="true" %>

<!DOCTYPE html>
<html>
<head>
<link href="../css/style.css" rel="stylesheet" type="text/css">
<script src="https://code.jquery.com/jquery-2.2.3.min.js"></script>
<title>메인페이지</title>
<script>

$(function(){
	var top = 0;
	$(".cont_menu_option").click(function(e){
		e.preventDefault();
		var a = $("#menu_" + $(this).attr("id"));
		a.css({
	    }).show();
	});
	$(".cont_btn_option").click(function(){
		$(this).hide();
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
				var nrecommend = args.recommend;
				var recommendFlag = args.recommendFlag;
				var selector = $("#recommend_img"+args.board_num);
				var selector2 = $("#u_cnt"+args.board_num);
				selector2.text(" " + nrecommend);
				if(recommendFlag == 'recommend'){
					selector.attr("src", "../image/recommend_off.png");
				} else{
					selector.attr("src", "../image/recommend_on.png");
				}
				
			}
		    ,error:function(e) {	// 이곳의 ajax에서 에러가 나면 얼럿창으로 에러 메시지 출력
		    	alert(e.responseText);
		    }
		});
	
	});
	$("#secret_content").click(function(e){
		e.preventDefault();
		var url= "/content/secret.do";
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
    $(".list_view_more").on("click",function(e) { 
    	e.preventDefault();
    	var url = "/main/mainAjax.do";
   		var params = "board_num=" + $(this).attr("id");      // 현재 리스트의 마지막글 번호를 가져온다. 마지막글 번호는 아래 더보기 버튼의 id값이
        $.ajax({
              type: "post",
              url: url,     	// 더보기 눌렀을때 데이터리스트를 html 형태로 만들어서 현재 리스트페이지로 보내준다.
              data:params,   	// 현재 리스트에 뿌려져있는 마지막 글 번호를 넣어준다. 그래야지 리스트의 마지막글 다음부터의 리스트를 가져온다.
              beforeSend:  function() {
             	 $('a.list_view_more').append('<img src="../image/loading.gif" />');    // 로딩 진행줄일때 .gif로 로딩중이라는거 표시 
	          },
	          success: function(html){
	        	  $(".list_view_more").remove(); 
	        	  
	          },
	          error:function(e) {	// 이곳의 ajax에서 에러가 나면 얼럿창으로 에러 메시지 출력
	    		  alert(e.responseText);
		      }
      	});
    
    });
});

</script>

<style>
.profile_thumb{ display: inline-block;
    overflow: hidden;
    position: relative;
    width: 52px;
    height: 52px;
    vertical-align: middle;  
}

.profile_thumb_mask{

    position: absolute;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    z-index: 10;
    background-position: -381px 0;
    background-image: url(https://ssl.pstatic.net/static/m/pholar/img/sp_pholar_160421.png);
    background-repeat: no-repeat;
}

.profile_photo img{
	display: block;
}

.view_more{
    clear: both;
    background-color: #fff;
    text-align: center;
    display: block;
    width: 650px;
    margin: auto;
    
}

.list_view_more{
    border-bottom: 1px solid #e4e4e4;
    display: block;
    height: 90px;
    line-height: 86px;
    font-size: 17px;
    font-weight: bold;
    color: #2f334e;
}

.ico_plus{
	display: inline-block;
    width: 14px;
    height: 14px;
    margin: -2px 6px 0 0;
    background-position: -220px -340px;
    vertical-align: middle;
}
.txt_view_more{

	line-height: 40px;
    font-size: 17px;
    font-weight: bold;
    color: #2f334e;
}
</style>

</head>
<body>
<c:forEach var="board" items="${allBoardList}">
	<div class="content_wrap">
		<div class="content_first">	
			<div class="cont_writer">
				<a href="#" class="profile_photo">
					<span class="profile_thumb">
	                    <img src="../image/5.jpg">
	                    <span class="profile_thumb_mask"></span>
               		</span>
				</a>
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
							<li>
								<a href="#" id="content_secret" class="cont_popup_close" >게시글 숨기기</a>
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
	                		<em id="u_cnt${board.board.board_num}" class="u_cnt"> ${board.board.recommend_num}</em>
	                	</a>
	                 	<div id="memList_${board.board.board_num}" class="re_btn_option">
							<div class="ly_dimmed"></div>
							<ul class="re_popup"></ul>
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
	                		<em id="u_cnt${board.board.board_num}" class="u_cnt"> ${board.board.recommend_num}</em>
	                	</a>
	                 	<div id="memList_${board.board.board_num}" class="re_btn_option">
							<div class="ly_dimmed"></div>
							<ul class="re_popup"></ul>
						</div>
					</div>
				</c:if>
				<a href="/content/contentForm.do?board_num=${board.board.board_num}&comment=true" class="btns_coment" >
					<span class="u_ico_coment">댓글</span>
					<span class="text_num">${board.commentCount}</span>				
				</a>
				<a href="#" class="btns_screp" >
					<span class="u_ico_screp">스크렙</span>
					<span class="text_num">19</span>
				</a>
       		</div>
       	</div>
	</div>
</c:forEach>
	<div id="${board.board.board_num}" class="view_more">
	 		<a href="#" id="${board.board.board_num}" class="list_view_more">
	 			<span class="ico_plus"><img src="../image/plus.png"></span><span class="txt_view_more">더 많은 리뷰 보기</span>
	 		</a>
    </div>
	<c:if test="${login_status==0 || login_status==1}">
		<div class="btn_posting_wrap">
			<a href="/write/writeForm.do" class="btn_posting">
				<span class="u_vc">글쓰기</span>
			</a>
		</div>
	</c:if>
</html>