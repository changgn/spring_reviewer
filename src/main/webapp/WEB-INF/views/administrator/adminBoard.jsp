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
			.tab {border:1px solid #ddd; border-left:none; background:#fff; overflow:hidden;  position: static; width: 100%; z-index: 1000000; }
			.tab li { float:left; width:33.3%; border-left:1px solid #ddd; text-align:center; box-sizing:border-box; }
			.tab li { display:inline-block; padding:20px; cursor:pointer; }
			.tab li.on { background-color:#eee; color:#f00; }
			.tab_con { clear:both; margin-top:50px auto; border:1px solid #ddd;}
			.tab_con div { display:none; height: 100% auto; background:#fff; line-height:100px; text-align:center;}
			.item{ margin-top: 20px; margin: auto; border-bottom: solid 1px; border-bottom-color: #f6f6f6; text-align: center;
				padding : 7px; color: #4c4c4c; font-size: 14px; border-top: solid 1px; border-top-color: #f6f6f6; vertical-align: middle;}
				
			.Board_Detaile_Info{position: relative; top: 0;right: 0;bottom: 0;left: 0;line-height: 100%;text-align: center;}
			.Board_Info{display: inline-block;position: relative; width: 300px auto;background-color: #fff;line-height: normal;vertical-align: middle; }
			.Report_Board_Detaile_Info{position: relative; top: 0;right: 0;bottom: 0;left: 0;line-height: 100%;text-align: center;}
			.Report_Board_Info{display: inline-block;position: relative; width: 300px auto;background-color: #fff;line-height: normal;vertical-align: middle; }
			.Popul_Board_Detaile_Info{position: relative; top: 0;right: 0;bottom: 0;left: 0;line-height: 100%;text-align: center;}
			.Popul_Board_Info{display: inline-block;position: relative; width: 300px auto;background-color: #fff;line-height: normal;vertical-align: middle; }
			.re_popup_close{display: inline-block;overflow: hidden;width: 100%;height: 60px;border: none;font-size: 16px;color: #414042;line-height: 60px;text-align: center;vertical-align: top;}
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
					<table class="item">
						<tr>
							<td width="20%" class="Board_Simple_Info" id="list_${board.board_num}" title="${board.board_num}">
								게시글 번호 : ${board.board_num}
							</td>
							<td width="20%" class="Board_Simple_Info" id="list_${board.board_num}" title="${board.board_num}">
								작성자 : ${board.id}
							</td>
							<td width="20%" class="Board_Simple_Info" id="list_${board.board_num}" title="${board.board_num}">
								작성일 : <fmt:formatDate value="${board.write_date}" pattern="yyyy-MM-dd HH:mm"/>
							</td>
							<td width="10%">
								<a id="text" href="/content/contentForm.do?board_num=${board.board_num}">
									게시글 <img src="../image/list_icon.png" width="15" height="15" title="게시글 보기"> 
								</a>
							</td>
							<td width="10%">
								<a id="text" href="/content/deleteContent.do?board_num=${board.board_num}&id=${board.id}">
									삭제 <img src="../image/memOut_icon1.png" width="30" height="30" title="삭제"> 
								</a>
							</td>
						</tr>
					</table>
					<div class="Board_Detaile_Info" id="info_${board.board_num}" title="${board.board_num}" style="display: none;">
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
									카테고리 : ${category_info[board.category_id].group1}, ${category_info[board.category_id].group2}, ${category_info[board.category_id].group3}
								</td>
							</tr>
							<tr>
								<td>
									작성일시 : <fmt:formatDate value="${board.write_date}" pattern="yyyy-MM-dd HH:mm"/>
								</td>
							</tr>
							<tr>
								<td>
									<a href="#" id="${board.board_num}" class="re_menu_option">
		              					추천 <img src="../image/recommend_off.png" width="10" height="10"> 
		              					<em id="u_cnt${board.board_num}" >
		              						: ${board.recommend_num}
		              					</em>
		              				</a>
								</td>
								<td id="memList_${board.board_num}" class="re_btn_option">
									<div class="ly_dimmed"></div>
									<ul class="re_popup ul_list"></ul>
								</td>
							</tr>
							<tr>
								<td>
									신고 <img src="../image/report.png" width="10" height="10"> : ${board.report_num}
								</td>
							</tr>
							<tr>
								<td>
									댓글 <img src="../image/icon_14.png" width="10" height="10"> : ${commentCount[board.board_num]}
								</td>
							</tr>
							<tr>
								<td>
									스크랩 <img src="../image/screp_on.png" width="10" height="10"> :${screpCount[board.board_num]}
								</td>
							</tr>
						</table>
					</div>
				</c:forEach>
    		</div>	
    		<div id="report">
    			<c:forEach var="reportBoard" items="${reportBoardList}"> 
					<table class="item">
						<tr>
							<td width="20%" class="Report_Board_Simple_Info" id="report_lsit_${reportBoard.board_num}" title="${reportBoard.board_num}">
								게시글 번호 : ${reportBoard.board_num}
							</td>
							<td width="20%" class="Report_Board_Simple_Info" id="report_lsit_${reportBoard.board_num}" title="${reportBoard.board_num}">
								작성자 : ${reportBoard.id}
							</td>
							<td width="20%" class="Report_Board_Simple_Info" id="report_lsit_${reportBoard.board_num}" title="${reportBoard.board_num}">
								작성일 : <fmt:formatDate value="${reportBoard.write_date}" pattern="yyyy-MM-dd HH:mm"/>
							</td>
							<td width="10%">
								<a id="text" href="/content/contentForm.do?board_num=${reportBoard.board_num}">
									게시글 <img src="../image/list_icon.png" width="15" height="15" title="게시글 보기"> 
								</a>
							</td>
							<td width="10%">
								<a id="text" href="/content/deleteContent.do?board_num=${reportBoard.board_num}&id=${reportBoard.id}">
									삭제 <img src="../image/memOut_icon1.png" width="30" height="30" title="삭제"> 
								</a>
							</td>
						</tr>
					</table>
					<div id="report_info_${reportBoard.board_num}" class="Report_Board_Detaile_Info" title="${reportBoard.board_num}" style="display: none;">
						<table class="Report_Board_Info ul_list">
							<tr>
								<td>
									글 번호 : ${reportBoard.board_num}
								</td>
							</tr>
							<tr>
								<td>
									작성자 : ${reportBoard.id}
								</td>
							</tr>
							<tr>
								<td>
									카테고리 : ${category_info[reportBoard.category_id].group1}, ${category_info[reportBoard.category_id].group2}, ${category_info[reportBoard.category_id].group3}
								</td>
							</tr>
							<tr>
								<td>
									작성일시 : <fmt:formatDate value="${reportBoard.write_date}" pattern="yyyy-MM-dd HH:mm"/>
								</td>
							</tr>
							<tr>
								<td>
									신고 <img src="../image/report.png" width="10" height="10"> : ${reportBoard.report_num}
								</td>
							</tr>
							<tr>
								<td>
									댓글 <img src="../image/icon_14.png" width="10" height="10"> : ${commentCount[reportBoard.board_num]}
								</td>
							</tr>
							<tr>
								<td>
									스크랩 <img src="../image/screp_on.png" width="10" height="10"> :${screpCount[reportBoard.board_num]}
								</td>
							</tr>
						</table>
					</div>
				</c:forEach>
			</div>
    		<div id="popul">
    			<c:forEach var="populBoard" items="${populBoardList}"> 
					<table class="item">
						<tr>
							<td width="20%" class="Popul_Board_Simple_Info" id="popul_lsit_${populBoard.board_num}" title="${populBoard.board_num}">
								게시글 번호 : ${populBoard.board_num}
							</td>
							<td width="20%" class="Popul_Board_Simple_Info" id="popul_lsit_${populBoard.board_num}" title="${populBoard.board_num}">
								작성자 : ${populBoard.id}
							</td>
							<td width="20%" class="Popul_Board_Simple_Info" id="popul_lsit_${populBoard.board_num}" title="${populBoard.board_num}">
								작성일 : <fmt:formatDate value="${populBoard.write_date}" pattern="yyyy-MM-dd HH:mm"/>
							</td>
							<td width="10%">
								<a id="text" href="/content/contentForm.do?board_num=${populBoard.board_num}">
									게시글 <img src="../image/list_icon.png" width="15" height="15" title="게시글 보기"> 
								</a>
							</td>
							<td width="10%">
								<a id="text" href="/content/deleteContent.do?board_num=${populBoard.board_num}&id=${board.id}">
									삭제 <img src="../image/memOut_icon1.png" width="30" height="30" title="삭제"> 
								</a>
							</td>
						</tr>
					</table>
					<div id="popul_info_${populBoard.board_num}" class="Popul_Board_Detaile_Info" title="${populBoard.board_num}" style="display: none;">
						<table class="Popul_Board_Info ul_list">
							<tr>
								<td>
									글 번호 : ${populBoard.board_num}
								</td>
							</tr>
							<tr>
								<td>
									작성자 : ${populBoard.id}
								</td>
							</tr>
							<tr>
								<td>
									카테고리 : ${category_info[populBoard.category_id].group1}, ${category_info[populBoard.category_id].group2}, ${category_info[populBoard.category_id].group3}
								</td>
							</tr>
							<tr>
								<td>
									작성일시 : <fmt:formatDate value="${populBoard.write_date}" pattern="yyyy-MM-dd HH:mm"/>
								</td>
							</tr>
							<tr>
								<td>
									추천 <img src="../image/recommend_off.png" width="10" height="10"> : ${populBoard.recommend_num}
								</td>
							</tr>
							<tr>
								<td>
									댓글 <img src="../image/icon_14.png" width="10" height="10"> : ${commentCount[populBoard.board_num]}
								</td>
							</tr>
							<tr>
								<td>
									스크랩 <img src="../image/screp_on.png" width="10" height="10"> : ${screpCount[populBoard.board_num]}
								</td>
							</tr>
						</table>
					</div>
				</c:forEach>
    		</div>
		</div>
		<div style="height: 5%;"></div>
		</div>
	</body>
</html>