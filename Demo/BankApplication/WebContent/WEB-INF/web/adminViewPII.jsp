<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>System Admin  - View User Details</title>
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
			<div class="panel-heading">Search Results</div>
			<div class="panel-body">
				<c:choose>
					<c:when test="${empty bankuserList.bankUsers}">
						<H2>The requested User does not Exist. Please search for another user</H2>
					</c:when>
					<c:otherwise>
						<c:forEach items="${bankuserList.bankUsers}" var="bankuser" varStatus="status">
							<table class="table table-hover">
								<tr> 
									<td> Bank User ID</td>
									<td> <label><c:out value="${bankuser.bankUserId}"></c:out></label></td>
								</tr>
								<tr> 
									<td> Username</td>
									<td> <label><c:out value="${bankuser.userName}"></c:out></label> </td>
								</tr>
								<tr>
									<td> Email </td>
									<td> <label><c:out value="${bankuser.email}"></c:out></label> </td>
								</tr>
								<tr> 
									<td> First Name</td>
									<td> <label> <c:out value="${bankuser.firstName}"></c:out></label> </td>
								</tr>
								<tr> 
									<td> Last Name </td>
									<td> <label><c:out value="${bankuser.lastName}"></c:out></label> </td>
								</tr>
								<tr> 
									<td> Date of Birth </td>
									<td> <label><c:out value="${bankuser.dateOfBirth}"></c:out></label> </td>
								</tr>
								<tr> 
									<td> Phone Number </td>
									<td> <label><c:out value="${bankuser.phoneNumber}"></c:out></label> </td>
								</tr>
								<tr>
									<td> SSN </td>
									<td> <label><c:out value="${bankuser.ssn}"></c:out></label> </td>
								</tr>
								<tr> 
									<td> User Created on Date </td>
									<td> <label><c:out value="${bankuser.userCreatedOn}"></c:out></label> </td>
								</tr>
								<tr> 
									<td> Account Status </td>
									<td> <label><c:out value="${bankuser.accountStatus}"></c:out></label> </td>
								</tr>
								<tr> 
									<td> Mailing Address </td>
									<td> <label><c:out value="${bankuser.mailingAddress}"></c:out></label> </td>
								</tr>
								<tr> 
									<td> Residential Address </td>
									<td> <label><c:out value="${bankuser.residentialAddress}"></c:out></label> </td>
								</tr>
								<tr> 
									<td> Assigned to Employee</td>
									<td> <label><c:out value="${bankuser.personalBanker}"></c:out></label> </td>
								</tr>
							</table>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
</body>
</html>