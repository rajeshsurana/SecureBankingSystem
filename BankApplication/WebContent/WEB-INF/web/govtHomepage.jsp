<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Government - Home</title>
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
		<div class="panel panel-default">
			<div class="panel-heading">Government Home</div>
			<div class="panel-body">
				<h3>SouthwestBank has created a request to view PII. Please accept the request</h3>
				<div class="form-group">
					<form name="PIIAccept" method="POST" action="AcceptPIIRequest.do">
						<input name="submit" class="btn btn-primary" type="submit"
							value="Accept"></input>
					</form>
					<form name="PIIAccept" method="POST" action="DenyPIIRequest.do">
						<input name="submit" class="btn btn-primary" type="submit"
							value="Deny"></input>
					</form>
				</div>

			</div>
		</div>
	</div>

</body>
</html>