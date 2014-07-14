<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<!-- Basic Page Needs
  ================================================== -->
<title>Fancraft Login</title>
<meta name="description" content="">
<meta name="author" content="">

<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" media="screen">

<!--[if lt IE 9]>
		<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->
</head>

<body>
<div class="container">
	<div class="jumbotron">
		<h3>Login</h3>
		<form:form action="performlogin.do" commandName="loginForm">
			<table>
				<tr>
					<td>User Name</td>
					<td><form:input path="userName" /></td>
					<td><FONT color="red"><form:errors path="userName" /></FONT></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><form:password path="password" /></td>
					<td><FONT color="red"><form:errors path="password" /></FONT></td>
				</tr>
				<tr>
					<td align="left"><button type="submit" class="btn btn-primary">Login</button></td>
					<td align="right"><a class="btn btn-primary" href="register.do" role="button">Register</a></td>
				</tr>
			</table>
		</form:form>
	</div>
	</div>
	
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>	
</body>
</html>