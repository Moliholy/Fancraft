<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Register on FanCraft</title>
<meta name="description" content="">
<meta name="author" content="">
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css"
	media="screen">

</head>
<body>
	<div class="container">
		<div class="jumbotron">
			<h3>User Information</h3>
			<form:form action="performregister.do" commandName="registerForm">
				<table>
					<tr>
						<td>User Name:</td>
						<td><form:input path="userName" /></td>
						<td><FONT color="red"><form:errors path="userName" /></FONT></td>
					</tr>
					<tr>
						<td>Display Name:</td>
						<td><form:input path="displayName" /></td>
						<td><FONT color="red"><form:errors path="displayName" /></FONT></td>
					</tr>
					<tr>
						<td>Password:</td>
						<td><form:password path="password" /></td>
						<td><FONT color="red"><form:errors path="password" /></FONT></td>
					</tr>
					<tr>
						<td>Confirm Password:</td>
						<td><form:password path="confirmPassword" /></td>
						<td><FONT color="red"><form:errors
									path="confirmPassword" /></FONT></td>
					</tr>
					<tr>
						<td><button type="submit" class="btn btn-primary">Register</button></td>
					</tr>
				</table>
			</form:form>
		</div>
	</div>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>