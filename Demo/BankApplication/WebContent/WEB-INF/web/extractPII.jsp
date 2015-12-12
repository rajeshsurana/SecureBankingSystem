<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>System Admin - Extract PII</title>
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<link rel="stylesheet" 	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet"	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/script/main.js"></script>

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
			<div class="panel-heading">PII Information Request</div>
			<div class="panel-body">
				<h3>Choose a method to view PII</h3>
				<div class="form-group">
					<sf:form method="POST" action="PIIUsingUserName.do">
						<table>
							<tr>
								<td colspan='2'> 1. 
								<input name="submit" type="submit" style="width:200px"
									class="btn btn-primary" value="Using User Name" /></td>
							</tr>
						</table>
					</sf:form>
					<sf:form method="POST" action="PIIUsingAccountNumber.do">	
						<table>
							<tr>
								<td colspan='2'> 2. 
								<input name="submit" type="submit" style="width:200px"
									class="btn btn-primary" value="Using Account Number" /></td>
							</tr>
						</table>
					</sf:form>
					<sf:form method="POST" action="SystemAdminupdateUserProfile.do">
						<table>
							<tr>
								<td colspan='2'> 3. 
								<input name="submit" type="submit" style="width:300px"
									class="btn btn-primary" value="Update User Profile By UserName" /></td>
							</tr>
						</table>
					</sf:form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>