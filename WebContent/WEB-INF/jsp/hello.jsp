<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Beanstalk Hello World</title>
<meta name="description" content="">
<meta name="author" content="">
</head>
<body>
	${message}
	<table>
		<c:forEach var="cr" items="${crafts}">
			<tr>
				<td>${cr}</td>
			</tr>
		</c:forEach>
	</table>
	<form action="addCraft.do" method="POST" name="addCraft">
		<input id="craft" type="text" name="craft" />
		<button>Add</button>
	</form>
</body>
</html>