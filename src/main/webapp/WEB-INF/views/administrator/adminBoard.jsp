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
			.tab {border:1px solid #ddd; border-left:none; background:#fff; overflow:hidden;  position: static; width: 100%; z-index: 1000000; }
			.tab li { float:left; width:33.3%; border-left:1px solid #ddd; text-align:center; box-sizing:border-box; }
			.tab li { display:inline-block; padding:20px; cursor:pointer; }
			.tab li.on { background-color:#eee; color:#f00; }
			.tab_con { clear:both; margin-top:50px auto; border:1px solid #ddd;}
			.tab_con div { display:none; height: 100% auto; background:#fff; line-height:100px; text-align:center;}
			
			.change{ margin: 0; padding: 0; width: 100px; height: 20px; text-align: left; cursor: pointer; t}
			
			.item{border:1px solid #ddd; border-left:none; background:#fff; overflow:hidden;  position: static; width: 100%; z-index: 1000000;}
			.item li {float:left; width:20%; border-left:1px solid #ddd; text-align:center; box-sizing:border-box;}
			.item li {display:inline-block; padding:5px; cursor:pointer; vertical-align: middle;}
			
			.Board_Detaile_Info{position: relative; top: 0;right: 0;bottom: 0;left: 0;line-height: 100%; margin: 0 auto; width: 400px;}
			.Board_Detaile_Info li{text-align: left;}
			.Report_Board_Detaile_Info{position: relative; top: 0;right: 0;bottom: 0;left: 0;line-height: 100%; margin: 0 auto; width: 400px;}
			.Report_Board_Detaile_Info li{text-align: left;}
			.Popul_Board_Detaile_Info{position: relative; top: 0;right: 0;bottom: 0;left: 0;line-height: 100%; margin: 0 auto; width: 400px;}
			.Popul_Board_Detaile_Info li{text-align: left;}
			
			.Sort_Menu{text-align: left; margin: 0; padding: 0; width: 200px; height: 20px; text-align: right; cursor: pointer;}
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
			$(function () {	
				tab('#tab',0);
			});
			function tab(e, num){
			    var num = num || 0;
			    var menu = $(e).children();
			    var con = $(e+'_con').children();
			    var select = $(menu).eq(num);
			    var i = num;
	
			    select.addClass('on');
			    con.eq(num).show();
	
			    menu.click(function(){
			        if(select!==null){
			            select.removeClass("on");
			            con.eq(i).hide();
			        }
	
			        select = $(this);	
			        i = $(this).index();
	
			        select.addClass('on');
			        con.eq(i).show();
			    });
			}
			$(function(){
				$(".change").click(function(){
		  			$(location).attr("href", "/administrator/adminMem.do");
		  		}); 
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
		    	$(".content").click(function(){
		  			$(location).attr("href", "/content/contentForm.do?board_num=" + $(this).attr("id"));
		  		}); 
		    	$(".delete").click(function(){
		    		$(locateion).attr("href", "/content/deleteContent.do?board_num" + $(this).attr("id") + "&id=" + $(this).attr());
		    	});
			});
		</script>
	</head>
	<body>
		<div class="board_tab">
			<ul class="tab" id="tab">
    			<li>전체 게시글</li>
    			<li>신고 받은 게시글</li>
    			<li>추천 받은 게시글</li>	
			</ul>
			<div class="change">
				회원 <img src="../image/change_icon.png">
			</div>
		<div style="height: 4%;"></div>
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
			</c:if>
		</div>
		<div class="tab_con" id="tab_con">
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
    					<li class="content" id="${board.board_num}">
							게시글 <img src="../image/list_icon.png" width="15" height="15" title="게시글 보기"> 
    					</li>
    					<li class="delete" id="${board.board_num}" title="${board.id}">
							삭제 <img src="../image/memOut_icon1.png" width="14" height="14" title="삭제"> 
    					</li>
    				</ul>
					<ul class="Board_Detaile_Info" id="info_${board.board_num}" title="${board.board_num}" style="display: none;">
						<li>
							글 번호 : ${board.board_num}
						</li>
						<li>
							작성자 : ${board.id}
						</li>
						<li>
							카테고리 : ${category_info[board.category_id].group1}, ${category_info[board.category_id].group2}, ${category_info[board.category_id].group3}
						</li>
						<li>
							작성일시 : <fmt:formatDate value="${board.write_date}" pattern="yyyy-MM-dd HH:mm"/>
						</li>
						<li>
		             		추천 <img src="../image/recommend_off.png" width="10" height="10"> : ${board.recommend_num}
						</li>
						<li>
							신고 <img src="../image/report.png" width="10" height="10"> : ${board.report_num}
						</li>
						<li>
							댓글 <img src="../image/icon_14.png" width="10" height="10"> : ${commentCount[board.board_num]}
						</li>
						<li>
							스크랩 <img src="../image/screp_on.png" width="10" height="10"> :${screpCount[board.board_num]}
						</li>
					</ul>
				</c:forEach>
    		</div>	
    		<div id="report">
    			<c:forEach var="reportBoard" items="${reportBoardList}"> 
					<ul class="item" id="css_${reportBoard.board_num}">
    					<li class="Report_Board_Simple_Info" id="report_list_${reportBoard.board_num}" title="${reportBoard.board_num}">
    						게시글 번호 : ${reportBoard.board_num}
    					</li>
    					<li class="Report_Board_Simple_Info" id="report_list_${reportBoard.board_num}" title="${reportBoard.board_num}">
    						작성자 : ${reportBoard.id}
    					</li>
    					<li class="Report_Board_Simple_Info" id="report_list_${reportBoard.board_num}" title="${reportBoard.board_num}">
    						작성일 : <fmt:formatDate value="${reportBoard.write_date}" pattern="yyyy-MM-dd HH:mm"/>
    					</li>
    					<li class="content" id="${reportBoard.board_num}">
							게시글 <img src="../image/list_icon.png" width="15" height="15" title="게시글 보기"> 
    					</li>
    					<li class="delete" id="${reportBoard.board_num}" title="${reportBoard.id}">
							삭제 <img src="../image/memOut_icon1.png" width="14" height="14" title="삭제"> 
    					</li>
    				</ul>
					<ul class="Report_Board_Detaile_Info" id="report_info_${reportBoard.board_num}" title="${reportBoard.board_num}" style="display: none;">
						<li>
							글 번호 : ${reportBoard.board_num}
						</li>
						<li>
							작성자 : ${reportBoard.id}
						</li>
						<li>
							카테고리 : ${category_info[reportBoard.category_id].group1}, ${category_info[reportBoard.category_id].group2}, ${category_info[reportBoard.category_id].group3}
						</li>
						<li>
							작성일시 : <fmt:formatDate value="${reportBoard.write_date}" pattern="yyyy-MM-dd HH:mm"/>
						</li>
						<li>
							신고 <img src="../image/report.png" width="10" height="10"> : ${reportBoard.report_num}
						</li>
						<li>
							댓글 <img src="../image/icon_14.png" width="10" height="10"> : ${commentCount[reportBoard.board_num]}
						</li>
						<li>
							스크랩 <img src="../image/screp_on.png" width="10" height="10"> :${screpCount[reportBoard.board_num]}
						</li>
					</ul>
				</c:forEach>
			</div>
    		<div id="popul">
    			<c:forEach var="populBoard" items="${populBoardList}"> 
					<ul class="item" id="css_${populBoard.board_num}">
    					<li class="Popul_Board_Simple_Info" id="popul_list_${populBoard.board_num}" title="${populBoard.board_num}">
    						게시글 번호 : ${populBoard.board_num}
    					</li>
    					<li class="Popul_Board_Simple_Info" id="popul_list_${populBoard.board_num}" title="${populBoard.board_num}">
    						작성자 : ${populBoard.id}
    					</li>
    					<li class="Popul_Board_Simple_Info" id="popul_list_${populBoard.board_num}" title="${populBoard.board_num}">
    						작성일 : <fmt:formatDate value="${populBoard.write_date}" pattern="yyyy-MM-dd HH:mm"/>
    					</li>
    					<li class="content" id="${populBoard.board_num}">
							게시글 <img src="../image/list_icon.png" width="15" height="15" title="게시글 보기"> 
    					</li>
    					<li class="delete" id="${populBoard.board_num}" title="${populBoard.id}">
							삭제 <img src="../image/memOut_icon1.png" width="14" height="14" title="삭제"> 
    					</li>
    				</ul>
					<ul class="Popul_Board_Detaile_Info" id="popul_info_${populBoard.board_num}" title="${populBoard.board_num}" style="display: none;">
						<li>
							글 번호 : ${populBoard.board_num}
						</li>
						<li>
							작성자 : ${populBoard.id}
						</li>
						<li>
							카테고리 : ${category_info[populBoard.category_id].group1}, ${category_info[populBoard.category_id].group2}, ${category_info[populBoard.category_id].group3}
						</li>
						<li>
							작성일시 : <fmt:formatDate value="${populBoard.write_date}" pattern="yyyy-MM-dd HH:mm"/>
						</li>
						<li>
		             		추천 <img src="../image/recommend_off.png" width="10" height="10"> : ${populBoard.recommend_num}
						</li>
						<li>
							신고 <img src="../image/report.png" width="10" height="10"> : ${populBoard.report_num}
						</li>
						<li>
							댓글 <img src="../image/icon_14.png" width="10" height="10"> : ${commentCount[populBoard.board_num]}
						</li>
						<li>
							스크랩 <img src="../image/screp_on.png" width="10" height="10"> :${screpCount[populBoard.board_num]}
						</li>
					</ul>
				</c:forEach>
    		</div>
		</div>
		<div style="height: 5%;"></div>
		</div>
	</body>
</html>