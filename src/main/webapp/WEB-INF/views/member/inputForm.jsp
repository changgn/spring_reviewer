<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
<script src="https://code.jquery.com/jquery-2.2.3.min.js"></script>
<script>
$(document).ready(function(){
	$(document).mousedown(function(e) {
		$('.agree_popup').each(function() {
			if( $(this).css('display') == 'block' ) {
				var objPos = $(this).offset();
				objPos.right = (objPos.left + $(this).width());
				objPos.bottom = (objPos.top + $(this).height());
				if( e.pageX < objPos.left || e.pageX > objPos.right
				 || e.pageY < objPos.top || e.pageY > objPos.bottom ) {
					$('#agree_content').hide();
					alert("aa");
				}
			}
		});
	});
});
$(function(){
	$("#btn_idcheck").click(function(){
		if($("#id").val()==""){
			alert("아이디를 입력하세요");
			$("#id").focus();
			return false;
		}
		var url = "/member/idCheckForm.do?id=" + $("#id").val();
		window.open(url, "_blank", "width=500,height=100");
	}); 
	$("#man").click(function(){
		$("#woman").css("background-color", "white");
		$("#woman a").css("color", "#4C4C4C");
		$(this).css("background-color", "#066E9F");
		$("#man a").css("color", "white");
		
		$(".gender_check").remove();
		$("#divgender").append("<input type='hidden' class='gender_check' name='gender' value='male'>");
		
	});
	$("#woman").click(function(){
		$("#man").css("background-color", "white");
		$("#man a").css("color", "#4C4C4C");
		$(this).css("background-color", "#066E9F");
		$("#woman a").css("color", "white");
		
		$(".gender_check").remove();
		$("#divgender").append("<input type='hidden' class='gender_check' name='gender' value='female'>");
	});
	$("#h_term_agree").click(function(){
		if($("#checkbox_term_agree").prop("checked")) {
			$("#checkbox_term_agree").removeAttr("checked");
		} else {
			$("#checkbox_term_agree").prop("checked", true);
		}
	});
	$("#btn_agree_content").click(function(){
		$("#agree_content").show();
		$("#checkbox_term_agree_content").prop("checked", false);
		$("#checkbox_term_agree").prop("checked", false);
	});
	
	$("#div_checkbox_term_agree_content").click(function(){
		if(!$("#checkbox_term_agree_content").prop("checked")) {
			$("#checkbox_term_agree").prop("checked", true);
			$("#checkbox_term_agree_content").prop("checked", true);
		} 
		$("#agree_content").hide();
	});
	$("#agree_content").click(function(){
		$(this).hide();
	});
	
	$("#join_btn_reset").click(function(){
		$("form")[0].reset();
	});
	$("#join_btn_cancel").click(function(){
		$(location).attr("href","/logon/login.do");
	});
	$("#join_btn_join").click(function(){
		if($("#id").val()==""){
			alert("아이디를 입력하세요");
			$("#id").focus();
			return false;
		}
		if($("#input_check").attr("value")=="nocheck"){
			alert("아이디 중복을 확인해 주세요");
			return false;
		}
		if($("#passwd").val()==""){
			alert("비밀번호를 입력하세요");
			$("#passwd").focus();
			return false;
		}
		if($("#passwd").val()!=$("#passwd2").val()){
			alert("비밀번호가 다릅니다");
			$("#passwd2").val("");
			$("#passwd2").focus();
			return false;
		}
		if($("#name").val()==""){
			alert("이름을 입력하세요");
			$("#name").focus();
			return false;
		}
		if($("#birth").val()==""){
			alert("생년원일을 입력하세요");
			$("#birth").focus();
			return false;
		}
		if($(".gender_check").attr("class")!="gender_check"){
			alert("성별을 입력하세요");
			$("#birth").focus();
			return false;
		}
		if($("#email").val()==""){
			alert("Email을 입력하세요");
			$("#email").focus();
			return false;
		}
		
		if($("#phone_num").val()==""){
			alert("핸드폰 번호를 입력하세요");
			$("#phone_num").focus();
			return false;
		}
		if(!$("#checkbox_term_agree").prop("checked")){
			alert("약관에 동의 해 주세요");
			return false;
		}
		$("#inputForm").submit();
	});
});
</script>
<style>
input[type=checkbox] {  
    display: none;  
}

input[type=checkbox] + label{
    display: inline-block;  
    cursor: pointer;  
    position: relative;  
    padding-left: 40px;  
    padding-bottom: 1px;
    margin-right: 15px;  
    font-size: 27px;
    color: grey;
}

input[type=checkbox]+ label:before {     
    content: "";  
    display: inline-block;  
    width: 27px;  
    height: 27px;  
    margin-right: 20px;  
    position: absolute;  
    left: 0;  
    bottom: 1px;  
    border-radius: 4px; 
    box-shadow: inset 0px 1px 1px 0px rgba(0, 0, 0, .3), 0px 1px 0px 0px rgba(255, 255, 255, .8);  
}

input[type=checkbox]:checked + label:before { 
    content: "\2713";  /* 체크모양 */
    text-shadow: 1px 1px 1px rgba(0, 0, 0, .2);  
    font-size: 40px; 
    font-weight:800; 
    color: #fff;  
    background:#2f87c1;
    text-align: center;  
    line-height: 18px;  
} 
.textarea_agree_content { width: 400px; height: 200px; resize: none; }
.agree_popup{display: inline-block;position: relative; z-index: 10000; width: 500px; background-color: #fff; line-height: normal; vertical-align: middle; top:150px;}
.agree_popup li { margin: 30 0 30 0; font-size: 15px; font-weight: bold;}
</style>
<title>회원가입</title>
</head>
<body>
<div id="join">
	<form method="post" action="/member/join.do" name="inputForm" id="inputForm">
		<div class="size_long"><h1 class="title_find">회 원 가 입</h1></div>
		<div class="size_long" id="divid">
			<div id="join_text_id"><input class="text_login" type="text" name="id" id="id" maxlength="12" placeholder="아이디"></div>
			<div id="btn_idcheck" class="btn_short"><a href="#">확&nbsp;&nbsp;&nbsp;인</a></div>
			<input type="hidden" id="input_check" value="nocheck">
		</div>
		<div class="size_long" id="divpasswd"><input type="password" class="text_login" id="passwd" name="passwd" maxlength="15" placeholder="비밀번호"> </div>
		<div class="size_long" id="divpasswd2"><input type="password" class="text_login" id="passwd2" name="passwd2" maxlength="15" placeholder="비밀번호 재입력"></div>
		<div class="size_long" id="divname"><input type="text" class="text_login" id="name" name="name" maxlength="10" placeholder="이름"></div>
		<div class="size_long" id="divbirth"><input type="text" class="text_login" id="birth" name="birth" maxlength="6" placeholder="생년월일(900111)"></div>
		<div class="size_long" id="divgender">
			<div class="gender" id="man"><a href="#">남자</a></div>
			<div class="gender" id="woman"><a href="#">여자</a></div>
		</div>
		<div class="size_long" id="divemail"><input type="email" class="text_login" id="email" name="email" placeholder="이메일"></div>
		<div class="size_long" id="divphone_num"><input type="text" class="text_login" id="phone_num" name="phone_num" maxlength="12" placeholder="핸드폰 번호(01012345678)"></div>
		<div class="size_long" id="divterms"><input id="checkbox_term_agree" type="checkbox"><label for="checkbox_term_agree"> 약 관 동 의 </label><a id="btn_agree_content" href="#">(약관보기)</a></div>
		<div id="join_btn_join" class="btn_long"><a href="#" >회 원 가 입</a></div>
		<div id="join_btn_reset" class="btn_long"><a href="#" >다 시 작 성</a></div>
		<div id="join_btn_cancel" class="btn_long"><a href="#" >취 소</a></div>
	</form>
</div>
<div id="agree_content" class="cont_btn_option">
	<div class="ly_dimmed"></div>
		<ul class="agree_popup">
			<li>
				REVIEWER 이용약관<br /><br />
				<textarea class="textarea_agree_content" readonly="readonly">
 제 1 조 (목적)
 이 약관은 REVIEWER 주식회사 ("회사" 또는 "REVIEWER")가 제공하는 REVIEWER 및 REVIEWER 관련 제반 서비스의 이용과 관련하여 회사와 회원과의 권리, 의무 및 책임사항, 기타 필요한 사항을 규정함을 목적으로 합니다.
			
 제 2 조 (정의)
 이 약관에서 사용하는 용어의 정의는 다음과 같습니다.
①"서비스"라 함은 구현되는 단말기(PC, TV, 휴대형단말기 등의 각종 유무선 장치를 포함)와 상관없이 "회원"이 이용할 수 있는 REVIEWER 및 REVIEWER 관련 제반 서비스를 의미합니다.
②"회원"이라 함은 회사의 "서비스"에 접속하여 이 약관에 따라 "회사"와 이용계약을 체결하고 "회사"가 제공하는 "서비스"를 이용하는 고객을 말합니다. 
③"아이디(ID)"라 함은 "회원"의 식별과 "서비스" 이용을 위하여 "회원"이 정하고 "회사"가 승인하는 문자와 숫자의 조합을 의미합니다. 
④"비밀번호"라 함은 "회원"이 부여 받은 "아이디와 일치되는 "회원"임을 확인하고 비밀보호를 위해 "회원" 자신이 정한 문자 또는 숫자의 조합을 의미합니다. 
⑤"유료서비스"라 함은 "회사"가 유료로 제공하는 각종 온라인디지털콘텐츠(각종 정보콘텐츠, VOD, 아이템 기타 유료콘텐츠를 포함) 및 제반 서비스를 의미합니다. 
⑥"포인트"라 함은 서비스의 효율적 이용을 위해 회사가 임의로 책정 또는 지급, 조정할 수 있는 재산적 가치가 없는 "서비스" 상의 가상 데이터를 의미합니다. 
⑦"게시물"이라 함은 "회원"이 "서비스"를 이용함에 있어 "서비스상"에 게시한 부호ㆍ문자ㆍ음성ㆍ음향ㆍ화상ㆍ동영상 등의 정보 형태의 글, 사진, 동영상 및 각종 파일과 링크 등을 의미합니다.

 제 3 조 (약관의 게시와 개정)
①"회사"는 이 약관의 내용을 "회원"이 쉽게 알 수 있도록 서비스 초기 화면에 게시합니다. 
②"회사"는 "약관의규제에관한법률", "정보통신망이용촉진및정보보호등에관한법률(이하 "정보통신망법")" 등 관련법을 위배하지 않는 범위에서 이 약관을 개정할 수 있습니다. 
③"회사"가 약관을 개정할 경우에는 적용일자 및 개정사유를 명시하여 현행약관과 함께 제1항의 방식에 따라 그 개정약관의 적용일자 30일 전부터 적용일자 전일까지 공지합니다. 다만, 회원에게 불리한 약관의 개정의 경우에는 공지 외에 일정기간 서비스내 전자우편, 전자쪽지, 로그인시 동의창 등의 전자적 수단을 통해 따로 명확히 통지하도록 합니다. 
④회사가 전항에 따라 개정약관을 공지 또는 통지하면서 회원에게 30일 기간 내에 의사표시를 하지 않으면 의사표시가 표명된 것으로 본다는 뜻을 명확하게 공지 또는 통지하였음에도 회원이 명시적으로 거부의 의사표시를 하지 아니한 경우 회원이 개정약관에 동의한 것으로 봅니다. 
⑤회원이 개정약관의 적용에 동의하지 않는 경우 회사는 개정 약관의 내용을 적용할 수 없으며, 이 경우 회원은 이용계약을 해지할 수 있습니다. 다만, 기존 약관을 적용할 수 없는 특별한 사정이 있는 경우에는 회사는 이용계약을 해지할 수 있습니다.

 제 4 조 (약관의 해석)
①"회사"는 "유료서비스" 및 개별 서비스에 대해서는 별도의 이용약관 및 정책(이하 "유료서비스약관 등")을 둘 수 있으며, 해당 내용이 이 약관과 상충할 경우에는 "유료서비스약관 등"이 우선하여 적용됩니다. 
②이 약관에서 정하지 아니한 사항이나 해석에 대해서는 "유료서비스약관 등" 및 관계법령 또는 상관례에 따릅니다.

 제 5 조 (이용계약 체결)
①이용계약은 "회원"이 되고자 하는 자(이하 "가입신청자")가 약관의 내용에 대하여 동의를 한 다음 회원가입신청을 하고 "회사"가 이러한 신청에 대하여 승낙함으로써 체결됩니다. 
②"회사"는 "가입신청자"의 신청에 대하여 "서비스" 이용을 승낙함을 원칙으로 합니다. 다만, "회사"는 다음 각 호에 해당하는 신청에 대하여는 승낙을 하지 않거나 사후에 이용계약을 해지할 수 있습니다. 
1. 1.가입신청자가 이 약관에 의하여 이전에 회원자격을 상실한 적이 있는 경우, 단 "회사"의 회원 재가입 승낙을 얻은 경우에는 예외로 함. 
2. 2.실명이 아니거나 타인의 명의를 이용한 경우 
3. 3.허위의 정보를 기재하거나, "회사"가 제시하는 내용을 기재하지 않은 경우 
4. 4.14세 미만 아동이 법정대리인(부모 등)의 동의를 얻지 아니한 경우 
5. 5.이용자의 귀책사유로 인하여 승인이 불가능하거나 기타 규정한 제반 사항을 위반하며 신청하는 경우
③제1항에 따른 신청에 있어 "회사"는 "회원"의 종류에 따라 전문기관을 통한 실명확인 및 본인인증을 요청할 수 있습니다. 
④"회사"는 서비스관련설비의 여유가 없거나, 기술상 또는 업무상 문제가 있는 경우에는 승낙을 유보할 수 있습니다. 
⑤제2항과 제4항에 따라 회원가입신청의 승낙을 하지 아니하거나 유보한 경우, "회사"는 원칙적으로 이를 가입신청자에게 알리도록 합니다. 
⑥이용계약의 성립 시기는 "회사"가 가입완료를 신청절차 상에서 표시한 시점으로 합니다. 
⑦"회사"는 "회원"에 대해 회사정책에 따라 등급별로 구분하여 이용시간, 이용횟수, 서비스 메뉴 등을 세분하여 이용에 차등을 둘 수 있습니다. 
⑧"회사"는 "회원"에 대하여 "영화및비디오물의진흥에관한법률" 및 "청소년보호법"등에 따른 등급 및 연령 준수를 위해 이용제한이나 등급별 제한을 할 수 있습니다.
			</textarea>
			</li>
			<li>
				개인정보 수집 및 이용에 대한 안내<br /><br />
				<textarea class="textarea_agree_content" readonly="readonly">
정보통신망법 규정에 따라 REVIEWER에 회원가입 신청하시는 분께 수집하는 개인정보의 항목, 개인정보의 수집 및 이용목적, 개인정보의 보유 및 이용기간을 안내 드리오니 자세히 읽은 후 동의하여 주시기 바랍니다.

1. 수집하는 개인정보
이용자는 회원가입을 하지 않아도 정보 검색, 뉴스 보기 등 대부분의 REVIEWER 서비스를 회원과 동일하게 이용할 수 있습니다. 이용자가 메일, 캘린더, 카페, 블로그 등과 같이 개인화 혹은 회원제 서비스를 이용하기 위해 회원가입을 할 경우, REVIEWER는 서비스 이용을 위해 필요한 최소한의 개인정보를 수집합니다.

회원가입 시점에 REVIEWER가 이용자로부터 수집하는 개인정보는 아래와 같습니다.
- 회원 가입 시에 ‘아이디, 비밀번호, 이름, 생년월일, 성별, 가입인증 휴대폰번호’를 필수항목으로 수집합니다. 만약 이용자가 입력하는 생년월일이 만14세 미만 아동일 경우에는 법정대리인 정보를 추가로 수집합니다. 그리고 선택항목으로 이메일 주소를 수집합니다.
- 단체아이디로 회원 가입 시에 단체아이디, 단체이름, 법인명(단체명), 대표자명, 대표 전화번호, 사업장소재지, 관리자 아이디, 관리자 휴대폰번호를 필수항목으로 수집합니다.
서비스 이용 과정에서 이용자로부터 수집하는 개인정보는 아래와 같습니다.
REVIEWER 내의 개별 서비스 이용, 이벤트 응모 및 경품 신청 과정에서 해당 서비스의 이용자에 한해 추가 개인정보 수집이 발생할 수 있습니다. 추가로 개인정보를 수집할 경우에는 해당 개인정보 수집 시점에서 이용자에게 ‘수집하는 개인정보 항목, 개인정보의 수집 및 이용목적, 개인정보의 보관기간’에 대해 안내 드리고 동의를 받습니다.
서비스 이용 과정에서 IP 주소, 쿠키, 방문일시·불량 이용 기록 등의 서비스 이용 기록, 기기정보가 생성되어 수집될 수 있습니다.
구체적으로 1) 서비스 이용 과정에서 이용자에 관한 정보를 정보통신서비스 제공자가 자동화된 방법으로 생성하여 이를 저장(수집)하거나, 2) 이용자 기기의 고유한 정보를 원래의 값을 확인하지 못하도록 안전하게 변환한 후에 수집하는 경우를 의미합니다.

2. 수집한 개인정보의 이용
REVIEWER는 회원관리, 서비스 개발・제공 및 향상, 안전한 인터넷 이용환경 구축 등 아래의 목적으로만 개인정보를 이용합니다.
- 회원 가입 의사의 확인, 연령 확인 및 법정대리인 동의 진행, 이용자 및 법정대리인의 본인 확인, 이용자 식별, 회원탈퇴 의사의 확인 등 회원관리를 위하여 개인정보를 이용합니다.
- 콘텐츠 등 기존 서비스 제공(광고 포함)에 더하여, 인구통계학적 분석, 서비스 방문 및 이용기록의 분석, 개인정보 및 관심에 기반한 이용자간 관계의 형성, 지인 및 관심사 등에 기반한 맞춤형 서비스 제공 등 신규 서비스 요소의 발굴 및 기존 서비스 개선 등을 위하여 개인정보를 이용합니다.
- 법령 및 REVIEWER 이용약관을 위반하는 회원에 대한 이용 제한 조치, 부정 이용 행위를 포함하여 서비스의 원활한 운영에 지장을 주는 행위에 대한 방지 및 제재, 계정도용 및 부정거래 방지, 약관 개정 등의 고지사항 전달, 분쟁조정을 위한 기록 보존, 민원처리 등 이용자 보호 및 서비스 운영을 위하여 개인정보를 이용합니다.
- 유료 서비스 제공에 따르는 본인인증, 구매 및 요금 결제, 상품 및 서비스의 배송을 위하여 개인정보를 이용합니다.
- 이벤트 정보 및 참여기회 제공, 광고성 정보 제공 등 마케팅 및 프로모션 목적으로 개인정보를 이용합니다.
- 서비스 이용기록과 접속 빈도 분석, 서비스 이용에 대한 통계, 서비스 분석 및 통계에 따른 맞춤 서비스 제공 및 광고 게재 등에 개인정보를 이용합니다.
- 보안, 프라이버시, 안전 측면에서 이용자가 안심하고 이용할 수 있는 서비스 이용환경 구축을 위해 개인정보를 이용합니다.
				</textarea>
			</li>
			<li>
				<div id="div_checkbox_term_agree_content"><input id="checkbox_term_agree_content" type="checkbox"><label for="checkbox_term_agree_content"> 약 관 동 의 </label></div>
			</li>
		</ul>
	</div>
</body>
</html>