<%@page import="com.spring.util.StaticConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ListAccountApprovals</title>
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
			<div class="panel-heading">New User Account Holders that require Approval</div>
			<div class="panel-body">
				<sf:form name="newBankUserApproval" id="newBankUserApproval" method="POST">
					<c:choose>
						<c:when test="${empty bankuserList.users}">
							<H4>There are no pending Approvals!!! All user Creation account requests have been addressed!!!</H4>
						</c:when>
						<c:otherwise>
						<div class="table-responsive">
							<table border="1" class="table">
								<tr>
									<th>userName</th>
									<th>FirstName</th>
									<th>LastName</th>
									<th>Date of Birth</th>
									<th>SSN</th>
									<th>Email</th>
									<th>Phone Number</th>
									<th>MailingAddress</th>
									<th>Residential Address</th>
									<th>User Created on</th>
									<th>Account Status</th>
									<th>Assigned to Manager</th>
								</tr>
								<c:forEach items="${bankuserList.users}" var="newbankuser"
									varStatus="status">
									<tr>
										<td><c:out value="${newbankuser.userName}"></c:out></td>
										<td><c:out value="${newbankuser.firstName}"></c:out></td>
										<td><c:out value="${newbankuser.lastName}"></c:out></td>
										<td><c:out value="${newbankuser.dateOfBirth}"></c:out></td>
										<td><c:out value="${newbankuser.ssn}"></c:out></td>
										<td><c:out value="${newbankuser.email}"></c:out></td>
										<td><c:out value="${newbankuser.phoneNumber}"></c:out></td>
										<td><c:out value="${newbankuser.mailingAddress}"></c:out></td>
										<td><c:out value="${newbankuser.residentialAddress}"></c:out></td>
										<td><c:out value="${newbankuser.userCreatedOn}"></c:out></td>
										<td><c:out value="${newbankuser.accountStatus}"></c:out></td>
										<td><c:out value="${newbankuser.assignedToManager}"></c:out></td>
									</tr>
								</c:forEach>
							</table>
						</div>
						</c:otherwise>
					</c:choose>
				</sf:form>
			</div>
		</div>
	</div>
</body>
</html>