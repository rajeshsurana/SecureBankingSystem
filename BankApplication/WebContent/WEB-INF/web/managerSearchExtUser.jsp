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

<script type="text/javascript">
	function check_field(id) {
		var field = document.getElementById(id);

		if (isNaN(field.value)) {
			 document.getElementById('errors').innerHTML="*Please enter only Integer*";
			 return false;
		} else {
			document.searchUser.action = "SearchUser.do";
			return true;
		}
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Manager - Search External User</title>
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
			<div class="panel-heading">Search for User</div>
			<div class="panel-body">
				<form name="searchUser" method="POST" action="" onsubmit="return check_field('bankAccountId');">
					<div class="form-group">
						<label for="Account Number"
							class="control-label">Enter the account Number</label> <input type="text"
							class="form-control"
							placeholder="Enter the account Number you want to retrieve the details for"
							name="bankAccountId" id="bankAccountId"
							onblur="check_field('bankAccountId');"></input>
						<div id="errors" style="color: #ff0000;"></div>
						<span style="color: #ff0000;">${message}</span>
					</div>
					<div class="form-group">
						<input name="submit" class="btn btn-default" type="submit"
							onclick="check_field('bankAccountId');" value="Search"></input>
					</div>
				</form>
			</div>
		</div>
	</div>

</body>

</html>
