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

<title>verifyOTP</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link type="text/css"
	href="${pageContext.request.contextPath}/resources/css/jquery.keypad.css"
	rel="stylesheet">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/script/jquery.plugin.min.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/script/jquery.keypad.min.js"></script>
<script>
	$(document).ready(function() {
		$('#otp').keypad({
			keypadOnly : false,
			layout : $.keypad.qwertyLayout
		});
	});
</script>
</head>
<body>
	<div class="navbar navbar-default navbar-static-top">
		<div class="navbar-brand">
			<a href="#"><img class="img-responsive"
				src="<c:url value="/resources/Images/logotan3.jpg" />" width="20%" /></a>
		</div>
	</div>
	<div class="container">
		<div class="btn pull-right">
			<a href="${pageContext.request.contextPath}/goToHome" class="btn btn-primary" role="button">Home</a>
			<a href="${pageContext.request.contextPath}/logout" class="btn btn-primary" role="button">Logout</a>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">Verify OPT</div>
			<div class="panel-body">
				<h2>An OTP has been sent to your Email. Please enter the sent
					OTP for Verification!</h2>
				<br /> <br />
				<form method="POST" action="verifyOTP.do">
					<div class="form-group">
						<label for="otp" class="control-label">One-Time Password</label> <input
							type="text" class="form-control" placeholder="Enter your OTP"
							name="otp" id="otp" />
					</div>
					<div class="form-group">
						<input name="submit" class="btn btn-primary" type="submit"
							value="verify OTP" />
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>