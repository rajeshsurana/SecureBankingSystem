<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.spring.util.StaticConstants"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee Profile</title>

<script	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<link rel="stylesheet" 	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link rel="stylesheet"	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/script/main.js"></script>
	
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/overcast/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script>
	$(function() {
		$("#dob_intUser").datepicker();
	});
</script>
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
			<div class="panel-heading">Search Results</div>
			<div class="panel-body">
				<c:choose>
					<c:when test="${empty bankuserList.bankUsers}">
						<H2>The requested USer does not Exist. Please search for another user</H2>
					</c:when>
					<c:otherwise>
						<c:forEach items="${bankuserList.bankUsers}" var="bankuser"
							varStatus="status">
							<H2>User Information</H2>
							<form>
								<fieldset disabled>
									<label for="BankUserID">Bank User Id</label>
									<input class="form-control" style="width: 200px" type="text" placeholder="<c:out value="${bankuser.bankUserId}"></c:out>" readonly>
								</fieldset> <br/>
								
								<fieldset disabled>
									<label for="username">Username</label>
									<input class="form-control" style="width: 200px" type="text" placeholder="<c:out value="${bankuser.userName}"></c:out>" readonly>
								</fieldset> <br/>
								
								<fieldset disabled>
									<label for="email">E-mail</label>
									<input class="form-control" style="width: 350px" type="text" placeholder="<c:out value="${bankuser.email}"></c:out>" readonly>
								</fieldset> <br/>
								
								<fieldset disabled>
									<label for="fname">First Name</label>
									<input class="form-control" style="width: 200px" type="text" placeholder="<c:out value="${bankuser.firstName}"></c:out>" readonly>
								</fieldset> <br/>
								
								<fieldset disabled>
									<label for="lname">Last Name</label>
									<input class="form-control" style="width: 200px" type="text" placeholder="<c:out value="${bankuser.lastName}"></c:out>" readonly>
								</fieldset> <br/>
								 
								<fieldset disabled>
									<label for="dob">Date of Birth (yyyy/mm/dd)</label>
									<input class="form-control" style="width: 200px" type="text" placeholder="<c:out value="${bankuser.dateOfBirth.toString().substring(0, 10).replace('-', '/')}"></c:out>" readonly id="dob">
								</fieldset> <br/>
								
								<fieldset disabled>
									<label for="phoneNum">Phone Number</label>
									<input class="form-control" style="width: 200px" type="text" placeholder="<c:out value="${bankuser.phoneNumber}"></c:out>" readonly>
								</fieldset> <br/>
								
								<fieldset disabled>
									<label for="maddress">Mailing Address</label>
									<input class="form-control" style="width: 500px" type="text" placeholder="<c:out value="${bankuser.mailingAddress}"></c:out>" readonly>
								</fieldset> <br/>
								
								<fieldset disabled>
									<label for="raddess">Residential Address</label>
									<input class="form-control" style="width: 500px" type="text" placeholder="<c:out value="${bankuser.residentialAddress}"></c:out>" readonly>
								</fieldset> <br/>
							</form>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>	
</body>
</html>