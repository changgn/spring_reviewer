$(document).ready(function(){
	if("${error}"=="error") {
		$(location).attr("href", "/main/main.do?sort=all");
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
	
	$(".cont_menu_option").click(function(){
		$(".cont_btn_option").css({
	    }).show();
	});
	$(".cont_btn_option").click(function(){
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
				var nrecommend = args.recommend;
				var recommendFlog = args.recommendFlog;
				var selector = $("#recommend_img"+args.board_num);
				var selector2 = $("#u_cnt"+args.board_num);
				selector2.text(" " + nrecommend);
				if(recommendFlog == 'recommend'){
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
					writer += '</div>';
					writer += '<div id="content_comment_writed_area">';
					writer += '<textarea id="content_comment_writed" readonly>' + args.data.content + '</textarea>';
					writer += '</div>';
					writer += '</div>';
					writer += '<div id="comment_btn_delete" class="btn_short">';
					if(args.data.id=='${id}'){
						writer += '<a href="/content/commentdel.do?board_num=' + args.data.board_num + '&comment_num=' + args.data.comment_num + '">삭&nbsp;&nbsp;&nbsp;제</a>';	
					}
					else{
						writer += '<a href="#" id="noDelete">삭&nbsp;&nbsp;&nbsp;제</a>';
					}
					writer += '</div>';
					writer += '</div>';	
					
					$("#writed_comment_area").prepend(writer);
					$("#content_comment_write").val("");
					$("#content_comment_write").focus();
				}
				
			}); 
			/* $("#content_comment_write_form").submit(); */
		}
	});
	
	$("#noDelete").click(function(){
		alert("댓글 작성자만 삭제할 수 있습니다");
		var top = $("#content_comment_area").offset().top;
		$('html, body').animate({ scrollTop : top });
	});
});
