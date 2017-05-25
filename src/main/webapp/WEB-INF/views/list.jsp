<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<table width="500" cellpadding="0" cellspacing="0" border="1">
		<tr>
			<td>번호</td>
			<td>제목</td>
			<td>날짜</td>
			<td>삭제</td>
		</tr>
			<c:forEach items="${list}" var="dto">
				<tr>
					<td>${dto.articleNumber}</td>
					<td><form action="content">
							<a href="content?title=${dto.title}">${dto.title}</a>
						</form></td>
					<td>${dto.regDate}</td>
					<td><a href="delete?title=${dto.title}">X</a></td>
				</tr>
			</c:forEach>
	</table>
<p><a href="noticeWriteForm">글작성</a></p>
</body>
</html>