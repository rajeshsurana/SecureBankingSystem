<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<link rel="stylesheet" 	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet"	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/script/main.js"></script>

	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Reset Password</title>
	<!--<style>
		body  {
		    background-image: url("<c:url value="/resources/Images/bg.jpg" />");
		    background-color: #cccccc;
		    background-size: cover;
    		background-repeat: no-repeat;
		}
	</style>-->
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
			<div class="panel-heading">Reset Password</div> 
			<div class="panel-body">
				<sf:form method="POST" action="${pageContext.request.contextPath}/resetPassword" commandName="bankUser">
					<div class="form-group">
						<label for="email" class="form-control">Email:</label>
						<sf:input type="email" cssClass="form-control" placeholder="Enter your Email Here" path="email" name="email" maxlength="30"/><sf:errors path="email"></sf:errors> 
					</div>
					<div class="form-group">
						OTP will be sent to your registered Email address.
					</div>	
					<div class="form-group">
						<c:out value = "${passwordResetResult}" />
					</div>		
					<div class="form-group">
						<input name="submit" type="submit" value="Send OTP" class="btn btn-primary"/>
					</div>
				</sf:form>
			</div>
		</div>
	</div>
</body>
</html>