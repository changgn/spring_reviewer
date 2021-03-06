<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<html>
<head>
<script src='http://jquery-multifile-plugin.googlecode.com/svn/trunk/jquery.form.js' type="text/javascript"></script>
<script src='http://jquery-multifile-plugin.googlecode.com/svn/trunk/jquery.MetaData.js' type="text/javascript"></script>
<script src='http://jquery-multifile-plugin.googlecode.com/svn/trunk/jquery.MultiFile.js' type="text/javascript"></script>
<script src='http://jquery-multifile-plugin.googlecode.com/svn/trunk/jquery.blockUI.js' type="text/javascript"></script> 
<script src="/script/categoryMenu_write.js" type="text/javascript"></script>
<script src="/script/upload.js" type="text/javascript"></script>
<script src="https://code.jquery.com/jquery-2.2.3.min.js"></script>
<title>글쓰기</title>

</head>
<body>
<div id="writeForm">
	<div class="category_add">
		<div id="group">
			<div id="group1" class="size_long category_setting">
				<div id="group1_1" class="btn_group1"><a href="#" onclick="event.preventDefault();">가&nbsp;&nbsp;전</a></div>
				<div id="group1_2" class="btn_group1"><a href="#" onclick="event.preventDefault();">가&nbsp;&nbsp;구</a></div>
				<div id="group1_3" class="btn_group1"><a href="#" onclick="event.preventDefault();">유&nbsp;&nbsp;아</a></div>
				<div id="group1_4" class="btn_group1"><a href="#" onclick="event.preventDefault();">생&nbsp;&nbsp;활</a></div>
				<div id="group1_5" class="btn_group1"><a href="#" onclick="event.preventDefault();">뷰&nbsp;&nbsp;티</a></div>
			</div>
			<div id="group2">
				<div class="group2">
					<div id="group2_11" class="btn_group2"><a href="#" onclick="event.preventDefault();">컴퓨터</a></div>
					<div id="group2_12" class="btn_group2"><a href="#" onclick="event.preventDefault();">주방가전</a></div>
					<div id="group2_13" class="btn_group2"><a href="#" onclick="event.preventDefault();">생활가전</a></div>
					<div id="group2_14" class="btn_group2"><a href="#" onclick="event.preventDefault();">계절가전</a></div>
					<div id="group2_15" class="btn_group2"><a href="#" onclick="event.preventDefault();">모바일</a></div>
				</div>
				<div class="group2">
					<div id="group2_21" class="btn_group2"><a href="#" onclick="event.preventDefault();">침대</a></div>
					<div id="group2_22" class="btn_group2"><a href="#" onclick="event.preventDefault();">수납가구</a></div>
					<div id="group2_23" class="btn_group2"><a href="#" onclick="event.preventDefault();">사무가구</a></div>
					<div id="group2_24" class="btn_group2"><a href="#" onclick="event.preventDefault();">거실가구</a></div>
				</div>
				<div class="group2">
					<div id="group2_31" class="btn_group2"><a href="#" onclick="event.preventDefault();">기저귀/분유/물티슈</a></div>
					<div id="group2_32" class="btn_group2"><a href="#" onclick="event.preventDefault();">목욕/위생/화장품</a></div>
					<div id="group2_33" class="btn_group2"><a href="#" onclick="event.preventDefault();">유모차/카시트</a></div>
					<div id="group2_34" class="btn_group2"><a href="#" onclick="event.preventDefault();">완구/도서</a></div>
				</div>
				<div class="group2">
					<div id="group2_41" class="btn_group2"><a href="#" onclick="event.preventDefault();">주방용품</a></div>
					<div id="group2_42" class="btn_group2"><a href="#" onclick="event.preventDefault();">세탁/청소용품</a></div>
					<div id="group2_43" class="btn_group2"><a href="#" onclick="event.preventDefault();">욕실/위생용품</a></div>
					<div id="group2_44" class="btn_group2"><a href="#" onclick="event.preventDefault();">애완용품</a></div>
				</div>
				<div class="group2">
					<div id="group2_51" class="btn_group2"><a href="#" onclick="event.preventDefault();">가방/지갑</a></div>
					<div id="group2_52" class="btn_group2"><a href="#" onclick="event.preventDefault();">시계/쥬얼리</a></div>
					<div id="group2_53" class="btn_group2"><a href="#" onclick="event.preventDefault();">신발</a></div>
					<div id="group2_54" class="btn_group2"><a href="#" onclick="event.preventDefault();">잡화/소품</a></div>
					<div id="group2_55" class="btn_group2"><a href="#" onclick="event.preventDefault();">의류/언더웨어</a></div>
				</div>
			</div>
			<div id="group3_11">
				<div class="group3_btn">
					<div id="111" class="btn_group3"><a href="#" onclick="event.preventDefault();">모니터</a></div>
					<div id="112" class="btn_group3"><a href="#" onclick="event.preventDefault();">노트북</a></div>
					<div id="113" class="btn_group3"><a href="#" onclick="event.preventDefault();">본체</a></div>
					<div id="114" class="btn_group3"><a href="#" onclick="event.preventDefault();">주변기기</a></div>
					<div id="115" class="btn_group3"><a href="#" onclick="event.preventDefault();">기타부품</a></div>
				</div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
			</div>
			<div id="group3_12">
				<div class="group3_btn">
					<div id="121" class="btn_group3"><a href="#" onclick="event.preventDefault();">냉장고</a></div>
					<div id="122" class="btn_group3"><a href="#" onclick="event.preventDefault();">김치냉장고</a></div>
					<div id="123" class="btn_group3"><a href="#" onclick="event.preventDefault();">정수기</a></div>
					<div id="124" class="btn_group3"><a href="#" onclick="event.preventDefault();">전기밥솥</a></div>
					<div id="125" class="btn_group3"><a href="#" onclick="event.preventDefault();">전자레인지</a></div>
				</div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
			</div>
			<div id="group3_13">
				<div class="group3_btn">
					<div id="131" class="btn_group3"><a href="#" onclick="event.preventDefault();">TV</a></div>
					<div id="132" class="btn_group3"><a href="#" onclick="event.preventDefault();">세탁기</a></div>
					<div id="133" class="btn_group3"><a href="#" onclick="event.preventDefault();">청소기</a></div>
					<div id="134" class="btn_group3"><a href="#" onclick="event.preventDefault();">카메라</a></div>
					<div id="135" class="btn_group3"><a href="#" onclick="event.preventDefault();">기타가전</a></div>
				</div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
			</div>
			<div id="group3_14">
				<div class="group3_btn">
					<div id="141" class="btn_group3"><a href="#" onclick="event.preventDefault();">에어컨</a></div>
					<div id="142" class="btn_group3"><a href="#" onclick="event.preventDefault();">선풍기</a></div>
					<div id="143" class="btn_group3"><a href="#" onclick="event.preventDefault();">공기청정기</a></div>
					<div id="144" class="btn_group3"><a href="#" onclick="event.preventDefault();">가습기</a></div>
					<div id="145" class="btn_group3"><a href="#" onclick="event.preventDefault();">난방기</a></div>
				</div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
			</div>
			<div id="group3_15">
				<div class="group3_btn">
					<div id="151" class="btn_group3"><a href="#" onclick="event.preventDefault();">휴대폰</a></div>
					<div id="152" class="btn_group3"><a href="#" onclick="event.preventDefault();">태블릿</a></div>
					<div id="153" class="btn_group3"><a href="#" onclick="event.preventDefault();">악세사리</a></div>
					<div id="154" class="btn_group3"><a href="#" onclick="event.preventDefault();">스마트워치</a></div>
					<div id="155" class="btn_group3"><a href="#" onclick="event.preventDefault();">기타가전</a></div>
				</div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
			</div>
			<div id="group3_21">
				<div class="group3_btn"></div>
				<div class="group3_btn">
					<div id="211" class="btn_group3"><a href="#" onclick="event.preventDefault();">침대</a></div>
					<div id="212" class="btn_group3"><a href="#" onclick="event.preventDefault();">매트리스</a></div>
					<div id="213" class="btn_group3"><a href="#" onclick="event.preventDefault();">화장대</a></div>
					<div id="214" class="btn_group3"><a href="#" onclick="event.preventDefault();">침대프레임</a></div>
					<div id="215" class="btn_group3"><a href="#" onclick="event.preventDefault();">침구</a></div>
				</div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
			</div>
			<div id="group3_22">
				<div class="group3_btn"></div>
				<div class="group3_btn">
					<div id="221" class="btn_group3"><a href="#" onclick="event.preventDefault();">장롱</a></div>
					<div id="222" class="btn_group3"><a href="#" onclick="event.preventDefault();">행거</a></div>
					<div id="223" class="btn_group3"><a href="#" onclick="event.preventDefault();">수납장</a></div>
					<div id="224" class="btn_group3"><a href="#" onclick="event.preventDefault();">선반</a></div>
					<div id="225" class="btn_group3"><a href="#" onclick="event.preventDefault();">식탁</a></div>
				</div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
			</div>
			<div id="group3_23">
				<div class="group3_btn"></div>
				<div class="group3_btn">
					<div id="231" class="btn_group3"><a href="#" onclick="event.preventDefault();">책상</a></div>
					<div id="232" class="btn_group3"><a href="#" onclick="event.preventDefault();">의자</a></div>
					<div id="233" class="btn_group3"><a href="#" onclick="event.preventDefault();">책장</a></div>
					<div id="234" class="btn_group3"><a href="#" onclick="event.preventDefault();">책상소품</a></div>
					<div id="235" class="btn_group3"><a href="#" onclick="event.preventDefault();">기타가구</a></div>
				</div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
			</div>
			<div id="group3_24">
				<div class="group3_btn"></div>
				<div class="group3_btn">
					<div id="241" class="btn_group3"><a href="#" onclick="event.preventDefault();">소파</a></div>
					<div id="242" class="btn_group3"><a href="#" onclick="event.preventDefault();">TV거실장</a></div>
					<div id="243" class="btn_group3"><a href="#" onclick="event.preventDefault();">거실테이블</a></div>
					<div id="244" class="btn_group3"><a href="#" onclick="event.preventDefault();">장식장</a></div>
					<div id="245" class="btn_group3"><a href="#" onclick="event.preventDefault();">커튼</a></div>
					<div id="246" class="btn_group3"><a href="#" onclick="event.preventDefault();">기타가구</a></div>
				</div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
			</div>
			<div id="group3_31">
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn">
					<div id="311" class="btn_group3"><a href="#" onclick="event.preventDefault();">기저귀</a></div>
					<div id="312" class="btn_group3"><a href="#" onclick="event.preventDefault();">국내분유</a></div>
					<div id="313" class="btn_group3"><a href="#" onclick="event.preventDefault();">해외분유</a></div>
					<div id="314" class="btn_group3"><a href="#" onclick="event.preventDefault();">물티슈</a></div>
					<div id="315" class="btn_group3"><a href="#" onclick="event.preventDefault();">이유식</a></div>
				</div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
			</div>
			<div id="group3_32">
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn">
					<div id="321" class="btn_group3"><a href="#" onclick="event.preventDefault();">샴푸/바디</a></div>
					<div id="322" class="btn_group3"><a href="#" onclick="event.preventDefault();">목욕용품</a></div>
					<div id="323" class="btn_group3"><a href="#" onclick="event.preventDefault();">유아화장품</a></div>
					<div id="324" class="btn_group3"><a href="#" onclick="event.preventDefault();">위생용품</a></div>
					<div id="325" class="btn_group3"><a href="#" onclick="event.preventDefault();">기타용품</a></div>
				</div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
			</div>
			<div id="group3_33">
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn">
					<div id="331" class="btn_group3"><a href="#" onclick="event.preventDefault();">유모차</a></div>
					<div id="332" class="btn_group3"><a href="#" onclick="event.preventDefault();">카시트</a></div>
					<div id="333" class="btn_group3"><a href="#" onclick="event.preventDefault();">아기띠</a></div>
					<div id="334" class="btn_group3"><a href="#" onclick="event.preventDefault();">보행기</a></div>
					<div id="335" class="btn_group3"><a href="#" onclick="event.preventDefault();">기타용품</a></div>
				</div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
			</div>
			<div id="group3_34">
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn">
					<div id="341" class="btn_group3"><a href="#" onclick="event.preventDefault();">레고</a></div>
					<div id="342" class="btn_group3"><a href="#" onclick="event.preventDefault();">작동완구</a></div>
					<div id="343" class="btn_group3"><a href="#" onclick="event.preventDefault();">교육/블럭완구</a></div>
					<div id="344" class="btn_group3"><a href="#" onclick="event.preventDefault();">인형</a></div>
					<div id="345" class="btn_group3"><a href="#" onclick="event.preventDefault();">기타완구</a></div>
				</div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
			</div>
			<div id="group3_41">
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn">
					<div id="411" class="btn_group3"><a href="#" onclick="event.preventDefault();">냄비</a></div>
					<div id="412" class="btn_group3"><a href="#" onclick="event.preventDefault();">프라이팬</a></div>
					<div id="413" class="btn_group3"><a href="#" onclick="event.preventDefault();">식기</a></div>
					<div id="414" class="btn_group3"><a href="#" onclick="event.preventDefault();">용기</a></div>
					<div id="415" class="btn_group3"><a href="#" onclick="event.preventDefault();">기타</a></div>
				</div>
				<div class="group3_btn"></div>
			</div>
			<div id="group3_42">
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn">
					<div id="421" class="btn_group3"><a href="#" onclick="event.preventDefault();">세제</a></div>
					<div id="422" class="btn_group3"><a href="#" onclick="event.preventDefault();">섬유유연제</a></div>
					<div id="423" class="btn_group3"><a href="#" onclick="event.preventDefault();">주방세제</a></div>
					<div id="424" class="btn_group3"><a href="#" onclick="event.preventDefault();">청소용품</a></div>
					<div id="425" class="btn_group3"><a href="#" onclick="event.preventDefault();">기타</a></div>
				</div>
				<div class="group3_btn"></div>
			</div>
			<div id="group3_43">
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn">
					<div id="431" class="btn_group3"><a href="#" onclick="event.preventDefault();">욕실인테리어</a></div>
					<div id="432" class="btn_group3"><a href="#" onclick="event.preventDefault();">수건</a></div>
					<div id="433" class="btn_group3"><a href="#" onclick="event.preventDefault();">샤워기/욕조</a></div>
					<div id="434" class="btn_group3"><a href="#" onclick="event.preventDefault();">화장지</a></div>
					<div id="435" class="btn_group3"><a href="#" onclick="event.preventDefault();">기타</a></div>
				</div>
				<div class="group3_btn"></div>
			</div>
			<div id="group3_44">
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn">
					<div id="441" class="btn_group3"><a href="#" onclick="event.preventDefault();">애견사료</a></div>
					<div id="442" class="btn_group3"><a href="#" onclick="event.preventDefault();">간식/영양제</a></div>
					<div id="443" class="btn_group3"><a href="#" onclick="event.preventDefault();">집/이동장/생활</a></div>
					<div id="444" class="btn_group3"><a href="#" onclick="event.preventDefault();">위생/장난감</a></div>
					<div id="445" class="btn_group3"><a href="#" onclick="event.preventDefault();">기타</a></div>
				</div>
				<div class="group3_btn"></div>
			</div>
			<div id="group3_51">
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn">
					<div id="511" class="btn_group3"><a href="#" onclick="event.preventDefault();">여성가방</a></div>
					<div id="512" class="btn_group3"><a href="#" onclick="event.preventDefault();">남성가방</a></div>
					<div id="513" class="btn_group3"><a href="#" onclick="event.preventDefault();">캐쥬얼가방</a></div>
					<div id="514" class="btn_group3"><a href="#" onclick="event.preventDefault();">여행가방</a></div>
					<div id="515" class="btn_group3"><a href="#" onclick="event.preventDefault();">지갑</a></div>
				</div>
			</div>
			<div id="group3_52">
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn">
					<div id="521" class="btn_group3"><a href="#" onclick="event.preventDefault();">시계</a></div>
					<div id="522" class="btn_group3"><a href="#" onclick="event.preventDefault();">스마트워치</a></div>
					<div id="523" class="btn_group3"><a href="#" onclick="event.preventDefault();">헤어액서서리</a></div>
					<div id="524" class="btn_group3"><a href="#" onclick="event.preventDefault();">목걸이</a></div>
					<div id="525" class="btn_group3"><a href="#" onclick="event.preventDefault();">반지/귀걸이</a></div>
				</div>
			</div>
			<div id="group3_53">
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn">
					<div id="531" class="btn_group3"><a href="#" onclick="event.preventDefault();">여성구두</a></div>
					<div id="532" class="btn_group3"><a href="#" onclick="event.preventDefault();">남성구두</a></div>
					<div id="533" class="btn_group3"><a href="#" onclick="event.preventDefault();">운동화</a></div>
					<div id="534" class="btn_group3"><a href="#" onclick="event.preventDefault();">샌들/슬리퍼</a></div>
					<div id="535" class="btn_group3"><a href="#" onclick="event.preventDefault();">워커/부츠</a></div>
				</div>
			</div>
			<div id="group3_54">
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn">
					<div id="541" class="btn_group3"><a href="#" onclick="event.preventDefault();">선글라스/안경</a></div>
					<div id="542" class="btn_group3"><a href="#" onclick="event.preventDefault();">우산/양산</a></div>
					<div id="543" class="btn_group3"><a href="#" onclick="event.preventDefault();">스카프/머플러</a></div>
					<div id="544" class="btn_group3"><a href="#" onclick="event.preventDefault();">모자</a></div>
					<div id="545" class="btn_group3"><a href="#" onclick="event.preventDefault();">장갑/벨트/넥타이</a></div>
					<div id="546" class="btn_group3"><a href="#" onclick="event.preventDefault();">기타</a></div>
				</div>
			</div>
			<div id="group3_55">
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn">
					<div id="551" class="btn_group3"><a href="#" onclick="event.preventDefault();">남성의류</a></div>
					<div id="552" class="btn_group3"><a href="#" onclick="event.preventDefault();">여성의류</a></div>
					<div id="553" class="btn_group3"><a href="#" onclick="event.preventDefault();">캐주얼의류</a></div>
					<div id="554" class="btn_group3"><a href="#" onclick="event.preventDefault();">아동의류</a></div>
					<div id="555" class="btn_group3"><a href="#" onclick="event.preventDefault();">기타</a></div>
				</div>
			</div>
		</div>
		<div class="category_added"></div>
	</div>
	<form action="/write/writeForm.do" id="writeBoard" method="post" enctype="multipart/form-data">
		<div id="content_area">
			<textarea id="text_content" name="boardContent" rows="20" cols="auto"></textarea>
		</div>
		<div id="upload_area">
			<input type="file" id="afile" name="afile" class="multi" maxlength="5">
		</div>
		<div id="text_content">
			<div id="btn_write" class="btn_long"><a href="#" onclick="event.preventDefault();">작&nbsp; 성&nbsp; 완&nbsp; 료</a></div>
			<div id="join_btn_cancel" class="btn_long" onclick="event.preventDefault();"><a href="#" >취&nbsp;  소</a></div>
		</div>
	</form>
</div>
</body>
</html>