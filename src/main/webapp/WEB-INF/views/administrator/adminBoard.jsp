<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
	<head>
		<link href="../css/style.css" rel="stylesheet" type="text/css">
		<style>
			* { margin:0; padding:0; }
			ul,li { list-style:none; }
			a { text-decoration:none; color:#000; }
			.tab {border:1px solid #ddd; border-left:none; background:#fff; overflow:hidden;  position: static; width: 100%}
			.tab li { float:left; width:33.3%; border-left:1px solid #ddd; text-align:center; box-sizing:border-box; }
			.tab li { display:inline-block; padding:20px; cursor:pointer; }
			.tab li.on { background-color:#eee; color:#f00; }
			.tab_con { clear:both; margin-top:50px auto; border:1px solid #ddd;}
			.tab_con div { display:none; height: 100% auto; background:#fff; line-height:100px; text-align:center;}
			.list_table{ margin-top: 20px; margin: auto; border-bottom: solid 1px; border-bottom-color: #f6f6f6; text-align: center;
				padding : 7px; color: #4c4c4c; font-size: 14px; }
				
			.Board_Detaile_Info{display: none; position: relative; z-index: 9999;top: 0;right: 0;bottom: 0;left: 0;line-height: 100%;text-align: center;}
			.Board_Info{display: inline-block;position: relative; z-index: 10000;width: 460px;background-color: #fff;line-height: normal;vertical-align: middle; }
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
					var a = $("#menu_" + $(this).attr("id"));
					a.show();
				});
				$("body").on("click", ".Board_Detaile_Info", function(e){
					$(this).hide();
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
		</script>
	</head>
	<body>
		<div class="board_tab">
		
		<ul class="tab" id="tab">
    		<li>전체 게시글</li>
    		<li>신고 받은 게시글</li>
    		<li>추천 받은 게시글</li>	
		</ul>
		<div style="height: 5%;"></div>
		<div class="tab_con" id="tab_con">
    		<div id="board">
    			<c:forEach var="board" items="${boardList}"> 
					<table class="list_table Board_Simple_Info" id="${board.id}" >
						<tr class="Board_Simple_Info" id="${board.id}">
							<td class="space" width="10"></td>
							<td width="20%" align="left">
								게시글 번호 : ${board.board_num}
							</td>
							<td class="space" width="10"></td>
							<td width="20%" >
								<a id="text" href="/profile/myProfile.do?id=${board.id}"> 작성자 : ${board.id}</a>
							</td>
							<td class="space" width="10"></td>
							<td width="20%">
								작성일 : <fmt:formatDate value="${board.write_date}" pattern="yyyy-MM-dd HH:mm"/>
							</td>
							<td class="space" width="10"></td>
							<td width="20%">
								<a id="text" href="/content/contentForm.do?board_num=${board.board_num}">게시글 보기</a>
							</td>
							<td class="space" width="10"></td>
							<td width="20%">
								<a id="text" href="/content/deleteContent.do?board_num=${board.board_num}&id=${board.id}">
									<img src="../image/icon_66.png"> 삭제 
								</a>
							</td>
						</tr>
					</table>
					<div id="menu_${board.id}" class="Board_Detaile_Info">
						<div class="ly_dimmed"></div>
						<table class="Board_Info ul_list">
							<tr>
								<td>
									글 번호 : ${board.board_num}
								</td>
							</tr>
							<tr>
								<td>
									작성자 : ${board.id}
								</td>
							</tr>
							<tr>
								<td>
									카테고리 : ${board.category_id}
								</td>
							</tr>
							<tr>
								<td>
									작성일시 : <fmt:formatDate value="${board.write_date}" pattern="yyyy-MM-dd HH:mm"/>
								</td>
							</tr>
							<tr>
								<td>
									추천 : ${board.recommend_num}
								</td>
							</tr>
							<tr>
								<td>
									신고 : ${board.report_num}
								</td>
							</tr>
							<tr>
								<td>
									<%-- 스크랩 :${screpCount[board.board_num]} --%>
								</td>
							</tr>
							<tr>
								<td>
									댓글 : ${commentCount[board.board_num]}
								</td>
							</tr>
						</table>
					</div>
				</c:forEach>
    		</div>	
    		<div id="report">
    			<c:forEach var="reportBoard" items="${reportBoardList}"> 
					<table class="list_table">
						<tr>
							<td class="space" width="10"></td>
							<td width="20%" align="left">
								<a id="text" href="/profile/myProfile.do?id=${reportBoard.id}">작성자 : ${reportBoard.id}</a>
							</td>
							<td class="space" width="10"></td>
							<td width="20%" >
								<img src="../image/report.png"> : ${reportBoard.report_num}
							</td>
							<td class="space" width="10"></td>
							<td width="25%">
								작성일 : <fmt:formatDate value="${reportBoard.write_date}" pattern="yyyy-MM-dd HH:mm"/>
							</td>
							<td class="space" width="10"></td>
							<td width="20%">
								<a id="text" href="/content/contentForm.do?board_num=${reportBoard.board_num}">상세보기</a>
							</td>
							<td class="space" width="10"></td>
							<td width="20%">
								<a id="text" href="/content/deleteContent.do?board_num=${reportBoard.board_num}&id=${reportBoard.id}">
									삭제 <img src="../image/icon_66.png">
								</a>
							</td>
						</tr>
					</table>
				</c:forEach>
			</div>
    		<div id="popul">
    			<c:forEach var="populBoard" items="${populBoardList}"> 
					<table class="list_table">
						<tr>
							<td class="space" width="10"></td>
							<td width="20%" align="left">
								<a id="text" href="/profile/myProfile.do?id=${populBoard.id}">작성자  : ${populBoard.id}</a>
							</td>
							<td class="space" width="10"></td>
							<td width="20%">
								<img src="../image/recommend_off.png" width="15" height="15"> : ${populBoard.recommend_num}
							</td>
							<td class="space" width="10"></td>
							<td width="25%">
								작성일 : <fmt:formatDate value="${populBoard.write_date}" pattern="yyyy-MM-dd HH:mm"/>
							</td>
							<td class="space" width="10"></td>
							<td width="20%">
								<a id="text" href="/content/contentForm.do?board_num=${populBoard.board_num}">상세보기</a> 
							</td>
							<td class="space" width="10"></td>
							<td width="20%">
								<a id="text" href="/content/deleteContent.do?board_num=${populBoard.board_num}&id=${populBoard.id}">
									삭제 <img src="../image/icon_66.png">
								</a>
							</td>
						</tr>
					</table>
				</c:forEach>
    		</div>
		</div>
		<div style="height: 5%;"></div>
		</div>
	</body>
</html>