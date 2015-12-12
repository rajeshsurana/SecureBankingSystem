<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Details</title>

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
			<div class="panel-heading">User details</div> 
			<div class="panel-body">
				<dl>
					<dd><label>Username</label></dd> 
					<dd><label><c:out value="${user_name}">uname missing</c:out></label></dd>
				</dl>
				<dl>
					<dd><label>E-mail</label></dd>
					<dd><label><c:out value="${user_email}">email missing</c:out></label></dd>
				</dl>
				<dl>
					<dd><label>First Name</label></dd>
					<dd><label><c:out value="${user_fname}">fname missing</c:out></label></dd>
				</dl>
				<dl>
					<dd><label>Last Name</label> </dd>
					<dd><label><c:out value="${user_lname}">lname missing</c:out></label></dd>
				</dl>
				<dl>
					<dd><label>Date of Birth</label></dd>
					<dd><label><c:out value="${user_dob}">dob missing</c:out></label></dd>
				</dl>
				<dl>
					<dd><label>SSN</label></dd>
					<dd><label><c:out value="${user_ssn}">ssn missing</c:out></label></dd>
				</dl>
				<dl>
					<dd><label>Residential Address</label></dd>
					<dd><label><c:out value="${user_raddress}">res addr missing</c:out></label></dd>
				</dl>
				<dl>
					<dd>Mailing Address</dd>
					<dd><label><c:out value="${user_maddress}">mail addr missing</c:out></label></dd>
				</dl>	
			</div>
		</div>
	</div>
</body>
</html>