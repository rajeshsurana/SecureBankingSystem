<%@page import="com.spring.util.StaticConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<link rel="stylesheet" 	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet"	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/script/main.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>System Admin -  Search External User</title>
</head>
<body>
	<div class="navbar navbar-default navbar-static-top">
		<div class="navbar-brand">
			<img class="img-responsive"
				src="<c:url value="/resources/Images/logotan3.jpg" />" width="20%" />
		</div>
	</div>
	<div class="container">
		<div class="btn pull-right">
			<a href="${pageContext.request.contextPath}/goToHome" class="btn btn-primary" role="button">Home</a>
			<a href="${pageContext.request.contextPath}/logout" class="btn btn-primary" role="button">Logout</a>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">Search for User by UserName</div>
			<div class="panel-body">
				<h2>Enter the user Name to view the details for the
					user!!!</h2>
				<form name="searchUser" method="POST" action="sysAdminUpdateUserProfileEdit.do">
					<div class="form-group">
						<span>${bankUserName}</span><br /> <label for="User Name"
							class="control-label">Enter the User Name</label> <input type="text"
							class="form-control"
							placeholder="Enter the User Name want to retrieve the details for"
							name="bankUserName" id="bankUserName"></input>
						<div id="errors" style="color: #ff0000;"></div>
						<span style="color: #ff0000;">${message}</span>
					</div>
					<div class="form-group">
						<input name="submit" class="btn btn-primary" type="submit" value="Search"></input>
					</div>
				</form>
			</div>
		</div>
	</div>

</body>

</html>
