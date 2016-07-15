<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
	<head>
		<style>
			* { margin:0; padding:0; }
			ul,li { list-style:none; }
			a { text-decoration:none; color:#000; }
			.Title{
				text-align: center;
				font-size: 20px;
				border: 1px solid;
				border-color: #b9b9b9;
				padding: 20px;
				color: #4c4c4c;
				width: 40%;	
				height: auto;
				margin: 50 auto;
				cursor: pointer;
			}
			.change{ margin: 0; padding: 0; width: 60px; height: 20px; text-align: center; cursor: pointer; float: left;}
			.board{width: 100%; height: 100% auto;} .boardListBody{height: auto;}
			.item{border:1px solid #ddd; border-left:none; background:#fff; overflow:hidden;  position: static; width: 100%; z-index: 1000000;}
			.item li {float:left; width:20%; border-left:1px solid #ddd; text-align:center; box-sizing:border-box;}
			.item li {display:inline-block; padding:5px; cursor:pointer; vertical-align: middle;}
			
			.Board_Detaile_Info{position: relative; top: 0;right: 0;bottom: 0;left: 0;line-height: 100%; margin: 0 auto; width: 500px;}
			.Board_Detaile_Info ul{float: left; width: 50%; text-align: left; vertical-align: middle;}
			.two li{ text-align: justify;}
			
			.Sort_Menu{ margin: 0; padding: 0; width: 400px; text-align: right; cursor: pointer; float: right;}
			
			.rcmd{cursor: pointer;}
			.rpt{cursor: pointer;}
			.boardNum{cursor: pointer;}
			.writer{cursor: pointer;}
			.cmt{cursor: pointer;}
			.scr{cursor: pointer;}
		</style>
		<script src="https://code.jquery.com/jquery-2.2.3.min.js"></script>
		<script>
			$(document).ready(function(){
				$(window).scroll(function(){
					if($(window).scrollTop()>79) {
						$("#tab").css("position", "fixed");
						$("#tab").css('top', "40px");
					} else {
						$("#tab").css("position", "static");
					}
				});
			});
			$(function(){
				$("body").on("click", ".Board_Simple_Info", function(e){
					e.preventDefault();
					var board_num = $(this).attr("title");
					var info = $("#info_" + board_num);
					var style = info.attr("style");
					var board_detale_class = info.attr("class");
					if(style == 'display: none;'){
						$("." + board_detale_class).hide();
						info.show();
					}else{
						info.hide();
					}
				});
				$("body").on("click", ".Report_Board_Simple_Info", function(e){
					e.preventDefault();
					var report_board_num = $(this).attr("title");
					var reprot_info = $("#report_info_" + report_board_num);
					var report_style = reprot_info.attr("style");
					var report_board_detale_class = reprot_info.attr("class");
					if(report_style == 'display: none;'){
						$("." + report_board_detale_class).hide();
						reprot_info.show();
					}else{
						reprot_info.hide()
					}
				});
				$("body").on("click", ".Popul_Board_Simple_Info", function(e){
					e.preventDefault();
					var popul_board_num = $(this).attr("title");
					var popul_info = $("#popul_info_" + popul_board_num);
					var popul_style = popul_info.attr("style");
					var popul_board_detale_class = popul_info.attr("class");
					if(popul_style == 'display: none;'){
						$("." + popul_board_detale_class).hide();
						popul_info.show();
					}else{
						popul_info.hide();
					}
				});
			});
			$(function(){
				$(".Board_Simple_Info").mouseover(function(e){
					var board_num = $(this).attr("title");
					var css_board_num = $("#css_" + board_num);
					css_board_num.css("background-color", '#EAEAEA');
				});
				$(".content").mouseover(function(e){
					var board_num = $(this).attr("title");
					var css_board_num = $("#css_" + board_num);
					css_board_num.css("background-color", '#EAEAEA');
				});
				$(".delete").mouseover(function(e){
					var board_num = $(this).attr("title");
					var css_board_num = $("#css_" + board_num);
					css_board_num.css("background-color", '#EAEAEA');
				});
				$(".Board_Simple_Info").mouseout(function(e){
					var board_num = $(this).attr("title");
					var css_board_num = $("#css_" + board_num);
					css_board_num.css("background-color", '#FFFFFF');
				});
				$(".content").mouseout(function(e){
					var board_num = $(this).attr("title");
					var css_board_num = $("#css_" + board_num);
					css_board_num.css("background-color", '#FFFFFF');
				});
				$(".delete").mouseout(function(e){
					var board_num = $(this).attr("title");
					var css_board_num = $("#css_" + board_num);
					css_board_num.css("background-color", '#FFFFFF');
				});
			});
			$(function(){
		    	$(".content").click(function(){
		  			$(location).attr("href", "/content/contentForm.do?board_num=" + $(this).attr("id"));
		  		}); 
		    	$(".delete").click(function(){
		    		$(locateion).attr("href", "/content/deleteContent.do?board_num" + $(this).attr("title") + "&id=" + $(this).attr("id"));
		    	});
		    	$(".Title").click(function(){
		  			$(location).attr("href", "/administrator/adminBoard.do");
		  		}); 
		    	$(".change").click(function(){
		  			$(location).attr("href", "/administrator/adminMem.do");
		  		}); 
			});
			$(function(){
				$(".rcmd").click(function(){
					var board_num = $(this).attr("id");
					var rcl_board_num = $("#rcl_" + board_num);
					var style = rcl_board_num.attr("style");
					if(style == 'display: none;'){
						$(".recommendList").hide();
						$(".reprotList").hide();
						$(".commentList").hide();
						$(".screpList").hide();
						rcl_board_num.show();
					}else{
						rcl_board_num.hide();
					}
				});
				$(".rpt").click(function(){
					var board_num = $(this).attr("id");
					var rpl_board_num = $("#rpl_" + board_num);
					var style = rpl_board_num.attr("style");
					if(style == 'display: none;'){
						$(".recommendList").hide();
						$(".reprotList").hide();
						$(".commentList").hide();
						$(".screpList").hide();
						rpl_board_num.show();
					}else{
						rpl_board_num.hide();
					}
				});
				$(".cmt").click(function(){
					var board_num = $(this).attr("id");
					var cmt_board_num = $("#cl_" + board_num);
					var style = cmt_board_num.attr("style");
					if(style == 'display: none;'){
						$(".recommendList").hide();
						$(".reprotList").hide();
						$(".commentList").hide();
						$(".screpList").hide();
						cmt_board_num.show();
					}else{
						cmt_board_num.hide();
					}
				});
				$(".scr").click(function(){
					var board_num = $(this).attr("id");
					var scr_board_num = $("#sl_" + board_num);
					var style = scr_board_num.attr("style");
					if(style == 'display: none;'){
						$(".recommendList").hide();
						$(".reprotList").hide();
						$(".commentList").hide();
						$(".screpList").hide();
						scr_board_num.show();
					}else{
						scr_board_num.hide();
					}
				});
			});
		</script>
		<title>게시글 관리</title>
	</head>
	<body>
		<div class="boardListBody">
			<div class="Title">
				게시글 (${listcount})
			</div>
			<span class="change">
				회원 <img src="../image/change_icon.png">
			</span>
			<div class="Sort_Menu">
				<c:if test="${kind eq 'noKind' &&sort eq 'noSort'}">
					<a href="/administrator/adminBoard.do?kind=board_num&sort=DESC">
						글번호
					</a>
					&nbsp;
					<img src="../image/icon_08.png" height="15">
					&nbsp;
					<a href="/administrator/adminBoard.do?kind=Writer&sort=DESC">
						작성자
					</a>
					&nbsp;
					<img src="../image/icon_08.png" height="15">
					&nbsp;
					<a href="/administrator/adminBoard.do?kind=writerDate&sort=DESC">
						작성일
					</a>
					&nbsp;
					<img src="../image/icon_08.png" height="15">
					&nbsp;
					<a href="/administrator/adminBoard.do?kind=report&sort=DESC">
						신고 게시글
					</a>
					&nbsp;
					<img src="../image/icon_08.png" height="15">
					&nbsp;
					<a href="/administrator/adminBoard.do?kind=recommend&sort=DESC">
						추천 게시글
					</a>
				</c:if>
				<c:if test="${kind ne 'noKind' &&sort ne 'noSort'}">
					<c:if test="${sort eq 'DESC'}">
						<a href="/administrator/adminBoard.do?kind=board_num&sort=DESC">
							글번호
						</a>
					</c:if>
					<c:if test="${sort eq 'ASC'}">
						<a href="/administrator/adminBoard.do?kind=board_num&sort=ASC">
							글번호
						</a>
					</c:if>
					&nbsp;
					<img src="../image/icon_08.png" height="15">
					&nbsp;
					<c:if test="${sort eq 'DESC'}">
						<a href="/administrator/adminBoard.do?kind=writer&sort=DESC">
							작성자
						</a>
					</c:if>
					<c:if test="${sort eq 'ASC'}">
						<a href="/administrator/adminBoard.do?kind=writer&sort=ASC">
							작성자
						</a>
					</c:if>
					&nbsp;
						<img src="../image/icon_08.png" height="15">
					&nbsp;
					<c:if test="${sort eq 'DESC'}">
						<a href="/administrator/adminBoard.do?kind=writeDate&sort=DESC">
							작성일
						</a>
					</c:if>
					<c:if test="${sort eq 'ASC'}">
						<a href="/administrator/adminBoard.do?kind=writeDate&sort=ASC">
							작성일 
						</a>
					</c:if>
					&nbsp;
						<img src="../image/icon_08.png" height="15">
					&nbsp;
					<c:if test="${sort eq 'DESC'}">
						<a href="/administrator/adminBoard.do?kind=report&sort=DESC">
							신고 게시글
						</a>
					</c:if>
					<c:if test="${sort eq 'ASC'}">
						<a href="/administrator/adminBoard.do?kind=report&sort=ASC">
							신고 게시글
						</a>
					</c:if>
					&nbsp;
						<img src="../image/icon_08.png" height="15">
					&nbsp;
					<c:if test="${sort eq 'DESC'}">
						<a href="/administrator/adminBoard.do?kind=recommend&sort=DESC">
							추천 게시글
						</a>
					</c:if>
					<c:if test="${sort eq 'ASC'}">
						<a href="/administrator/adminBoard.do?kind=recommend&sort=ASC">
							추천 게시글
						</a>
					</c:if>
				</c:if>
			</div>
  		  	<div id="board">
  		  		<c:forEach var="board" items="${boardList}">
  		  			<ul class="item" id="css_${board.board_num}">
  		  				<li class="Board_Simple_Info" id="list_${board.board_num}" title="${board.board_num}">
  		  					게시글 번호 : ${board.board_num}
    					</li>
    					<li class="Board_Simple_Info" id="list_${board.board_num}" title="${board.board_num}">
    						작성자 : ${board.id}
    					</li>
    					<li class="Board_Simple_Info" id="list_${board.board_num}" title="${board.board_num}">
    						작성일 : <fmt:formatDate value="${board.write_date}" pattern="yyyy-MM-dd HH:mm"/>
    					</li>
    					<li class="content" id="${board.board_num}" title="${board.board_num}">
							게시글 <img src="../image/list_icon.png" width="15" height="15" title="게시글 보기"> 
    					</li>
    					<li class="delete" id="${board.id}" title="${board.board_num}">
							삭제 <img src="../image/memOut_icon1.png" width="14" height="14" title="삭제"> 
    					</li>
    				</ul>
    				<div class="Board_Detaile_Info" id="info_${board.board_num}" title="${board.board_num}" style="display: none;">
						<ul class="one">
							<li >
								글 &nbsp;번&nbsp;호 : ${board.board_num}
							</li>
							<li>
								작&nbsp;성&nbsp;자&nbsp; : ${board.id}
							</li>
							<li>
								카테고리 : ${category_info[board.category_id].group1}, ${category_info[board.category_id].group2}, ${category_info[board.category_id].group3}
							</li>
							<li>
								작성일시 : <fmt:formatDate value="${board.write_date}" pattern="yyyy-MM-dd HH:mm"/>
							</li>
							<li>
								<span class="rcmd" id="${board.board_num}">
				            		추&nbsp;&nbsp;&nbsp;천 <img src="../image/recommend_off.png" width="10" height="10"> : ${board.recommend_num}
				            	</span>
							</li>
							<li>
								<span class="rpt" id="${board.board_num}">
									신&nbsp;&nbsp;&nbsp;고 <img src="../image/report.png" width="10" height="10"> : ${board.report_num}
								</span>
							</li>
							<li>
								<span class="cmt" id="${board.board_num}">
									댓&nbsp;&nbsp;&nbsp;글 <img src="../image/icon_14.png" width="10" height="10"> : ${commentCount[board.board_num]}
								</span>
							</li>
							<li>
								<span class="scr" id="${board.board_num}">
									스크랩 <img src="../image/screp_on.png" width="10" height="10"> : ${screpCount[board.board_num]}
								</span>
							</li>
						</ul>
						<ul class="two">
							<li class="recommendList" id="rcl_${board.board_num}" style="display: none;">
								<c:if test="${empty recommendListByBoard[board.board_num]}">
									추천 없음
								</c:if>
								<c:if test="${!empty recommendListByBoard[board.board_num]}">
									<c:forEach var="recommend" items="${recommendListByBoard[board.board_num]}">
										< ${recommend} > 
									</c:forEach>
								</c:if>
							</li>
							<li class="reprotList" id="rpl_${board.board_num}" style="display: none;">
								<c:if test="${empty reportListByBoard[board.board_num]}">
									신고 없음
								</c:if>
								<c:if test="${!empty reportListByBoard[board.board_num]}">
									<c:forEach var="report" items="${reportListByBoard[board.board_num]}">
										< ${report} >
									</c:forEach>
								</c:if>
							</li>
							<li class="commentList" id="cl_${board.board_num}" style="display: none;"> 
								<c:if test="${empty commentIdListByBoardnum[board.board_num]}">
									댓글 없음
								</c:if>
								<c:if test="${!empty commentIdListByBoardnum[board.board_num]}">
									<c:forEach var="commentList" items="${commentIdListByBoardnum[board.board_num]}">
										< ${commentList} >
									</c:forEach>
								</c:if>								
							</li>
							<li class="screpList" id="sl_${board.board_num}" style="display: none;">
								<c:if test="${empty screpListByBoardnum[board.board_num]}">
									스크랩 없음
								</c:if>
								<c:if test="${!empty screpListByBoardnum[board.board_num]}">
									<c:forEach var="screp" items="${screpListByBoardnum[board.board_num]}">
										< ${screp} >
									</c:forEach>
								</c:if>		
							</li>
						</ul>
					</div>
				</c:forEach>
    		</div>
    		<div style="height: 10px;"></div>	
		</div>
		<div style="height: 5%;"></div>
	</body>
</html>