<%@ page language="java" contentType="text/html; charset=UTF-8"
  	  pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri="http://java.sun.com/jstl/core_rt" %>

<c:set var ="path" 	value="${pageContext.request.contextPath}"></c:set>

<c:if test="${sessionScope.id == null}">
	<script>
		alert("로그인 하신 후 사용하세요.");
		location.href="${path}/customer/login.do";
	</script>
</c:if>












