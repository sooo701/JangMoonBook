<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<c:set var = "path" value = "${pageContext.request.contextPath }"/>

<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script>
    function sample6_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var fullAddr = ''; // 최종 주소 변수
                var extraAddr = ''; // 조합형 주소 변수

                // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    fullAddr = data.roadAddress;

                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    fullAddr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
                if(data.userSelectedType === 'R'){
                    //법정동명이 있을 경우 추가한다.
                    if(data.bname !== ''){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있을 경우 추가한다.
                    if(data.buildingName !== ''){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                    fullAddr += (extraAddr !== '' ? ' ('+ extraAddr +')' : '');
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('zipcode').value = data.zonecode; //5자리 새우편번호 사용
                document.getElementById('address1').value = fullAddr;

                // 커서를 상세주소 필드로 이동한다.
                document.getElementById('address2').focus();
            }
        }).open();
    }
</script>
</head>
<body>
<h2>회원가입</h2>
	<form name = "form1" method = "post" action="${path}/customer/insert.do">
		<table border = "1" width = "400px">
			<tr>
				<td>ID </td>
				<td><input name ="id"></td>
			</tr>
			<tr>
				<td>비밀번호 </td>
				<td><input type="password"  name ="pwd"></td>
			</tr>
			<tr>
				<td>우편번호 </td>
				<td><input type="text" id="zipcode" name="zipcode" placeholder="우편번호">
					<input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기"><br></td>
			</tr>
			<tr>
				<td>주소 </td>
				<td><input type="text" id="address1" name="address1" placeholder="주소"></td>
			</tr>
			<tr>
				<td>상세주소 </td>
				<td><input type="text" id="address2" name="address2" placeholder="상세주소"></td>
			</tr>
			<tr>
				<td>Email </td>
				<td><input name ="email"></td>
			</tr>
			<tr>
				<td>전화번호 </td>
				<td><input name ="phone"></td>
			</tr>
			<tr>
				<td colspan = "2"><input type = "submit" value = "등록"></td>
			</tr>
		</table>	
	</form>
</body>
</html>