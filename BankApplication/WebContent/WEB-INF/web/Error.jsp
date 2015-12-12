<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<link rel="stylesheet" 	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet"	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/script/main.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Refresh" content="5;url=${pageContext.request.contextPath}/">
<title>Error</title>
</head>
<body>
	<div class="navbar navbar-default navbar-static-top">
		<div class="navbar-brand">
			<a href="#"><img class="img-responsive" src='<c:url value="/resources/Images/logotan3.jpg" />' width="20%"/></a>
		</div>
	</div>
	<div class="container">
		<div class="panel panel-default">
	  		<div class="panel-heading">Action Intrusion.</div>
	  		<div class="panel-body">
				<div class="alert alert-danger" role="alert">oops Something went wrong!!!!</div>
				<div class="alert alert-info" role="alert">You will shortly be redirected to login page.</div>
			</div>
		</div>
	</div>			
</body>
</html>