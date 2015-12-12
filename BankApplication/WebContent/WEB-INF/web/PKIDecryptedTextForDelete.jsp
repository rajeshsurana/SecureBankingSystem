<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.spring.util.StaticConstants"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PKI - View Decrypted Function</title>
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<link rel="stylesheet" 	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet"	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

</head>
<body>
	<div class="navbar navbar-default navbar-static-top">
		<div class="navbar-brand">
			<img class="img-responsive" src="<c:url value="/resources/Images/logotan3.jpg" />" width="20%" />
		</div>
	</div>
	<div class="container">
		<div class="btn pull-right">
			<a href="${pageContext.request.contextPath}/goToHome" class="btn btn-primary" role="button">Home</a>
			<a href="${pageContext.request.contextPath}/logout" class="btn btn-primary" role="button">Logout</a>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">PKI Decrypt text -Step 2 Verification</div>
			<div class="panel-body" align="center">
				<form
					action="${pageContext.request.contextPath}/encryptPKIforDelete.do"
					method="post">
					<c:choose>
						<c:when test="${empty pkiverification}">
							<H2>The requested User does not Exist or you are not
								authorized to access the user. You are only authorized to update
								Internal Employees</H2>
						</c:when>
						<c:otherwise>
							<fieldset>
								<legend>Decrypted Text</legend>
								<label><c:out
										value="${pkiverification.userVerificationCode}"></c:out></label>
							</fieldset>
							<fieldset>
								<label for="pki" class="control-label">Enter the servers
									public key</label>
								<TEXTAREA class="form-control"
									placeholder="Enter Servers key here" name="pki" id="pki"
									ROWS="10"></TEXTAREA>
							</fieldset>
						</c:otherwise>
					</c:choose>
					<div class="form-group">
						<input name="submit" class="btn btn-primary" type="submit"
							value="Encrypt with Servers Key" />
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>