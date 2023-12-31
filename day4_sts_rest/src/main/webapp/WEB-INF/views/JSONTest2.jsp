<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<% request.setCharacterEncoding("UTF-8"); %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home</title>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript">
	$(function() {
		$("#checkJson").click(function() {
			var article = {
					articleNo : "114",
					writer : "박지성",
					title : "안녕하세요",
					content : "상품 소개 글 입니다."
			};
			$.ajax({
// 				type : "POST",
// 				url : '${contextPath}/boards',
				type : "PUT",
				url : '${contextPath}/boards/114',
// 				type : "DELETE",
// 				url : '${contextPath}/boards/114',
				contentType : "application/json",
				data : JSON.stringify(article),
				
				success: function (data, textStatus) {
					alert(data);
				},
				error: function (data, textStatus) {
					alert("에러가 발생했습니다.");
				},
				complete: function (data, textStatus) {
				}
			});
		});
	});
</script>
</head>
<body>
	<input type="button" id="checkJson" value="쓰기 or 수정 or 삭제"/> <br><br>
	<div id="output"></div>
</body>
</html>