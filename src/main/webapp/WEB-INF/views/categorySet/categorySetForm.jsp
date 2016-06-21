<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
<head>
<title>카테고리 설정</title>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
<script src="/script/categoryMenu.js"></script>

</head>
<body>
	<div class="category_add">
		<div class="size_long"><h1 class="title_find">카 테 고 리 설 정</h1></div>
		<div id="group">
			<div id="group1" class="size_long category_setting">
				<div class="btn_group1"><a id="group1_1" href="#" onclick="event.preventDefault();">가&nbsp;&nbsp;전</a></div>
				<div class="btn_group1"><a id="group1_2" href="#" onclick="event.preventDefault();">가&nbsp;&nbsp;구</a></div>
				<div class="btn_group1"><a id="group1_3" href="#" onclick="event.preventDefault();">유&nbsp;&nbsp;아</a></div>
				<div class="btn_group1"><a id="group1_4" href="#" onclick="event.preventDefault();">생&nbsp;&nbsp;활</a></div>
				<div class="btn_group1"><a id="group1_5" href="#" onclick="event.preventDefault();">뷰&nbsp;&nbsp;티</a></div>
			</div>
			<div id="group2">
				<div class="group2">
					<div class="btn_group2"><a id="group2_11" href="#" onclick="event.preventDefault();">컴퓨터</a></div>
					<div class="btn_group2"><a id="group2_12" href="#" onclick="event.preventDefault();">주방가전</a></div>
					<div class="btn_group2"><a id="group2_13" href="#" onclick="event.preventDefault();">생활가전</a></div>
					<div class="btn_group2"><a id="group2_14" href="#" onclick="event.preventDefault();">계절가전</a></div>
					<div class="btn_group2"><a id="group2_15" href="#" onclick="event.preventDefault();">모바일</a></div>
				</div>
				<div class="group2">
					<div class="btn_group2"><a id="group2_21" href="#" onclick="event.preventDefault();">침대</a></div>
					<div class="btn_group2"><a id="group2_22" href="#" onclick="event.preventDefault();">수납가구</a></div>
					<div class="btn_group2"><a id="group2_23" href="#" onclick="event.preventDefault();">사무가구</a></div>
					<div class="btn_group2"><a id="group2_24" href="#" onclick="event.preventDefault();">거실가구</a></div>
				</div>
				<div class="group2">
					<div class="btn_group2"><a id="group2_31" href="#" onclick="event.preventDefault();">기저귀/분유/물티슈</a></div>
					<div class="btn_group2"><a id="group2_32" href="#" onclick="event.preventDefault();">목욕/위생/화장품</a></div>
					<div class="btn_group2"><a id="group2_33" href="#" onclick="event.preventDefault();">유모차/카시트</a></div>
					<div class="btn_group2"><a id="group2_34" href="#" onclick="event.preventDefault();">완구/도서</a></div>
				</div>
				<div class="group2">
					<div class="btn_group2"><a id="group2_41" href="#" onclick="event.preventDefault();">주방용품</a></div>
					<div class="btn_group2"><a id="group2_42" href="#" onclick="event.preventDefault();">세탁/청소용품</a></div>
					<div class="btn_group2"><a id="group2_43" href="#" onclick="event.preventDefault();">욕실/위생용품</a></div>
					<div class="btn_group2"><a id="group2_44" href="#" onclick="event.preventDefault();">애완용품</a></div>
				</div>
				<div class="group2">
					<div class="btn_group2"><a id="group2_51" href="#" onclick="event.preventDefault();">가방/지갑</a></div>
					<div class="btn_group2"><a id="group2_52" href="#" onclick="event.preventDefault();">시계/쥬얼리</a></div>
					<div class="btn_group2"><a id="group2_53" href="#" onclick="event.preventDefault();">신발</a></div>
					<div class="btn_group2"><a id="group2_54" href="#" onclick="event.preventDefault();">잡화/소품</a></div>
					<div class="btn_group2"><a id="group2_55" href="#" onclick="event.preventDefault();">의류/언더웨어</a></div>
				</div>
			</div>
			<div id="group3_11">
				<div class="group3_btn">
					<div class="btn_group3"><a id="111" href="#" onclick="event.preventDefault();">모니터</a></div>
					<div class="btn_group3"><a id="112" href="#" onclick="event.preventDefault();">노트북</a></div>
					<div class="btn_group3"><a id="113" href="#" onclick="event.preventDefault();">본체</a></div>
					<div class="btn_group3"><a id="114" href="#" onclick="event.preventDefault();">주변기기</a></div>
					<div class="btn_group3"><a id="115" href="#" onclick="event.preventDefault();">기타부품</a></div>
				</div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
			</div>
			<div id="group3_12">
				<div class="group3_btn">
					<div class="btn_group3"><a id="121" href="#" onclick="event.preventDefault();">냉장고</a></div>
					<div class="btn_group3"><a id="122" href="#" onclick="event.preventDefault();">김치냉장고</a></div>
					<div class="btn_group3"><a id="123" href="#" onclick="event.preventDefault();">정수기</a></div>
					<div class="btn_group3"><a id="124" href="#" onclick="event.preventDefault();">전기밥솥</a></div>
					<div class="btn_group3"><a id="125" href="#" onclick="event.preventDefault();">전자레인지</a></div>
				</div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
			</div>
			<div id="group3_13">
				<div class="group3_btn">
					<div class="btn_group3"><a id="131" href="#" onclick="event.preventDefault();">TV</a></div>
					<div class="btn_group3"><a id="132" href="#" onclick="event.preventDefault();">세탁기</a></div>
					<div class="btn_group3"><a id="133" href="#" onclick="event.preventDefault();">청소기</a></div>
					<div class="btn_group3"><a id="134" href="#" onclick="event.preventDefault();">카메라</a></div>
					<div class="btn_group3"><a id="135" href="#" onclick="event.preventDefault();">기타가전</a></div>
				</div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
			</div>
			<div id="group3_14">
				<div class="group3_btn">
					<div class="btn_group3"><a id="141" href="#" onclick="event.preventDefault();">에어컨</a></div>
					<div class="btn_group3"><a id="142" href="#" onclick="event.preventDefault();">선풍기</a></div>
					<div class="btn_group3"><a id="143" href="#" onclick="event.preventDefault();">공기청정기</a></div>
					<div class="btn_group3"><a id="144" href="#" onclick="event.preventDefault();">가습기</a></div>
					<div class="btn_group3"><a id="145" href="#" onclick="event.preventDefault();">난방기</a></div>
				</div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
			</div>
			<div id="group3_15">
				<div class="group3_btn">
					<div class="btn_group3"><a id="151" href="#" onclick="event.preventDefault();">휴대폰</a></div>
					<div class="btn_group3"><a id="152" href="#" onclick="event.preventDefault();">태블릿</a></div>
					<div class="btn_group3"><a id="153" href="#" onclick="event.preventDefault();">악세사리</a></div>
					<div class="btn_group3"><a id="154" href="#" onclick="event.preventDefault();">스마트워치</a></div>
					<div class="btn_group3"><a id="155" href="#" onclick="event.preventDefault();">기타가전</a></div>
				</div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
			</div>
			<div id="group3_21">
				<div class="group3_btn"></div>
				<div class="group3_btn">
					<div class="btn_group3"><a id="211" href="#" onclick="event.preventDefault();">침대</a></div>
					<div class="btn_group3"><a id="212" href="#" onclick="event.preventDefault();">매트리스</a></div>
					<div class="btn_group3"><a id="213" href="#" onclick="event.preventDefault();">화장대</a></div>
					<div class="btn_group3"><a id="214" href="#" onclick="event.preventDefault();">침대프레임</a></div>
					<div class="btn_group3"><a id="215" href="#" onclick="event.preventDefault();">침구</a></div>
				</div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
			</div>
			<div id="group3_22">
				<div class="group3_btn"></div>
				<div class="group3_btn">
					<div class="btn_group3"><a id="221" href="#" onclick="event.preventDefault();">장롱</a></div>
					<div class="btn_group3"><a id="222" href="#" onclick="event.preventDefault();">행거</a></div>
					<div class="btn_group3"><a id="223" href="#" onclick="event.preventDefault();">수납장</a></div>
					<div class="btn_group3"><a id="224" href="#" onclick="event.preventDefault();">선반</a></div>
					<div class="btn_group3"><a id="225" href="#" onclick="event.preventDefault();">식탁</a></div>
				</div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
			</div>
			<div id="group3_23">
				<div class="group3_btn"></div>
				<div class="group3_btn">
					<div class="btn_group3"><a id="231" href="#" onclick="event.preventDefault();">책상</a></div>
					<div class="btn_group3"><a id="232" href="#" onclick="event.preventDefault();">의자</a></div>
					<div class="btn_group3"><a id="233" href="#" onclick="event.preventDefault();">책장</a></div>
					<div class="btn_group3"><a id="234" href="#" onclick="event.preventDefault();">책상소품</a></div>
					<div class="btn_group3"><a id="235" href="#" onclick="event.preventDefault();">기타가구</a></div>
				</div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
			</div>
			<div id="group3_24">
				<div class="group3_btn"></div>
				<div class="group3_btn">
					<div class="btn_group3"><a id="241" href="#" onclick="event.preventDefault();">소파</a></div>
					<div class="btn_group3"><a id="242" href="#" onclick="event.preventDefault();">TV거실장</a></div>
					<div class="btn_group3"><a id="243" href="#" onclick="event.preventDefault();">거실테이블</a></div>
					<div class="btn_group3"><a id="244" href="#" onclick="event.preventDefault();">장식장</a></div>
					<div class="btn_group3"><a id="245" href="#" onclick="event.preventDefault();">커튼</a></div>
					<div class="btn_group3"><a id="246" href="#" onclick="event.preventDefault();">기타가구</a></div>
				</div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
			</div>
			<div id="group3_31">
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn">
					<div class="btn_group3"><a id="311" href="#" onclick="event.preventDefault();">기저귀</a></div>
					<div class="btn_group3"><a id="312" href="#" onclick="event.preventDefault();">국내분유</a></div>
					<div class="btn_group3"><a id="313" href="#" onclick="event.preventDefault();">해외분유</a></div>
					<div class="btn_group3"><a id="314" href="#" onclick="event.preventDefault();">물티슈</a></div>
					<div class="btn_group3"><a id="315" href="#" onclick="event.preventDefault();">이유식</a></div>
				</div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
			</div>
			<div id="group3_32">
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn">
					<div class="btn_group3"><a id="321" href="#" onclick="event.preventDefault();">샴푸/바디</a></div>
					<div class="btn_group3"><a id="322" href="#" onclick="event.preventDefault();">목욕용품</a></div>
					<div class="btn_group3"><a id="323" href="#" onclick="event.preventDefault();">유아화장품</a></div>
					<div class="btn_group3"><a id="324" href="#" onclick="event.preventDefault();">위생용품</a></div>
					<div class="btn_group3"><a id="325" href="#" onclick="event.preventDefault();">기타용품</a></div>
				</div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
			</div>
			<div id="group3_33">
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn">
					<div class="btn_group3"><a id="331" href="#" onclick="event.preventDefault();">유모차</a></div>
					<div class="btn_group3"><a id="332" href="#" onclick="event.preventDefault();">카시트</a></div>
					<div class="btn_group3"><a id="333" href="#" onclick="event.preventDefault();">아기띠</a></div>
					<div class="btn_group3"><a id="334" href="#" onclick="event.preventDefault();">보행기</a></div>
					<div class="btn_group3"><a id="335" href="#" onclick="event.preventDefault();">기타용품</a></div>
				</div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
			</div>
			<div id="group3_34">
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn">
					<div class="btn_group3"><a id="341" href="#" onclick="event.preventDefault();">레고</a></div>
					<div class="btn_group3"><a id="342" href="#" onclick="event.preventDefault();">작동완구</a></div>
					<div class="btn_group3"><a id="343" href="#" onclick="event.preventDefault();">교육/블럭완구</a></div>
					<div class="btn_group3"><a id="344" href="#" onclick="event.preventDefault();">인형</a></div>
					<div class="btn_group3"><a id="345" href="#" onclick="event.preventDefault();">기타완구</a></div>
				</div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
			</div>
			<div id="group3_41">
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn">
					<div class="btn_group3"><a id="411" href="#" onclick="event.preventDefault();">냄비</a></div>
					<div class="btn_group3"><a id="412" href="#" onclick="event.preventDefault();">프라이팬</a></div>
					<div class="btn_group3"><a id="413" href="#" onclick="event.preventDefault();">식기</a></div>
					<div class="btn_group3"><a id="414" href="#" onclick="event.preventDefault();">용기</a></div>
					<div class="btn_group3"><a id="415" href="#" onclick="event.preventDefault();">기타</a></div>
				</div>
				<div class="group3_btn"></div>
			</div>
			<div id="group3_42">
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn">
					<div class="btn_group3"><a id="421" href="#" onclick="event.preventDefault();">세제</a></div>
					<div class="btn_group3"><a id="422" href="#" onclick="event.preventDefault();">섬유유연제</a></div>
					<div class="btn_group3"><a id="423" href="#" onclick="event.preventDefault();">주방세제</a></div>
					<div class="btn_group3"><a id="424" href="#" onclick="event.preventDefault();">청소용품</a></div>
					<div class="btn_group3"><a id="425" href="#" onclick="event.preventDefault();">기타</a></div>
				</div>
				<div class="group3_btn"></div>
			</div>
			<div id="group3_43">
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn">
					<div class="btn_group3"><a id="431" href="#" onclick="event.preventDefault();">욕실인테리어</a></div>
					<div class="btn_group3"><a id="432" href="#" onclick="event.preventDefault();">수건</a></div>
					<div class="btn_group3"><a id="433" href="#" onclick="event.preventDefault();">샤워기/욕조</a></div>
					<div class="btn_group3"><a id="434" href="#" onclick="event.preventDefault();">화장지</a></div>
					<div class="btn_group3"><a id="435" href="#" onclick="event.preventDefault();">기타</a></div>
				</div>
				<div class="group3_btn"></div>
			</div>
			<div id="group3_44">
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn">
					<div class="btn_group3"><a id="441" href="#" onclick="event.preventDefault();">애견사료</a></div>
					<div class="btn_group3"><a id="442" href="#" onclick="event.preventDefault();">간식/영양제</a></div>
					<div class="btn_group3"><a id="443" href="#" onclick="event.preventDefault();">집/이동장/생활</a></div>
					<div class="btn_group3"><a id="444" href="#" onclick="event.preventDefault();">위생/장난감</a></div>
					<div class="btn_group3"><a id="445" href="#" onclick="event.preventDefault();">기타</a></div>
				</div>
				<div class="group3_btn"></div>
			</div>
			<div id="group3_51">
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn">
					<div class="btn_group3"><a id="511" href="#" onclick="event.preventDefault();">여성가방</a></div>
					<div class="btn_group3"><a id="512" href="#" onclick="event.preventDefault();">남성가방</a></div>
					<div class="btn_group3"><a id="513" href="#" onclick="event.preventDefault();">캐쥬얼가방</a></div>
					<div class="btn_group3"><a id="514" href="#" onclick="event.preventDefault();">여행가방</a></div>
					<div class="btn_group3"><a id="515" href="#" onclick="event.preventDefault();">지갑</a></div>
				</div>
			</div>
			<div id="group3_52">
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn">
					<div class="btn_group3"><a id="521" href="#" onclick="event.preventDefault();">시계</a></div>
					<div class="btn_group3"><a id="522" href="#" onclick="event.preventDefault();">스마트워치</a></div>
					<div class="btn_group3"><a id="523" href="#" onclick="event.preventDefault();">헤어액서서리</a></div>
					<div class="btn_group3"><a id="524" href="#" onclick="event.preventDefault();">목걸이</a></div>
					<div class="btn_group3"><a id="525" href="#" onclick="event.preventDefault();">반지/귀걸이</a></div>
				</div>
			</div>
			<div id="group3_53">
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn">
					<div class="btn_group3"><a id="531" href="#" onclick="event.preventDefault();">여성구두</a></div>
					<div class="btn_group3"><a id="532" href="#" onclick="event.preventDefault();">남성구두</a></div>
					<div class="btn_group3"><a id="533" href="#" onclick="event.preventDefault();">운동화</a></div>
					<div class="btn_group3"><a id="534" href="#" onclick="event.preventDefault();">샌들/슬리퍼</a></div>
					<div class="btn_group3"><a id="535" href="#" onclick="event.preventDefault();">워커/부츠</a></div>
				</div>
			</div>
			<div id="group3_54">
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn">
					<div class="btn_group3"><a id="541" href="#" onclick="event.preventDefault();">선글라스/안경</a></div>
					<div class="btn_group3"><a id="542" href="#" onclick="event.preventDefault();">우산/양산</a></div>
					<div class="btn_group3"><a id="543" href="#" onclick="event.preventDefault();">스카프/머플러</a></div>
					<div class="btn_group3"><a id="544" href="#" onclick="event.preventDefault();">모자</a></div>
					<div class="btn_group3"><a id="545" href="#" onclick="event.preventDefault();">장갑/벨트/넥타이</a></div>
					<div class="btn_group3"><a id="546" href="#" onclick="event.preventDefault();">기타</a></div>
				</div>
			</div>
			<div id="group3_55">
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn"></div>
				<div class="group3_btn">
					<div class="btn_group3"><a id="551" href="#" onclick="event.preventDefault();">남성의류</a></div>
					<div class="btn_group3"><a id="552" href="#" onclick="event.preventDefault();">여성의류</a></div>
					<div class="btn_group3"><a id="553" href="#" onclick="event.preventDefault();">캐주얼의류</a></div>
					<div class="btn_group3"><a id="554" href="#" onclick="event.preventDefault();">아동의류</a></div>
					<div class="btn_group3"><a id="555" href="#" onclick="event.preventDefault();">기타</a></div>
				</div>
			</div>
		</div>
		<div class="category_added"></div>
		<form action="/categorySet/categorySet.do" id="addCategory" method="post"></form>
		<div id="btn_add" class="btn_long"><a href="#" onclick="event.preventDefault();">카 테 고 리&nbsp;&nbsp;&nbsp;추 가</a></div>
	</div>
	<div class="category_save">
		<div id="category_added_all" class="category_all">
			<c:if test="${CategoryListSize!=0}">
				<c:forEach var="Category" items="${CategoryList}">
					<div class="category_set${Category.category_id} size_long">
						<div id="add_${Category.category_id}" class="size_short">
							${Category.group1}, ${Category.group2}, ${Category.group3}
						</div>
						<div id="del_${Category.category_id}" class="del_category btn_short"><a href="#" onclick="event.preventDefault();">삭&nbsp;&nbsp;&nbsp;제</a></div>
					</div>
				</c:forEach>
			</c:if>
			<c:if test="${CategoryListSize==0 || CategoryListSize==null}">
				<div id="no_category_text" class="size_long category_text"><h1 class="text_sub">카테고리를 추가해 주세요</h1></div>
			</c:if>
		</div>
	</div>
</body>
</html>